package com.mygdx.adventure.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class UIText extends Actor {

    BitmapFont font;
    String fontFile = "fonts/UI.ttf";
    int size = 8;
    public String text;
    Color color;

    public static final String ruCharacters = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}|%";




    public UIText(int size,String file,String text,Color color){
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(file));
        FreeTypeFontGenerator.FreeTypeFontParameter fontSettings = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontSettings.color = color;
        this.color = color;
        fontSettings.characters = ruCharacters;
        fontSettings.size = size;
        font = gen.generateFont(fontSettings);
        this.size = size;
        fontFile = file;
        this.text = text;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch,text,getX(),getY());

        super.draw(batch, parentAlpha);
    }
    public void setBorder(){

    }
}
