package com.mygdx.adventure;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.adventure.actors.Map;


public class Item extends Actor{
    public int id;

    public Texture texture;
    public TextureRegion textureR;
    public String name;
    public String description="";
    public int usesRemain;
    public boolean isPlayerNear;

    public boolean inMainSlot = false;
    public boolean inInventory = false;
    public boolean dragging = false;
    private TextureData td;
    public Texture rareTxr;

    public int rare; // 1 - common, 2 - rare, 3 - epic, 4 - legendary

    private Inventory inventory;

    boolean canPickUp = true;

    public Map map;

    public Item(Texture texture, int Rare, String name, int uses, String description,Map map){
        this.texture = texture;
        rare = Rare;
        usesRemain = uses;
        this.description = description;
        this.name = name;
        id = myGame.items.size();
        //System.out.println(myGame.items.size());
        setBounds(0,0,2f,2f);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pickup(game.Player.inventory);
                super.clicked(event, x, y);

            }
        });


        char[] descriptionChar = description.toCharArray();
        description = "";
        for(int i = 1;i<descriptionChar.length+1;i++){
            description+= descriptionChar[i-1];
            if(i%27==0){
                description+="\n";
            }
        }
        td = texture.getTextureData();
        this.description = description;
        myGame.items.add(this);
        this.map = map;
        rareTxr = game.main.raresTxrs[Rare-1];
    }
    public Item(TextureRegion icon, int Rare, String name, int uses, String description,Map map){
        this.textureR = icon;
        rare = Rare;
        usesRemain = uses;
        this.description = description;
        this.name = name;
        id = myGame.items.size();
        //System.out.println(myGame.items.size());
        setBounds(0,0,1.75f,1.75f);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pickup(game.Player.inventory);
                super.clicked(event, x, y);

            }
        });


        char[] descriptionChar = description.toCharArray();
        description = "";
        for(int i = 1;i<descriptionChar.length+1;i++){
            description+= descriptionChar[i-1];
            if(i%27==0){
                description+="\n";
            }
        }
        this.description = description;
        myGame.items.add(this);
        this.map = map;
        rareTxr = game.main.raresTxrs[Rare-1];
    }
    public String info(){
        return "Название: "+name+"\n"+"редкость: "+rare+"\n"+"использований осталось: "+usesRemain+"\n"+"особенность: "+"\n"+description;
    }

    public void Drop(){
        setSize(1.75f,1.75f);
        if(inMainSlot){
            MainSlot();
        }
        inInventory = false;
        this.setPosition(inventory.Player.collosionRect.getPosition().x-getWidth()/80,inventory.Player.getY());
        //this.setPosition(0,0);
        map = myGame.maps.get(game.currentMap.id);
        map.items.add(this);
        canPickUp = true;
        inventory.removeItem(this);
        game.stage.addActor(this);
        inventory = null;
        clearListeners();
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pickup(game.Player.inventory);
            }
        });

    }
    public void spawn(){
        setSize(1.75f,1.75f);
        inInventory = false;
        //this.setPosition(0,0);

        canPickUp = true;
        game.Player.getStage().addActor(this);
        inventory = null;
        clearListeners();
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pickup(game.Player.inventory);
            }
        });
        //map = game.Player.map;
    }
    public void addToMap(){
        map.items.add(this);
    }
    public void save(Preferences prefs){
        if(map != null){
            prefs.putInteger("map"+id,map.id);
        }else if(inMainSlot){
            prefs.putInteger("map"+id,-2);
        }else{
            prefs.putInteger("map"+id,-1);
        }
        prefs.putFloat("x"+id,getX());
        prefs.putFloat("y"+id,getY());
        prefs.putInteger("count"+id,usesRemain);

    }
    public void pickup(Inventory inventory){
        if(inventory.haveEmptyPlace() && canPickUp && isPlayerNear){
            inInventory = true;
            inventory.addItem(this);
            this.inventory = inventory;
            canPickUp = false;
            clearListeners();
            setSize(69,69);
            map = null;

            addListener(new ClickListener(){
                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    super.touchDragged(event,x,y,pointer);
                    if(getInventory().isOpen) {
                        setPosition(getX()+x-getWidth()/2,getY()+y-getHeight()/2);

                    }
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(getInventory().isOpen){
                        dragging = true;
                    }
                    return super.touchDown(event, x, y, pointer, button);

                }
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    getInventory().infoText.text = info();
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    dragging = false;
                    if(!(getX()+getWidth()/2>640-512 && getX()+getWidth()/2<640+512 && getY()+getHeight()/2>360-114+150 && getY()+getHeight()/2<360-114+150+256) && getInventory().isOpen){
                        Drop();
                    }
                    if(getX()+getWidth()/2>640-512+314 && getX()+getWidth()/2<640+712 && getY()+getHeight()/2>360-114+150 && getY()+getHeight()/2<360-114+150+146 && !inMainSlot && getInventory().isOpen){
                        MainSlot();
                    }
                    if(getX()+getWidth()/2>640-512 && getX()+getWidth()/2<640+512 && getY()+getHeight()/2>360-114+150+146 && getY()+getHeight()/2<360-114+150+256 && inMainSlot && getInventory().isOpen){
                        MainSlot();
                    }
                }
            });
        }
    }
    public void forceAdd(final Inventory inventory){
        if(inventory.haveEmptyPlace()) {
            inInventory = true;
            inventory.addItem(this);
            this.inventory = inventory;
            canPickUp = false;
            clearListeners();
            setSize(69, 69);
            map = null;

            addListener(new ClickListener() {
                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    super.touchDragged(event, x, y, pointer);
                    if (inventory.isOpen) {
                        setPosition(getX() + x - getWidth() / 2, getY() + y - getHeight() / 2);

                    }
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (inventory.isOpen) {
                        dragging = true;
                    }
                    return super.touchDown(event, x, y, pointer, button);

                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    getInventory().infoText.text = info();
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    dragging = false;
                    if (!(getX() + getWidth() / 2 > 640 - 512 && getX() + getWidth() / 2 < 640 + 512 && getY() + getHeight() / 2 > 360 - 114 + 150 && getY() + getHeight() / 2 < 360 - 114 + 150 + 256) && getInventory().isOpen) {
                        Drop();
                    }
                    if (getX() + getWidth() / 2 > 640 - 512 + 314 && getX() + getWidth() / 2 < 640 + 712 && getY() + getHeight() / 2 > 360 - 114 + 150 && getY() + getHeight() / 2 < 360 - 114 + 150 + 146 && !inMainSlot && getInventory().isOpen) {
                        MainSlot();
                    }
                    if (getX() + getWidth() / 2 > 640 - 512 && getX() + getWidth() / 2 < 640 + 512 && getY() + getHeight() / 2 > 360 - 114 + 150 + 146 && getY() + getHeight() / 2 < 360 - 114 + 150 + 256 && inMainSlot && getInventory().isOpen) {
                        MainSlot();
                    }
                }
            });
        }
    }
    public void onMainSlot(){ }

    public void onMoveFromMainSlot(){
        inMainSlot = false;
    }
    public void onMoveToMainSlot() {
        inMainSlot = true;
    }

    public Inventory getInventory(){
        return inventory;
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
            batch.draw(rareTxr,getX(),getY(),getWidth(),getHeight());
            if(textureR != null)batch.draw(textureR,getX(),getY(),getWidth(),getHeight());
            else batch.draw(texture,getX(),getY(),getWidth(),getHeight());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(inMainSlot){
            onMainSlot();
        }
        if(usesRemain<1 && inInventory){
            runOut();
        }
        if(this.getStage() != game.UI){
            if(Math.abs(getX()+getWidth()/2-game.Player.collosionRect.getPosition().x)<4 && Math.abs(getY()+getHeight()/2-game.Player.collosionRect.getPosition().y)<3){
                isPlayerNear = true;
            }else{
                isPlayerNear = false;
            }

        }

    }
    public void use(){
        usesRemain-=1;
        getInventory().infoText.text = info();
    }
    public void runOut(){
        getInventory().infoText.text = "";
        Drop();
        canPickUp = false;
        this.remove();

    }
    public void dispose(){
        texture.dispose();
    }
    public void load(){
        texture.load(td);
    }
    public void MainSlot(){
        inventory.SpecSlot(this);
    }
    public void firtsAdd(){
        map.items.add(this);
    }
}
