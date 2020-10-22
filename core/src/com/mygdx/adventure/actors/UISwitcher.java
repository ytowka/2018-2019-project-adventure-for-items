package com.mygdx.adventure.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UISwitcher extends Actor {
    Texture sprite;
    Texture pressedSprite;

    private boolean flip = false;
    public boolean enabled = false;
    int k = 1;
    public UISwitcher(Texture texture){
        sprite = texture;
        setBounds(0,0,128,64);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(enabled){
                    onDisable();
                }else{
                    onEnable();
                }
                enabled = !enabled;
                onSwitch();
            }
        });
        flip = true;
    }
    public UISwitcher(Texture texture,Texture pressed){
        sprite = texture;
        pressedSprite = pressed;
        //setBounds(0,0,128,64);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                onClick();
            }
        });
    }
    public void onEnable(){}
    public void onDisable(){}
    public void onSwitch(){}
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!flip){
            if(enabled){
                batch.draw(pressedSprite,getX(),getY(),getWidth(),getHeight());
            }else{
                batch.draw(sprite,getX(),getY(),getWidth(),getHeight());
            }
        }else{
            batch.draw(sprite,getX()-getWidth()/2*k+getWidth()/2,getY(),getWidth()*k,getHeight());
        }


    }
    public void onClick(){
        if(enabled){
            onDisable();
        }else{
            onEnable();
        }
        enabled = !enabled;
        onSwitch();
    }
    @Override
    public void act(float delta){
        if(enabled) k = -1; else k = 1;
    }
}
