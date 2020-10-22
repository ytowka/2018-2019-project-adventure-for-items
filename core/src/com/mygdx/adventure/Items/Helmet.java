package com.mygdx.adventure.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.adventure.Item;
import com.mygdx.adventure.actors.Map;
import com.mygdx.adventure.entity;
import com.mygdx.adventure.game;

import javax.swing.text.PlainDocument;

public class Helmet extends Item {
    public int protection;

    public Helmet(Texture texture, int Rare, String name,int protection,int quality,String description, Map map) {
        super(texture, Rare, name,quality,description,map);
        this.protection = protection;

    }
    public Helmet(TextureRegion texture, int Rare, String name, int protection, int quality, String description, Map map) {
        super(texture, Rare, name,quality,description,map);
        this.protection = protection;

    }
    @Override
    public void onMainSlot() {
        super.onMainSlot();
    }

    @Override
    public void onMoveFromMainSlot() {
        getInventory().Player.damageReduce-=protection;
        super.onMoveFromMainSlot();

    }

    @Override
    public void onMoveToMainSlot() {
        getInventory().Player.damageReduce+=protection;
        if(textureR != null){
            game.Player.helmet.setRegion(textureR);
        }else getInventory().Player.helmet.setTexture(texture);
        super.onMoveToMainSlot();

    }
    public void onAttack(entity attackingEntity){

    }

    @Override
    public void MainSlot() {
        getInventory().HelmetSlot(this);
    }
    @Override
    public String info(){
        return "Название: "+name+"\n"+"редкость: "+rare+"\n"+"уменьшение урона на "+protection+"\n"+"ударов осталось: "+usesRemain+"\n"+"особенность: "+"\n"+description;
    }
}
