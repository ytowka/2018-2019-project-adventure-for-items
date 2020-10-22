package com.mygdx.adventure.Items;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.adventure.Resources;
import com.mygdx.adventure.game;
import com.mygdx.adventure.myGame;

public class fullMap {
    Stage mapStage;
    public boolean open = false;
    public Image paperMap;
    public Image playerIcon;
    OrthogonalTiledMapRenderer tmr;
    public float scale = 1/8.5f;
    public fullMap(){
        mapStage = new Stage(new ExtendViewport(myGame.WIDTH,myGame.HEIGHT));
        mapStage.getCamera().position.set(myGame.WIDTH/2-140-20,0,0);
        paperMap = new Image(new Texture("papaerMap.png"));
        paperMap.setBounds(140,110,1000,500);
        playerIcon = new Image(new Texture("player/playerMapIcon.png"));
        playerIcon.setBounds(1,1,10,10);
        mapStage.addActor(playerIcon);
        tmr = new OrthogonalTiledMapRenderer(game.currentMap.map,scale);

    }

    public void update(){
        if(open){
            tmr.setView((OrthographicCamera)mapStage.getCamera());
            tmr.render();
            mapStage.draw();
            mapStage.act();
            playerIcon.setPosition(game.Player.collosionRect.getPosition().x*myGame.coef*scale/2-5,game.Player.collosionRect.getPosition().y*myGame.coef*scale/2-5);

        }
    }
    public void newMap(TiledMap map){
        tmr.dispose();
        tmr = new OrthogonalTiledMapRenderer(map,scale);
    }
}
