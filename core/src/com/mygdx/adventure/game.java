package com.mygdx.adventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.adventure.Items.fullMap;
import com.mygdx.adventure.actors.Map;
import com.mygdx.adventure.actors.UIButton;
import com.mygdx.adventure.actors.UISwitcher;
import com.mygdx.adventure.actors.UIText;
import com.mygdx.adventure.actors.healthBar;
import com.mygdx.adventure.actors.player;
import com.mygdx.adventure.actors.tipsWindow;


public class game implements Screen {
    public static myGame main;
    public static game Game;

    public static Stage stage;

    public Texture bgTexture;
    public static Batch bg;
    public OrthographicCamera bgCamera;

    public Texture bgTexture2;
    public static Batch bg2;
    public OrthographicCamera bgCamera2;

    public static Stage UI;
    public OrthographicCamera UICamera;
    public static OrthographicCamera gameCamera;

    public Texture leftTexture;
    public Texture upTexture;
    public Texture rightTexture;
    public Texture attackTexture;

    public Texture leftTextureP;
    public Texture upTextureP;
    public Texture rightTextureP;
    public Texture attackTextureP;

    public UIButton Left;
    public UIButton Right;
    public UIButton Up;
    public UIButton Attack;
    public UIButton health;
    public UIButton Any;
    public healthBar healthbar;
    public healthBar xpBar;
    public Image frame;
    public UISwitcher inventoryOpener;
    public UISwitcher mapOpener;
    public static fullMap paperMap;
    public UIButton pauseBtn;
    tipsWindow tips;


    public UIText hp;
    public UIText ep;
    public UIText lvl;

    public UIText fps;
    public UIText linearVelocity;

    public static World gameWorld;
    public static Box2DDebugRenderer b2Visual;

    public boolean onPause = false;
    public Stage pause;
    public UIButton resume;
    public UIButton exit;
    public Image pauseFrame;
    public UIText resumeTxt;
    public UIText exitTxt;



    public static Map currentMap;

    int pos;
    public static player Player;
    //Texture[][] playerTextures;
    public InputMultiplexer input;
    public static boolean gameStarted = false;


