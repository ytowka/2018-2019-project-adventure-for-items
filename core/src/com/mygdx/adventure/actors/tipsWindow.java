package com.mygdx.adventure.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.mygdx.adventure.game;

public class tipsWindow extends Actor{
    public String text = "this future tip\nasd";
    public UIText textUI;
    Texture frame;
    public boolean drawText = false;
    public boolean opened = false;
    public tipsWindow(){
        setBounds(10,720,296,158);
        frame = new Texture("tipsWindow.png");
        textUI = new UIText(19,"fonts/UI.ttf",text,Color.BLACK);
        textUI.setPosition(20,720-20);
        game.UI.addActor(this);
    }

    public void open1(){
        if(!opened){
            MoveByAction move = new MoveByAction(){
                @Override
                protected void end() {
                    super.end();
                    opened = true;
                }
            };
            move.setDuration(0.15f);
            move.setAmountY(-168);
            addAction(move);
        }
    }
    public void open(){
        //setPosition(15,720-168);
        if(!opened){
            MoveByAction move = new MoveByAction(){
                @Override
                protected void end() {
                    super.end();
                    drawText = true;
                    //game.UI.addActor(textUI);
                }
            };
            move.setDuration(0.2f);
            move.setAmountY(-168);
            addAction(move);
        }
        opened = true;

    }
    public void close(){
        //setPosition(15,720);
        //textUI.remove();
        if(opened){
            MoveByAction move = new MoveByAction();
            move.setAmountY(168);
            move.setDuration(0.2f);
            addAction(move);
        }
        opened = false;
        drawText = false;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(frame,getX(),getY(),getWidth(),getHeight());
        if(drawText){
            textUI.draw(batch,parentAlpha);
        }
    }
}
