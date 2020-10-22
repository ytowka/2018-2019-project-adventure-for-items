package com.mygdx.adventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.mygdx.adventure.actors.Map;

public class entity extends Actor {
    public int id;
    public float vision= 19;
    public float attackDistance = 0.1f;
    public int damage;
    public Body body;
    public int xp;
    public float speed = 2f;
    public static float jumpDelay = 1f;
    public float jumpTime = 0;
    public int jumpHeight = 1500;
    public int hp;
    public int maxhp;

    public Animation<TextureRegion> run;
    public TextureRegion[] runTexture;

    public float stateTime = 0;
    public boolean isRun = false;
    public Map map;
    public static final float attackSpeed = 0.3f;
    public float attackTime = 0.3f;

    public short dir = 1;

    public float hpBarUpdDelay = 0;

    public boolean alive = true;
    public boolean respawn = true;

    public float mainX;
    public float mainY;


    public entity(Texture texture,int animationsFramesCount, int damage, int hp, int xpDrop, float x, float y, float width, float height, Map map) {
        id = myGame.entities.size();
        game.hpBar.setSize(8 / 3f, 1 / 3f);
        xp = xpDrop;
        setBounds(x, y, width, height);
        this.damage = damage;
        this.maxhp = hp;
        this.hp = maxhp;
        runTexture = new TextureRegion[animationsFramesCount];
        for(int i = 0; i < runTexture.length;i++){
            runTexture[i] = new TextureRegion(texture,i*texture.getWidth()/animationsFramesCount,0,texture.getWidth()/animationsFramesCount,texture.getHeight());
        }
        run = new Animation<TextureRegion>(0.2f, runTexture);
        this.map = map;
        map.entities.add(this);
        mainX = x;
        mainY = y;

        myGame.entities.add(this);
    }
    public entity(Texture texture,int animationsFramesCount, int damage,float speed, int hp, int xpDrop, float x, float y, float width, float height, Map map) {
        id = myGame.entities.size();
        game.hpBar.setSize(8 / 3f, 1 / 3f);
        xp = xpDrop;
        this.speed = speed;
        setBounds(x, y, width, height);
        this.damage = damage;
        this.maxhp = hp;
        this.hp = maxhp;
        runTexture = new TextureRegion[animationsFramesCount];
        for(int i = 0; i < runTexture.length;i++){
            runTexture[i] = new TextureRegion(texture,i*texture.getWidth()/animationsFramesCount,0,texture.getWidth()/animationsFramesCount,texture.getHeight());
        }
        run = new Animation<TextureRegion>(0.2f, runTexture);
        this.map = map;
        map.entities.add(this);
        mainX = x;
        mainY = y;

        myGame.entities.add(this);
    }
    public void attack() {
        if (attackTime < 0) {
            if (Math.abs(body.getTransform().getPosition().x - game.Player.collosionRect.getTransform().getPosition().x) < 1.1f + getWidth() / 2+attackDistance && Math.abs(body.getTransform().getPosition().y - game.Player.collosionRect.getTransform().getPosition().y) < 2.1 + getHeight() / 2f) {
                game.Player.giveDamage(damage,this);
                game.Player.collosionRect.applyForceToCenter(new Vector2(500 * dir, 500), false);
            }
            attackTime = attackSpeed;
        }
    }
    public void attack2(){

    }
    public void createAttackAnimation(int frames){

    }
    public void giveDamage(int damage) {
        game.main.entityHit.play();
        if (hp - damage < 1) {
            die();
        } else {
            hp -= damage;
            updateHPbar();
            game.Player.addXp(Math.round(xp/10f));
        }
    }

    public void die() {
        destroy();
        alive = false;
        MoveByAction fall = new MoveByAction(){
            @Override
            protected void end() {
                super.end();
                remove();
            }
        };
        fall.setAmountY(-15);
        fall.setDuration(0.5f);
        game.Player.addXp(xp);
        addAction(fall);
        game.Player.giveHealth(damage);
        game.main.entityDie.play();
    }

    @Override
    public void act(float delta) {
        if(alive){
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
        super.act(delta);
        if (body.getLinearVelocity().x > 0) dir = 1;
        else dir = -1;
        if (seePlayer() != 0) {
            //body.setLinearVelocity(speed * getDirection(), body.getLinearVelocity().y);
            dir = seePlayer();
            if(Math.abs(body.getLinearVelocity().x) < Math.abs(speed)) {
                body.applyLinearImpulse(new Vector2(speed * getDirection(), 0), new Vector2(0, 0), false);
            }
            if(Math.abs(body.getLinearVelocity().x) < 1){
                tryJump();
            }
            isRun = true;
        } else isRun = false;
        stateTime += delta;
        attackTime -= delta;
        attack();
        if ((Math.abs(body.getTransform().getPosition().x - game.Player.collosionRect.getTransform().getPosition().x)) < (5f + getWidth() / 2) && Math.abs(body.getTransform().getPosition().y - game.Player.collosionRect.getTransform().getPosition().y) < 2.6 + getHeight() / 2f && (body.getTransform().getPosition().x - game.Player.collosionRect.getTransform().getPosition().x) * game.Player.direction > 0) {
            game.Player.currTarget = this;
        }
        hpBarUpdDelay += delta;
        jumpTime += delta;
    }
    public boolean tryJump(){
        if(jumpTime > jumpDelay){
            jumpTime = 0;
            body.applyForceToCenter(new Vector2(0,jumpHeight),false);
            return true;
        }else{
            return false;
        }
    }

    public float getDirection() {
        return -(body.getPosition().x - game.Player.collosionRect.getPosition().x) / Math.abs(body.getPosition().x - game.Player.collosionRect.getPosition().x);
    }

    public void updateHpBarByPlayer() {
        if (hpBarUpdDelay > 0.3) {
            hpBarUpdDelay = 0;
            updateHPbar();
        }
    }

    public void spawn() {
        if(respawn){
            createBody();
            body.setTransform(new Vector2(mainX,mainY),0);
            hp = maxhp;
            alive = true;
        }else if(alive){
            createBody();
            body.setTransform(new Vector2(mainX,mainY),0);
            hp = maxhp;
        }

    }

    public void updateHPbar() {
        game.hpBar.update(maxhp, hp);
    }

    public void createBody() {
        body = Collision.createRect(BodyDef.BodyType.DynamicBody, getWidth() / 2, getHeight() / 2, getX(), getY(), 3);
        //body.getFixtureList().first().setFriction(0);
        body.setFixedRotation(true);
    }

    public void destroy() {
        if (alive) {
            body.destroyFixture(body.getFixtureList().get(0));
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isRun)
            batch.draw(run.getKeyFrame(stateTime, true), getX() - getWidth() / 2 * dir + getWidth() / 2, getY(), getWidth() * dir, getHeight());
        else
            batch.draw(run.getKeyFrame(0, true), getX() - getWidth() / 2 * dir + getWidth() / 2, getY(), getWidth() * dir, getHeight());
        game.hpBar.draw(batch, parentAlpha);
    }

    public short seePlayer() {
        if (Math.abs(body.getTransform().getPosition().x - game.Player.collosionRect.getTransform().getPosition().x) < vision && Math.abs(body.getPosition().y - game.Player.collosionRect.getPosition().y) < 5 ) {
            if(game.Player.collosionRect.getPosition().x-body.getPosition().x > 0){
                return 1;
            }else{
                return -1;
            }
        } else return 0;
    }
}