    public void createPause(){
        pause = new Stage(UI.getViewport());
        Texture buttonR = new Texture("moveButtons/contentButton.png");
        resume = new UIButton(buttonR,buttonR){
            @Override
            public void onClick() {
                onPause = false;
            }
        };
        resume.sound = true;
        exit = new UIButton(buttonR,buttonR){
            @Override
            public void onClick() {
                if (onPause) Gdx.app.exit();
            }
        };
        exit.sound = true;
        pauseFrame = new Image(new Texture("pauseScreen.png"));
        resumeTxt = new UIText(30, "fonts/UI.ttf", "продолжить", Color.BLACK);
        exitTxt = new UIText(37, "fonts/UI.ttf", "выход", Color.BLACK);

        resume.setBounds(640-85,350,170,85);
        exit.setBounds(640-85,260,170,85);

        resumeTxt.setPosition(resume.getX()+10,resume.getY()+60);
        exitTxt.setPosition(exit.getX()+40,exit.getY()+60);

        pauseFrame.setSize(1280,720);

        pause.addActor(pauseFrame);
        pause.addActor(resume);
        pause.addActor(exit);
        pause.addActor(resumeTxt);
        pause.addActor(exitTxt);
    }
    public void createUI() {
        leftTexture = new Texture("moveButtons/leftReleased.png");
        rightTexture = new Texture("moveButtons/rightReleased.png");
        upTexture = new Texture("moveButtons/forwardReleased.png");
        attackTexture = new Texture("moveButtons/attackButtonR.png");

        leftTextureP = new Texture("moveButtons/leftPressed.png");
        rightTextureP = new Texture("moveButtons/rightPressed.png");
        upTextureP = new Texture("moveButtons/forwardPressed.png");
        attackTextureP = new Texture("moveButtons/attackButtonP.png");

        UICamera = new OrthographicCamera();

        UI = new Stage(new ExtendViewport(main.WIDTH, main.HEIGHT, UICamera));

        tips = new tipsWindow();

        Up = new UIButton(upTexture, upTextureP) {
            @Override
            public void onTouched() {
                Player.jump();
            }
        };
        Left = new UIButton(leftTexture, leftTextureP) {
        };
        Right = new UIButton(rightTexture, rightTextureP) {
        };
        Attack = new UIButton(attackTexture, attackTextureP) {
            @Override
            public void onTouched() {
                if (Player.inventory.swordSlot != null) {
                    Player.inventory.swordSlot.use();
                }

            }
        };
        Any = new UIButton(new Texture("moveButtons/aButtonR.png"), new Texture("moveButtons/aButtonP.png")) {
            @Override
            public void onClick() {
                super.onClick();
                if (Player.inventory.specSlot != null) {
                    Player.inventory.specSlot.use();
                }
            }
        };
        health = new UIButton(new Texture("moveButtons/healthButtonR.png"), new Texture("moveButtons/healthButtonP.png")) {
            @Override
            public void onClick() {
                if (Player.inventory.foodSlot != null) {
                    Player.inventory.foodSlot.use();
                }
            }
        };
        healthbar = new healthBar();
        xpBar = new healthBar();
        healthbar.setBounds(main.WIDTH - 266, main.HEIGHT - 42f, 256, 32);
        xpBar.setBounds(main.WIDTH - 266, main.HEIGHT - 84f, 256, 32);
        xpBar.setTextures(new Texture("moveButtons/energyFill.png"), new Texture("moveButtons/energyFrame.png"));
        hp = new UIText(37, "fonts/UI.ttf", String.valueOf(Player.health), Color.BLACK);
        ep = new UIText(37, "fonts/UI.ttf", String.valueOf(Player.getXp()), Color.BLACK);
        lvl = new UIText(37, "fonts/UI.ttf", String.valueOf(Player.level), Color.BLACK);

        hp.setBounds(healthbar.getX() + 10, healthbar.getY() + 28, 5, 5);
        ep.setBounds(xpBar.getX() + 10, xpBar.getY() + 28, 5, 5);
        lvl.setPosition(xpBar.getX() + xpBar.getWidth()-40, xpBar.getY() +28);


        pauseBtn = new UIButton(new Texture("moveButtons/pauseButtonR.png"), new Texture("moveButtons/pauseButtonP.png")){
            @Override
            public void onClick() {
                onPause = true;
            }
        };
        pauseBtn.sound = true;
        inventoryOpener = new UISwitcher(new Texture("moveButtons/backReleased.png"), new Texture("moveButtons/backPressed.png")){
            @Override
            public void onClick() {
                if(!mapOpener.enabled) super.onClick();
            }

            @Override
            public void onEnable() {
                if(!mapOpener.enabled){
                    super.onEnable();
                    Player.inventory.Open(true);
                    tips.open();
                }

            }
            @Override
            public void onDisable() {
                super.onDisable();
                Player.inventory.Open(false);
                tips.close();
                //System.out.print("asd");
            }
        };
        inventoryOpener.setBounds(640 + 220, 720 - 75, 70, 70);
        pauseBtn.setBounds(640-220-96,720-99,96,96);

        fps = new UIText(27, "fonts/UI.ttf", String.valueOf(Player.health), Color.BLACK);
        linearVelocity = new UIText(16, "fonts/UI.ttf", "x velocity ", Color.BLACK);

        if (main.devoloperMode) {
            UI.addActor(fps);
            fps.setBounds(main.WIDTH - 30, main.HEIGHT / 2, 5, 5);
            UI.addActor(linearVelocity);
            linearVelocity.setPosition(0, main.HEIGHT / 2 - 30);

        }
        frame = new Image(new Texture("frame.png"));
        frame.setBounds(0,0,main.WIDTH,main.HEIGHT);

        Left.setBounds(20,40,128,128);
        Right.setBounds(168,20,128,128);
        Any.setBounds(128-10,148,128,128);

        Attack.setBounds(main.WIDTH-148,40,128,128);
        Up.setBounds(main.WIDTH-148-148,20,128,128);
        health.setBounds(main.WIDTH-256+10,148,128,128);


        UI.addActor(frame);
        UI.addActor(Left);
        UI.addActor(Up);
        UI.addActor(Right);
        UI.addActor(Attack);
        UI.addActor(healthbar);
        UI.addActor(xpBar);
        UI.addActor(hp);
        UI.addActor(ep);
        UI.addActor(Player.inventory);
        UI.addActor(inventoryOpener);
        UI.addActor(health);
        UI.addActor(pauseBtn);
        UI.addActor(Any);
        UI.addActor(lvl);

        UIButton item = new UIButton(new Texture("tipsWindow.png"),new Texture("tipsWindow.png")){
            @Override
            public void onClick() {
                super.onClick();
                System.out.println(Player.collosionRect.getPosition().x+"f, "+Player.collosionRect.getPosition().y+"f"+" - item");
            }
        };
        item.setBounds(300,200,60,60);
        UIButton mob = new UIButton(new Texture("tipsWindow.png"),new Texture("tipsWindow.png")){
            @Override
            public void onClick() {
                super.onClick();
                System.out.println(Player.collosionRect.getPosition().x+"f, "+Player.collosionRect.getPosition().y+"f"+" - mob");
            }
        };
        mob.setBounds(400,200,60,60);
        if(main.devoloperMode){
            UI.addActor(item);
            UI.addActor(mob);
        }
    }

