package com.mygdx.adventure.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.adventure.Item;
import com.mygdx.adventure.actors.Map;
import com.mygdx.adventure.actors.player;

public class food extends Item {
    int heathRegen;
    int duriataion = 1;
    public food(Texture texture, int rare, int duritation, int regenPerSecond, String name, int uses, String description, Map map){
        super(texture,rare,name,uses,description,map);
        this.heathRegen = regenPerSecond;
        this.duriataion = duritation;
    }
    public food(TextureRegion texture, int rare, int duritation, int regenPerSecond, String name, int uses, String description, Map map){
        super(texture,rare,name,uses,description,map);
        this.heathRegen = regenPerSecond;
        this.duriataion = duritation;
    }
    public boolean using = false;
    private void regenHealth(final player Player){
        class regenThread extends Thread{
            @Override
            public void run() {
                super.run();
                for (int i = 0;i<duriataion*2;i++){
                    if(Player.giveHealth(heathRegen/2)){

                    }
                    try{
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                using = false;

            }
        }
        using = true;
        regenThread regeneration= new regenThread();
        regeneration.start();
    }

    @Override
    public void use() {
        super.use();
        if(getInventory() != null){
            regenHealth(getInventory().Player);
        }
    }
    public void whileUsing(){ }
    @Override
    public void act(float delta) {
        super.act(delta);
        if(using) whileUsing();
    }

    @Override
    public void MainSlot() {
        getInventory().FoodSlot(this);
    }
    @Override
    public String info(){
        return "Название: "+name+"\n"+"редкость: "+rare+"\n"+"использваний ост.: "+usesRemain+"\n"+"регенерация в секунду: "+heathRegen+"\n"+"длительность: "+duriataion+"\n"+"особенность: "+"\n"+description;
    }
}
