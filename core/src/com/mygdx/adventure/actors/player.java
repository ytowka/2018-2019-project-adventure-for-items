package com.mygdx.adventure.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.adventure.Inventory;
import com.mygdx.adventure.Resources;
import com.mygdx.adventure.entity;
import com.mygdx.adventure.game;
import com.mygdx.adventure.myGame;

public class player extends Actor {
    private Sprite sprie;
    public int direction = 1;
    public float speed = 4f;
    public float speedModifier = 0;
    public float jumpForce = 70;
    public float jumpModifier = 0;
    public float yModifier = 0;

    public int maxHealthInc = 0;

    public Sound runSound;
    public Sound swordSwingSound;
    public Sound hurtSound;
    public Sound lvlUpSound;

    public entity currTarget;

    public Sprite helmet;

    public int damageReduce = 0;

    public float playerDamageIncrease = 1f;

    public Inventory inventory;

    public int maxHealth = 100;
    public int health = 100;
    public boolean isAlve = true;

    private int xp = 0;
    public int level = 0;
    public int[] lvlxps = new int[]{100, 250, 500, 1250, 3000, 5000, 7500, 15000, 25000, 40000};
    public int[] hps = new int[]{0, 25, 25, 25, 50, 50, 50, 100, 100, 100};
    public Image lvlUp;

    public Fixture groundFixture;
    public int maxJumps = 2;
    public int jumpsUsed = 0;

    public int getXp() {
        return xp;
    }

    public void addXp(int xp) {
        if (level != lvlxps.length - 1) {
            this.xp += xp;
            if (this.xp >= lvlxps[level]) {
                this.xp -= lvlxps[level];
                level++;
                lvlUp();
            }
        } else {
            this.xp += xp;
        }
    }

    public void forceSetXp(int xp) {
        this.xp = xp;
    }

    public Map map;

    public Body collosionRect;

    float stateTime = 0;
    Animation run;
    TextureRegion[] runAtlas;

    Animation stay;
    TextureRegion[] stayAtlas;

    public Animation attack;
    TextureRegion[] attackAtlas;

    public Animation jump;

    public Animation currentAnimation;
    public float updateSoundDelay = 0.15f;
    public float updateSoundTime = 0;
    //public boolean playRunSound = false;
    //soundThred runSoundManager;

    public int move = 0;


    public float boxWidth;
    public float boxHeight;

    public long runSoundId;


    public player(Texture texture,Sound RunSound) {
        sprie = new Sprite(texture);
        setBounds(0, 0, 4, 4);
        setWidth(4 * direction);
        sprie.setBounds(getX() - (getWidth() / 2), getY(), getWidth(), getHeight());
        helmet = new Sprite(new Texture("items/helmet.png"));
        helmet.setBounds(sprie.getX(), sprie.getY(), 2, 2);


        inventory = new Inventory(this);
        runSound = RunSound;

        swordSwingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/swing.ogg"));
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("sounds/playerHurt.ogg"));
        lvlUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lvlup.ogg"));

        Texture runTexture = new Texture("player/run.png");
        runAtlas = new TextureRegion[4];
        runAtlas[0] = new TextureRegion(runTexture, 0, 0, 256, 256);
        runAtlas[1] = new TextureRegion(runTexture, 256, 0, 256, 256);
        runAtlas[2] = new TextureRegion(runTexture, 512, 0, 256, 256);
        runAtlas[3] = new TextureRegion(runTexture, 768, 0, 256, 256);
        run = new Animation(0.15f, runAtlas);

        Texture stayTexture = new Texture("player/stay.png");
        stayAtlas = new TextureRegion[2];
        stayAtlas[0] = new TextureRegion(stayTexture, 0, 0, 256, 256);
        stayAtlas[1] = new TextureRegion(stayTexture, 256, 0, 256, 256);
        stay = new Animation(0.3f, stayAtlas);

        Texture attackTexture = new Texture("player/attack.png");
        attackAtlas = new TextureRegion[4];
        attackAtlas[0] = new TextureRegion(attackTexture, 0, 0, 256, 256);
        attackAtlas[1] = new TextureRegion(attackTexture, 256, 0, 256, 256);
        attackAtlas[2] = new TextureRegion(attackTexture, 512, 0, 256, 256);
        attackAtlas[3] = new TextureRegion(attackTexture, 768, 0, 256, 256);
        attack = new Animation(0.15f, attackAtlas);

        lvlUp = new Image(new Texture("moveButtons/lvlUpImg.png"));
        lvlUp.setBounds(540 - 220, -128-300, 512, 128);


        currentAnimation = stay;