    public static healthBar hpBar;
    public void createStage() {
        gameCamera = new OrthographicCamera();
        stage = new Stage(new ExtendViewport(main.worldWidth, main.worldHeight, gameCamera));
        stage.addActor(Player);
        hpBar = new healthBar(){
            @Override
            public void act(float delta) {
                super.act(delta);
                if(Player.currTarget != null){
                    setPosition(Player.currTarget.body.getPosition().x -  game.hpBar.getWidth() / 2, Player.currTarget.body.getPosition().y + Player.currTarget.getHeight() / 2 + 0.1f);
                }

            }
        };
        stage.addActor(hpBar);
    }

    public void createWorld() {
        gameWorld = new World(new Vector2(0,-9.8f),false);
        b2Visual = new Box2DDebugRenderer();

        Player.createBody(16,22);
    }
    public void createBG(){
        bg = new SpriteBatch();
        bgCamera = new OrthographicCamera(myGame.worldWidth,myGame.worldHeight);
        bgTexture = new Texture("bg.png");

        bg2 = new SpriteBatch();
        bgCamera2 = new OrthographicCamera(myGame.worldWidth,myGame.worldHeight);
        bgTexture2 = new Texture("bg2.png");
    }

    //-------------------------------------------------------------------------------------------------------------------

    public game(myGame Main){



        Player = Main.Player;
        main = Main;
        Game = this;

        createStage();
        createUI();
        createWorld();
        createPause();
        createBG();

        Resources.initMaps();
        Resources.initItems();
        Resources.initEntities();

        input = new InputMultiplexer();
        input.addProcessor(UI);
        input.addProcessor(stage);
        input.addProcessor(pause);
        Gdx.input.setInputProcessor(input);


        if(!myGame.started){
            currentMap = Resources.village;
            if(myGame.devoloperMode){
                currentMap = myGame.maps.get(myGame.devoloperMapId);
            }
            Resources.firstInit();
        }else{
            myGame.initItems();
            currentMap = myGame.initMaps();
        }
        currentMap.load();
        if(!myGame.started){
            Resources.setItemsPos();
            myGame.checkPointSave();
        }
        gameStarted = true;
        myGame.playerSave[myGame.profileIndex].putBoolean("started", true);

        createPaperMap();

    }

    public void createPaperMap(){
        paperMap = new fullMap();
        mapOpener = new UISwitcher(new Texture("moveButtons/mapButton.png"),new Texture("moveButtons/mapButtonP.png")){
            @Override
            public void onClick() {
                if(!inventoryOpener.enabled) super.onClick();
            }
            @Override
            public void onDisable() {
                super.onDisable();
                paperMap.open = false;
                paperMap.paperMap.remove();
            }
            @Override
            public void onEnable() {
                if(!inventoryOpener.enabled){
                    super.onEnable();
                    paperMap.open = true;
                    UI.addActor(paperMap.paperMap);
                }

            }
        };
        mapOpener.setBounds(640 + 220+70, 720 - 109, 70, 70);
        UI.addActor(mapOpener);
    }

    @Override
    public void show() {

    }

