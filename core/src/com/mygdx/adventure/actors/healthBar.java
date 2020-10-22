package com.mygdx.adventure.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class healthBar extends Actor {
    public Texture healthFrameTexture;
    public Texture healthFillTexture;
    public TextureRegion healthFill;


    public healthBar(){
        healthFrameTexture = new Texture("moveButtons/healthFrame.png");
        healthFillTexture = new Texture("moveButtons/healthFill.png");
        healthFill = new TextureRegion(healthFillTexture);
        fillsize = getWidth();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
    float fillsize;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(healthFrameTexture,getX(),getY(),getWidth(),getHeight());
        batch.draw(healthFill,getX(),getY(),fillsize,getHeight());
        super.draw(batch, parentAlpha);
    }
    public void update(float maxValue,float value){
        fillsize = getWidth() / maxValue * value;
        healthFill.setRegionWidth((int)((float)healthFillTexture.getWidth()/maxValue*value));

    }
    public void setTextures(Texture fill,Texture frame){
        healthFrameTexture = frame;
        healthFillTexture = fill;
        healthFill = new TextureRegion(healthFillTexture);
    }
}