        map = Resources.village;


    }

    public Sprite getSpite() {
        return sprie;
    }

    public Texture black = new Texture("black.png");

    public class blackScreen extends Actor {
        public MoveByAction a;
        public MoveByAction b;
        public MoveByAction c;

        public void onEnd() {
        }

        ;

        public blackScreen(float time, float delay, boolean fadeUp) {
            setSize(1280, 720);
            a = new MoveByAction() {
                @Override
                protected void end() {
                    super.end();
                    addAction(b);
                }
            };
            b = new MoveByAction() {
                @Override
                protected void end() {
                    super.end();
                    onEnd();
                    addAction(c);
                }
            };
            c = new MoveByAction() {
                @Override
                protected void end() {
                    super.end();
                    remove();
                }
            };
            c.setDuration(time / 2f);
            a.setDuration(time / 2f);
            b.setDuration(delay);
            if (fadeUp) {
                this.setPosition(0, -720);
                a.setAmountY(720);
                c.setAmountY(720);
            } else {
                this.setPosition(0, 720);
                a.setAmountY(-720);
                c.setAmountY(-720);
            }
            this.addAction(a);
            game.UI.addActor(this);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(black, 0, getY(), 1280, 720);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(currentAnimation != jump){
            sprie.setRegion((TextureRegion) currentAnimation.getKeyFrame(stateTime, true));
        }else{
            sprie.setRegion(runAtlas[0]);
        }

        if (currentAnimation == stay) {
            if ((TextureRegion) stay.getKeyFrame(stateTime, true) == stayAtlas[1] && move == 0) {
                helmet.setBounds(collosionRect.getPosition().x - direction * 1.115f, collosionRect.getPosition().y + .5f - 0.07f, sprie.getWidth() / 2, sprie.getHeight() / 2);
            } else {
                helmet.setBounds(collosionRect.getPosition().x - direction * 1.115f, collosionRect.getPosition().y + .5f, sprie.getWidth() / 2, sprie.getHeight() / 2);
            }
        } else if (currentAnimation == run) {
            if (currentAnimation.getKeyFrame(stateTime, true) == runAtlas[1] || currentAnimation.getKeyFrame(stateTime, true) == runAtlas[3]) {
                yModifier = -0.07f;
            } else {
                yModifier = +0.07f;
            }
            helmet.setBounds(collosionRect.getPosition().x - direction * 1.115f, collosionRect.getPosition().y + .5f + yModifier, sprie.getWidth() / 2, sprie.getHeight() / 2);
        } else {
            yModifier = 0;
            helmet.setBounds(collosionRect.getPosition().x - direction * 1.115f, collosionRect.getPosition().y + .5f, sprie.getWidth() / 2, sprie.getHeight() / 2);
        }

        sprie.draw(batch);
        if (inventory.helmetSlot != null) {
            helmet.draw(batch);
        }

    }

    public void createBody(float x, float y) {
        BodyDef rectDef = new BodyDef();
        rectDef.type = BodyDef.BodyType.DynamicBody;
        rectDef.fixedRotation = true;
        rectDef.position.set(x, y);
        collosionRect = game.gameWorld.createBody(rectDef);
        PolygonShape rectShape = new PolygonShape();
        rectShape.setAsBox(4 / 6f, 1.2f);

        CircleShape bottomCircleShape = new CircleShape();
        bottomCircleShape.setRadius(4 / 6f);
        bottomCircleShape.setPosition(new Vector2(0, -1.2f));


        CircleShape topCircleShape = new CircleShape();
        topCircleShape.setRadius(4 / 6f);
        topCircleShape.setPosition(new Vector2(0, 1.2f));

        collosionRect.createFixture(rectShape, 1);
        collosionRect.createFixture(bottomCircleShape, 1);
        collosionRect.createFixture(topCircleShape, 1f);
        collosionRect.getFixtureList().get(0).setFriction(0);
        collosionRect.getFixtureList().get(1).setFriction(0.99f);
        collosionRect.getFixtureList().get(2).setFriction(0);

        groundFixture = collosionRect.getFixtureList().get(1);

        topCircleShape.dispose();
        bottomCircleShape.dispose();
        rectShape.dispose();

        boxHeight = 1.2f * 2 + 4 / 6;
        boxWidth = 4 / 6f;

        //
        game.UI.addActor(lvlUp);

    }

    public void lvlUp() {
        lvlUpSound.play();

        final MoveByAction moveDown = new MoveByAction();
        final MoveByAction stay = new MoveByAction() {
            @Override
            protected void end() {
                super.end();
                lvlUp.addAction(moveDown);
            }
        };
        stay.setDuration(1);
        moveDown.setAmount(0, -450-300);
        moveDown.setDuration(1.5f);
        MoveByAction moveUp = new MoveByAction() {
            @Override
            protected void end() {
                super.end();
                lvlUp.addAction(stay);
            }
        };
        moveUp.setAmount(0, 450+300);
        moveUp.setDuration(0.3f);
        lvlUp.addAction(moveUp);
        maxHealth += hps[level];
        health = maxHealth;
    }
    public void die() {
        if (isAlve) {
            game.paperMap.open = false;
            if(myGame.Android) game.main.getAds().showInter();
            isAlve = false;
            new blackScreen(1, 1, true) {
                @Override
                public void onEnd() {
                    Resources.initItems();
                    super.onEnd();
                    inventory.clearInv();
                    game.currentMap.Exit();
                    myGame.respawnPlayer();
                    myGame.respawnItems();
                    isAlve = true;
                    game.paperMap.newMap(game.currentMap.map);
                }
            };
        }
    }

    public void sleep() {
        new blackScreen(2, 1, false);
        health = maxHealth;
        myGame.checkPointSave();

    }

    public void setTexture(Texture texture) {
        sprie.setTexture(texture);
    }

    public void giveDamage(float Damage,entity ent) {
        hurtSound.play();
        int damage = (int) (Damage - damageReduce);
        if(damage<=0) damage = 1;
        Gdx.input.vibrate(50);
        if (health - damage < 1) {
            health = 0;
            die();
            return;
        }
        health -= damage;
        if (inventory.helmetSlot != null) {
            inventory.helmetSlot.use();
            inventory.helmetSlot.onAttack(ent);
            inventory.helmetSlot.usesRemain--;
        }
    }

    public boolean giveHealth(int health) { // return true if health become full
        if (this.health + health > maxHealth) {
            this.health = maxHealth;
            return true;
        } else {
            this.health += health;
            return false;
        }
    }

    public void move(UIButton left, UIButton right) {
        int move = 0;
        if (left.isPressed()) {
            move = -1;
            direction = -1;
        }
        if (right.isPressed()) {
            move = 1;
            direction = 1;
        }
        if (currentAnimation == attack) {
            if (attack.isAnimationFinished(stateTime)) {
                currentAnimation = stay;
            }
        } else {
            if(!grounded){
                currentAnimation = jump;
            }else{
                if (move != 0) {
                    currentAnimation = run;
                } else {
                    currentAnimation = stay;
                }
            }

        }

        if(grounded){
            playerDamageIncrease = 1f;
            if(currentAnimation != attack){
                collosionRect.setLinearVelocity((speed + speedModifier) * move, collosionRect.getLinearVelocity().y);
            }else{
                collosionRect.setLinearVelocity((speed + speedModifier) * move * 0.5f, collosionRect.getLinearVelocity().y);
            }
        }else{
            playerDamageIncrease = 1.2f;
            collosionRect.setLinearVelocity((speed + speedModifier) * move * 1.25f, collosionRect.getLinearVelocity().y);
        }




    }

    @Override
    public void act(float delta) {
        setPosition(collosionRect.getPosition().x - (getWidth() / 2 * direction), collosionRect.getPosition().y - (getHeight() / 2 - getWidth() / 6 / 2) - getWidth() / 6 + 0.5f + yModifier);
        sprie.setBounds(getX(), getY(), getWidth() * direction, getHeight());
        if (currentAnimation != jump) {
            //jumpsUsed = 0;
            if (currentAnimation == run) {
                runSound.resume(runSoundId);
                runSound.setVolume(runSoundId,0.3f);
            } else {
                runSound.pause(runSoundId);
            }
        }else{
            runSound.pause(runSoundId);
        }
        if (currTarget != null) {
            if ((Math.abs(currTarget.body.getTransform().getPosition().x - game.Player.collosionRect.getTransform().getPosition().x)) < (5f + getWidth() / 2) && Math.abs(currTarget.body.getTransform().getPosition().y - game.Player.collosionRect.getTransform().getPosition().y) < 2.6 + getHeight() / 2f && (currTarget.body.getTransform().getPosition().x - game.Player.collosionRect.getTransform().getPosition().x) * game.Player.direction > 0) {
                currTarget.updateHpBarByPlayer();
            } else {
                currTarget = null;
            }
        } else {
            game.hpBar.setPosition(-10, 0);
        }
        if (collosionRect.getPosition().y < 9) {
            die();
        }
        updateSoundTime += delta;
        stateTime += delta;
    }


    public void jump() {
        if (jumpsUsed+1 < maxJumps) {
            collosionRect.setLinearVelocity(collosionRect.getLinearVelocity().x,0f);
            collosionRect.applyLinearImpulse(0, jumpForce+jumpModifier, collosionRect.getPosition().x, collosionRect.getPosition().y, false);
            jumpsUsed++;
        }
    }

    public boolean grounded = true;

    public void attack() {
        stateTime = 0;
        currentAnimation = attack;
        swordSwingSound.play();
    }

    public void dispose() {
        runSound.dispose();
        swordSwingSound.dispose();
    }
}