    @Override
        public void render(float delta) {
        long time = System.currentTimeMillis();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        pos=3;

        ep.text = String.valueOf(Player.getXp()+" - "+Player.lvlxps[Player.level]);
        hp.text = String.valueOf(Player.health);
        lvl.text = String.valueOf(Player.level+1);

        healthbar.update(Player.maxHealth,Player.health);
        xpBar.update(Player.lvlxps[Player.level],Player.getXp());

        fps.text = String.valueOf(Gdx.graphics.getFramesPerSecond());
        linearVelocity.text = "x velocity "+String.valueOf(Player.collosionRect.getLinearVelocity().x) + "\n" + "y velocity "+String.valueOf(Player.collosionRect.getLinearVelocity().y+"\n"+"x: "+ Player.collosionRect.getPosition().x+"  y: "+Player.collosionRect.getPosition().y);


        bgCamera.position.set(gameCamera.position.x/6+bgCamera.viewportWidth/2,0+bgCamera.viewportHeight/2,0);
        bgCamera.update();
        bg.setProjectionMatrix(bgCamera.combined);

        bgCamera2.position.set(gameCamera.position.x/12+bgCamera.viewportWidth/2,0+bgCamera.viewportHeight/2,0);
        bgCamera2.update();
        bg2.setProjectionMatrix(bgCamera2.combined);

        currentMap.drawBG();
        bg2.begin();
        bg2.draw(bgTexture2,0,0,myGame.worldWidth*2,myGame.worldHeight);
        bg2.end();

        bg.begin();
        bg.draw(bgTexture,0,0,myGame.worldWidth*4,myGame.worldHeight);
        bg.end();

        if(currentMap.updateTips() != null){
            tips.open();
            tips.textUI.text = currentMap.updateTips().text;
        }else{
            tips.close();
        }


        currentMap.drawB();


        if (!onPause) stage.act();
        stage.draw();

        if(Gdx.input.isKeyPressed(32)){
            System.out.println(Player.collosionRect.getPosition().x+" "+Player.collosionRect.getPosition().y+" - item");
        }
        if(Gdx.input.isKeyPressed(88)){
            System.out.println(Player.collosionRect.getPosition().x+" "+Player.collosionRect.getPosition().y+" - mob");
        }


        if (!onPause) currentMap.updateTransitions();


        currentMap.drawF();

        if(main.devoloperMode) {
            b2Visual.render(gameWorld,gameCamera.combined);
        }
        if (!onPause) updateWorld(delta);


        if (!onPause) UI.draw();
        if (!onPause) UI.act();
        if(!onPause) paperMap.update();
        hpBar.act(delta);
        if(onPause){
            pause.draw();
            pause.act();
        }
        if(Player.collosionRect.getPosition().x>1+gameCamera.viewportWidth/2 &&  Player.collosionRect.getPosition().x<currentMap.endX-1-gameCamera.viewportWidth/2) gameCamera.position.set(Player.collosionRect.getPosition().x,Player.collosionRect.getPosition().y+1,0);
        else if(Player.collosionRect.getPosition().x<1+gameCamera.viewportWidth/2) gameCamera.position.set(1+gameCamera.viewportWidth/2,Player.collosionRect.getPosition().y+1,0);
        else gameCamera.position.set(currentMap.endX-1-gameCamera.viewportWidth/2,Player.collosionRect.getPosition().y+1+Player.yModifier,0);

    }
    public void updateWorld(float delta){
        gameWorld.step(1/18f,2,1);
        Player.move(Left,Right);
    }
    @Override
    public void resize(int width, int height) {

        UI.getViewport().update(width, height);
        UI.getCamera().position.set(UI.getViewport().getWorldWidth()/2-(UI.getViewport().getWorldWidth()-1280)/2,UI.getViewport().getWorldHeight()/2-(UI.getViewport().getWorldHeight()-720),0);
        frame.setBounds(-(UI.getViewport().getWorldWidth()-1280)/2,-(UI.getViewport().getWorldHeight()-720),UI.getViewport().getWorldWidth(),UI.getViewport().getWorldHeight());
        //stage.getViewport().setWorldSize(UI.getViewport().getWorldWidth()/myGame.coef+100,UI.getViewport().getWorldHeight()/myGame.coef);
        stage.getViewport().update(width,height);

        System.out.println(UI.getViewport().getWorldWidth()+" "+ UI.getViewport().getWorldHeight() +" "+(float)width/(float)height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {


    }
}
