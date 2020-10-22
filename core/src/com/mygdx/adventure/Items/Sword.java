package com.mygdx.adventure.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.adventure.Item;
import com.mygdx.adventure.actors.Map;
import com.mygdx.adventure.game;

public class Sword extends Item {
    public int Damage;
    public int delay;
    SpeedThread delayThread;
    public int knockBack = 400;

    public boolean canAttack = true;
    public Sword(Texture texture, int Rare,int Damage,int speed,String name,int uses, String description, Map map) {
        super(texture, Rare,name,uses,description,map);
        this.Damage = Damage;
        delay = speed;
    }
    public Sword(TextureRegion texture, int Rare, int Damage, int speed, String name, int uses, String description, Map map) {
        super(texture, Rare,name,uses,description,map);
        this.Damage = Damage;
        delay = speed;
    }
    @Override
    public void use(){
        if(canAttack){
            canAttack = false;
            getInventory().infoText.text = info();
            if(game.Player.currTarget != null){
                usesRemain--;
                game.Player.currTarget.giveDamage(Math.round(Damage*game.Player.playerDamageIncrease));
                System.out.println("damage: "+Math.round(Damage*game.Player.playerDamageIncrease));
                game.Player.currTarget.body.applyForceToCenter(new Vector2(knockBack*game.Player.direction,knockBack),false);
            }
            delayThread = new SpeedThread();
            delayThread.start();
            getInventory().Player.attack();
        }
    }
    @Override
    public void MainSlot() {
        getInventory().SwordSlot(this);
    }
    public String info(){
        return "Название: "+name+"\n"+"редкость: "+rare+"\n"+"использований осталось: "+usesRemain+"\n"+"урон: "+Damage+"\n"+"задержка: "+delay+"\n" +"особенность: "+"\n"+description;
    }
    public class SpeedThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            canAttack = true;
        }
    }

    @Override
    public void onMoveToMainSlot() {
        super.onMoveToMainSlot();
        getInventory().Player.attack.setFrameDuration(delay/1000f/4f);
    }
}
