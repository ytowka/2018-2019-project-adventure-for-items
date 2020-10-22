package com.mygdx.adventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.adventure.actors.UIButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.adventure.actors.UIText;


public class menu implements Screen {
    public static myGame main;

    public Texture Play;
    public Texture Exit;
    public Texture Sett;

    OrthographicCamera menuCamera;
    Stage menu;
    Stage BG;

    UIButton sett;
    UIButton go;
    UIButton exit;

    UIButton[] profiles;
    UIText profilesText;

    public UIButton disableAds;

    Image preview;
    public Image loadingScreen;
    boolean loading;
    public menu(final myGame Main){
        main = Main;

        Exit = new Texture("exitButtonR.png");
        Sett = new Texture("settButtonR.png");
        Play = new Texture("playButtonR.png");

        profiles = new UIButton[3];
        profilesText = new UIText(35,"fonts/UI.ttf","Профиль 1            Профиль 2            Профиль 3",Color.BLACK);
        profilesText.setPosition(640-100-10-200,20+55);

        final Texture PBT = new Texture("moveButtons/profileButton.png"); //profiles Button Texture
        final Texture PBTs = new Texture("moveButtons/profileButtonSelected.png"); //profiles Button Texture selected
        profiles[0] = new UIButton(PBTs,PBTs){
            @Override
            public void onClick() {
                super.onClick();
                setTextures(PBTs,PBTs);
                profiles[1].setTextures(PBT,PBT);
                profiles[2].setTextures(PBT,PBT);
                myGame.profileIndex = 0;
                setBounds(640-100-40-200-10,20-5,220,110);
                profiles[1].setBounds(640-100,20,200,100);
                profiles[2].setBounds(640+100+40,20,200,100);
                myGame.started = myGame.playerSave[myGame.profileIndex].getBoolean("started");
            }
        };
        profiles[0].setBounds(640-100-40-200-10,20-5,220,110);

        profiles[1] = new UIButton(PBT,PBT){
            @Override
            public void onClick() {
                super.onClick();
                setTextures(PBTs,PBTs);
                profiles[0].setTextures(PBT,PBT);
                profiles[2].setTextures(PBT,PBT);
                myGame.profileIndex = 1;
                setBounds(640-100-10,20-5,220,110);
                profiles[0].setBounds(640-100-40-200,20,200,100);
                profiles[2].setBounds(640+100+40,20,200,100);
                myGame.started = myGame.playerSave[myGame.profileIndex].getBoolean("started");
            }
        };
        profiles[1].setBounds(640-100,20,200,100);

        profiles[2] = new UIButton(PBT,PBT){
            @Override
            public void onClick() {
                super.onClick();
                setTextures(PBTs,PBTs);
                profiles[0].setTextures(PBT,PBT);
                profiles[1].setTextures(PBT,PBT);
                myGame.profileIndex = 2;
                setBounds(640+100+40-10,20-5,220,110);
                profiles[0].setBounds(640-100-40-200,20,200,100);
                profiles[1].setBounds(640-100,20,200,100);
                myGame.started = myGame.playerSave[myGame.profileIndex].getBoolean("started");
            }
        };
        profiles[2].setBounds(640+100+40,20,200,100);

        menuCamera = new OrthographicCamera(main.WIDTH,main.HEIGHT);
        menu = new Stage(new ExtendViewport(main.WIDTH,main.HEIGHT,menuCamera));
        BG = new Stage(new FillViewport(main.WIDTH,main.HEIGHT));
        menuCamera.position.set(main.WIDTH/2,main.HEIGHT/2,0);
        BG.getCamera().position.set(main.WIDTH/2,main.HEIGHT/2,0);
        //disableAds = new UIButton();

        exit = new UIButton(Exit,new Texture("exitButtonP.png")){
            @Override
            public void onClick() {
                super.onClick();
                Gdx.app.exit();
                exit.dispose();
            }
        };
        exit.sound = true;
        sett = new UIButton(Sett,new Texture("settButtonP.png")){
            @Override
            public void onClick() {
                super.onClick();
                main.setScreen(new SettingsMenu(main));
                sett.dispose();
            }
        };
        sett.sound = true;
        go = new UIButton(Play,new Texture("playButtonP.png")){
            @Override
            public void onClick() {
                super.onClick();
                loading = true;
            }
        };
        go.sound = true;

        preview = new Image(Main.preview);
        BG.addActor(preview);
        preview.setBounds(0,0,1280,720);
        menu.addActor(exit);
        menu.addActor(sett);
        menu.addActor(go);
        for(UIButton i : profiles) menu.addActor(i);
        menu.addActor(profilesText);


        loadingScreen = new Image(new Texture("loadingScreen.png"));
        loading = false;

        BG.addActor(loadingScreen);
        loadingScreen.setBounds(0,-720,1280,720);


        go.setBounds(35,main.HEIGHT/2-128,256,256);
        sett.setBounds(15,main.HEIGHT/2+140,192,192);
        //exit.setBounds(15,640-128-192-20,192,192);
        exit.setBounds(15,main.HEIGHT/2-20-192-128,192,192);
        Gdx.input.setInputProcessor(menu);


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        BG.draw();
        BG.act();
        if (loading) {
            loadingScreen.setPosition(0, loadingScreen.getY() + 40);
            if (loadingScreen.getY() >= 40) {
                loading = false;
                main.setScreen(new game(main));
                main.preview.dispose();
                go.dispose();
            }
        }else{
            menu.draw();
            menu.act();
        }

    }


    @Override
    public void resize(int width, int height){
        BG.getViewport().update(width,height);
        menu.getViewport().update(width,height);
        //preview.setBounds(0,0,menu.getViewport().getScreenWidth(),menu.getViewport().getScreenHeight());
        System.out.println(menu.getViewport().getScreenWidth()+" "+menu.getViewport().getScreenHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
