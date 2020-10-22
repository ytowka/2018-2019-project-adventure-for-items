package com.mygdx.adventure;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.mygdx.adventure.Items.*;
import com.mygdx.adventure.actors.UIText;
import com.mygdx.adventure.actors.player;

import java.util.ArrayList;

public class Inventory extends Actor{


    public float y;
    public float[] x;
    public float mainY;
    public float[] mainX;

    public ArrayList<Item> items;
    public food foodSlot;
    public Sword swordSlot;
    public Helmet helmetSlot;
    public Item specSlot;
    public UIText infoText;
    public Texture infoWindow;

    Texture texture;


    public boolean isOpen = false;

    public player Player;

    public Inventory(player Player){
        this.Player = Player;
        setBounds(640-512,720-102,1024,256);
        texture = new Texture("moveButtons/inventory.png");
        items = new ArrayList<Item>();
        y = getY()+155;

        int offset =98;
        x = new float[10];
        for(int i = 0;i<10;i++){
            x[i] = offset*i+26+getX()+5+6;
        }
        mainX = new float[4];
        for(int i = 0;i<4;i++){
            mainX[i] = (offset-3)*i+325+getX()+5+6;
        }
        infoWindow = new Texture("items/informationWindow.png");
        infoText = new UIText(27,"fonts/UI.ttf","",Color.BLACK);
        infoText.setPosition(getX()+334,getY()-256);

    }
    public void clearInv(){
        if(specSlot != null) specSlot.Drop();
        if(foodSlot != null) foodSlot.Drop();
        if(helmetSlot != null) helmetSlot.Drop();
        if(swordSlot != null) swordSlot.Drop();
        for(int i = 0;i<items.size();i++){
            items.get(i).Drop();
        }
        items.clear();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
        if(isOpen){
            batch.draw(infoWindow,getX()+314,getY()-390-5,398,390);
            infoText.draw(batch,parentAlpha);
        }
    }
    public void Open(boolean isOpen){
        this.isOpen=isOpen;
        MoveToAction open = new MoveToAction();
        MoveToAction close = new MoveToAction();

        open.setPosition(640-512,360-114+150);
        close.setPosition(640-512,720-114);

        open.setDuration(0.2f);
        close.setDuration(0.2f);

        if(!isOpen){
            //setPosition(640-512,720-114);
            addAction(close);
        }else{
            //setPosition(640-512,360-114+150);
            addAction(open);
        }
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        y = getY()+135;
        mainY = getY()+22;

        for(int i = 0;i<items.size();i++){
            if(!items.get(i).dragging){
                items.get(i).setPosition(x[i],y);
            }
        }
        if(swordSlot != null && !swordSlot.dragging){
                swordSlot.setPosition(mainX[0], mainY);
        }
        if(helmetSlot!= null&& !helmetSlot.dragging){
            helmetSlot.setPosition(mainX[1],mainY);
        }
        if(foodSlot != null&& !foodSlot.dragging){
            foodSlot.setPosition(mainX[2],mainY);
        }
        if(specSlot != null&& !specSlot.dragging){
            specSlot.setPosition(mainX[3],mainY);
        }
    }
    public void addItem(Item item){
            items.add(item);
            game.UI.addActor(item);
    }
    public boolean haveEmptyPlace(){
        return items.size()<10;
    }
    public void removeItem(Item item){
        items.remove(item);
    }

    public void SwordSlot(Sword item){
        if(item.inMainSlot){
            if(haveEmptyPlace()){
                addItem(item);
                item.onMoveFromMainSlot();
                swordSlot = null;
            }
        }else if(swordSlot != null){
            swordSlot.MainSlot();
            items.remove(item);
            swordSlot = item;
            item.onMoveToMainSlot();

        }else{
            items.remove(item);
            swordSlot = item;
            item.onMoveToMainSlot();
        }


    }
    public void HelmetSlot(Helmet item){
        if(item.inMainSlot){
            if(haveEmptyPlace()){
                items.add(item);
                item.onMoveFromMainSlot();
                helmetSlot = null;
            }
        }else if(helmetSlot != null){
            helmetSlot.MainSlot();
            items.remove(item);
            helmetSlot = item;
            item.onMoveToMainSlot();

        }else{
            items.remove(item);
            helmetSlot = item;
            item.onMoveToMainSlot();
        }
    }
    public void FoodSlot(food item){
        if(item.inMainSlot){
            if(haveEmptyPlace()){
                addItem(item);
                item.onMoveFromMainSlot();
                foodSlot = null;
            }
        }else if(foodSlot != null){
            foodSlot.MainSlot();
            items.remove(item);
            foodSlot = item;
            item.onMoveToMainSlot();

        }else{
            items.remove(item);
            foodSlot = item;
            item.onMoveToMainSlot();
        }
    }
    public void SpecSlot(Item item){
        if(item.inMainSlot){
            if(haveEmptyPlace()){
                addItem(item);
                item.onMoveFromMainSlot();
                specSlot = null;
            }
        }else if(specSlot != null){
            specSlot.MainSlot();
            items.remove(item);
            specSlot = item;
            item.onMoveToMainSlot();

        }else{
            items.remove(item);
            specSlot = item;
            item.onMoveToMainSlot();
        }
    }

}
