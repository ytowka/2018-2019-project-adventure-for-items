package com.mygdx.adventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.adventure.actors.UIButton;
import com.mygdx.adventure.actors.UISwitcher;
import com.mygdx.adventure.actors.UIText;

public class SettingsMenu implements Screen {
    public myGame main;
    private BitmapFont head;

    Stage settingsMenu;
    OrthographicCamera settingsCamera;

    UIButton back;

    UISwitcher devolopMode;
    UIText devoloperModeText;

    UIText clearDataTxt;
    UIButton clearDataBtn;
    UIText versionLabel;

    public Stage creditsInfo;


    public  SettingsMenu(final myGame Main){
        main = Main;


        settingsCamera = new OrthographicCamera(main.WIDTH,main.HEIGHT);
        settingsMenu = new Stage(new FitViewport(main.WIDTH,main.HEIGHT,settingsCamera));
        settingsCamera.position.set(main.WIDTH/2,main.HEIGHT/2,0);

        back = new UIButton(main.back,main.back);
        back.setBounds(1,main.HEIGHT-128-20,128,128);
        back.sound = true;

        devolopMode = new UISwitcher(new Texture("moveButtons/switcher.png"));
        devolopMode.setBounds(main.WIDTH/2+100,main.HEIGHT-100,128,64);

        devoloperModeText = new UIText(47,"fonts/UI.ttf","режим разработчика",Color.BLACK);
        devoloperModeText.setBounds(main.WIDTH/2-310,main.HEIGHT-50,100,100);

        clearDataBtn = new UIButton(new Texture("moveButtons/contentButton.png"),new Texture("moveButtons/contentButtonP.png")){
            @Override
            public void onClick() {
                super.onClick();
                myGame.clearData();
            }
        };
        clearDataBtn.setSize(200,100);
        clearDataBtn.setPosition(main.WIDTH/2+65,main.HEIGHT-250);

        clearDataTxt = new UIText(47,"fonts/UI.ttf","удалить сохранение",Color.BLACK);
        clearDataTxt.setPosition(main.WIDTH/2-320,main.HEIGHT-170);


        versionLabel = new UIText(27,"fonts/UI.ttf","Версия: "+myGame.version,Color.BLACK);
        versionLabel.setPosition(main.WIDTH/2-128,35);

        settingsMenu.addActor(back);
        settingsMenu.addActor(devolopMode);
        settingsMenu.addActor(devoloperModeText);
        settingsMenu.addActor(versionLabel);
        settingsMenu.addActor(clearDataBtn);
        settingsMenu.addActor(clearDataTxt);

        devolopMode.enabled = main.devoloperMode;

        Gdx.input.setInputProcessor(settingsMenu);






    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        settingsMenu.draw();
        settingsMenu.act();

        main.devoloperMode = devolopMode.enabled;


        if(back.isPressed()){
            main.setScreen(new menu(main));
        }
    }

    @Override
    public void resize(int width, int height) {

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
