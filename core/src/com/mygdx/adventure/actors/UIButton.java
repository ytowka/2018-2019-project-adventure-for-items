package com.mygdx.adventure.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.adventure.menu;

public class UIButton extends Actor {
    public Sprite sprite;
    public Texture pressed;
    public Texture released;
    public Sound press;
    public boolean sound = false;

    private boolean isPressed;

    public UIButton(final Texture released, final Texture pressed){
        this.released = released;
        this.pressed = pressed;
        sprite = new Sprite(released);
        setBounds(0,0,released.getWidth(),released.getHeight());
        sprite.setBounds(getX(),getY(),getWidth(),getHeight());
        press = menu.main.click;
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                sprite.setTexture(pressed);
                onTouched();
                if(sound) press.play();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = false;
                sprite.setTexture(released);
                onTouchUpped();
                if(sound) press.play();
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void clicked(InputEvent event, float x, float y){
                onClick();
            }

        });


    }
    public void onClick(){}
    @Override
    public void act(float delta) {
        sprite.setBounds(getX(),getY(),getWidth(),getHeight());
        //sprite.setTexture(released);
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        super.draw(batch, parentAlpha);
    }
   public boolean isPressed(){
        return isPressed;
   }

    public void clicked(InputEvent event, float x, float y){

    }
    public void onTouched(){

    }
    public void onTouchUpped(){

    }
    public void dispose(){
        pressed.dispose();
        released.dispose();
    }
    public void setTextures(Texture released,Texture pressed){
        this.pressed = pressed;
        this.released = released;
        sprite.setTexture(released);
    }
}
