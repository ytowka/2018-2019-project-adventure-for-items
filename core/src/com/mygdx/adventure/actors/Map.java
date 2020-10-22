package com.mygdx.adventure.actors;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.adventure.Collision;
import com.mygdx.adventure.Item;
import com.mygdx.adventure.entity;
import com.mygdx.adventure.game;
import com.mygdx.adventure.myGame;

import java.util.ArrayList;


public class Map{
    public float scale = 1/16f;
    public int id;
    public Texture sleepIcon = new Texture("map/sleepIcon.png");

    public OrthogonalTiledMapRenderer renderer;
    public TiledMap map;
    public OrthographicCamera camera;
    public OrthographicCamera bgCamera;
    public Texture bgImage = new Texture("map/sky.jpg");
    public SpriteBatch bgBatch;
    public Sound runSound = game.main.grassRun;
    public Music music = game.main.anotherMusic;

    public int[] fLayers;
    public int[] bLayers;

    public MapObjects collision;
    public Body body;
    groundedChecker pgc;

    public Vector2[] enter;
    public Transition[] transitions;
    public float endX;

    public tip[] tips;

    public String file;
    public ArrayList<Item> items = new ArrayList<Item>();
    public ArrayList<entity> entities = new ArrayList<entity>();
    public class sleepIcon extends Actor{
        public sleepIcon(float x,float y){
            addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    game.Player.sleep();
                    return super.touchDown(event, x, y, pointer, button);

                }
            });
            setPosition(x,y);
            game.stage.addActor(this);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(sleepIcon,getX(),getY(),2,4);
        }
    }
    public UIButton[] saveButtons;

    public Map(String file,OrthographicCamera camera,int[] frontLayers,int[] backLayers){
        id = myGame.maps.size();

        this.camera = camera;
        bgCamera = new OrthographicCamera(1280,720);
        map = new TmxMapLoader().load(file);
        renderer = new OrthogonalTiledMapRenderer(map,scale);
        fLayers = frontLayers;
        bLayers = backLayers;

        collision = map.getLayers().get("collision").getObjects();
        pgc = new groundedChecker();

        bgBatch = new SpriteBatch();

        createEnterPoints();
        transitions = new Transition[enter.length];
        createSavePoint();
        //body = Collision.tiledMapBody(this);

        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        endX = ground.getWidth()*ground.getTileWidth()*scale;
        //System.out.println(endX);

        myGame.maps.add(this);
        createTips();
        this.file = file;
    }
    public Map(String file,OrthographicCamera camera,int[] frontLayers,int[] backLayers,Music music){
        id = myGame.maps.size();

        this.camera = camera;
        bgCamera = new OrthographicCamera(1280,720);
        map = new TmxMapLoader().load(file);
        renderer = new OrthogonalTiledMapRenderer(map,scale);
        fLayers = frontLayers;
        bLayers = backLayers;

        collision = map.getLayers().get("collision").getObjects();
        pgc = new groundedChecker();

        bgBatch = new SpriteBatch();

        createEnterPoints();
        transitions = new Transition[enter.length];
        createSavePoint();
        //body = Collision.tiledMapBody(this);

        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        endX = ground.getWidth()*ground.getTileWidth()*scale;
        //System.out.println(endX);

        this.music = music;

        myGame.maps.add(this);
        createTips();
        this.file = file;
    }
    public Map(String file,OrthographicCamera camera,int[] frontLayers,int[] backLayers,Sound runSound){
        id = myGame.maps.size();

        this.camera = camera;
        bgCamera = new OrthographicCamera(1280,720);
        map = new TmxMapLoader().load(file);
        renderer = new OrthogonalTiledMapRenderer(map,scale);
        fLayers = frontLayers;
        bLayers = backLayers;
        this.runSound = runSound;

        collision = map.getLayers().get("collision").getObjects();
        pgc = new groundedChecker();

        bgBatch = new SpriteBatch();

        createEnterPoints();
        transitions = new Transition[enter.length];
        createSavePoint();
        //body = Collision.tiledMapBody(this);

        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        endX = ground.getWidth()*ground.getTileWidth()*scale;
        //System.out.println(endX);

        myGame.maps.add(this);
        createTips();
        this.file = file;
    }
    public Map(String file, OrthographicCamera camera, int[] frontLayers, int[] backLayers, Sound runSound, Music music){
        id = myGame.maps.size();

        this.camera = camera;
        bgCamera = new OrthographicCamera(1280,720);
        map = new TmxMapLoader().load(file);
        renderer = new OrthogonalTiledMapRenderer(map,scale);
        fLayers = frontLayers;
        bLayers = backLayers;
        this.runSound = runSound;


        this.music = music;

        collision = map.getLayers().get("collision").getObjects();
        pgc = new groundedChecker();

        bgBatch = new SpriteBatch();

        createEnterPoints();
        transitions = new Transition[enter.length];
        createSavePoint();
        //body = Collision.tiledMapBody(this);

        TiledMapTileLayer ground = (TiledMapTileLayer) map.getLayers().get("ground");
        endX = ground.getWidth()*ground.getTileWidth()*scale;
        //System.out.println(endX);

        myGame.maps.add(this);
        createTips();
        this.file = file;


    }
    public void drawBG(){
        bgBatch.setProjectionMatrix(bgCamera.combined);
        bgBatch.begin();
        bgBatch.draw(bgImage,-640,-320,1280,720);
        bgBatch.end();
    }
    public void drawF() {
        renderer.setView(camera);
        renderer.render(fLayers);
    }
    public void drawB() {
        camera.update();
        renderer.setView(camera);
        renderer.render(bLayers);
    }
    public void draw() {
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }
    public void createEnterPoints(){
        MapObjects enter = map.getLayers().get("enter").getObjects();
        this.enter = new Vector2[enter.getCount()];
        for(int i = 0; i<enter.getCount();i++){
            this.enter[i] = new Vector2(Float.valueOf(enter.get(i).getProperties().get("x").toString())*scale,Float.valueOf(enter.get(i).getProperties().get("y").toString())*scale);
        }
        for(Vector2 i : this.enter){
        }
    }
    public void createSavePoint(){
        MapObjects points = map.getLayers().get("saves").getObjects();
        saveButtons = new UIButton[points.getCount()];
        for(int i = 0; i<points.getCount(); i++){

            saveButtons[i] = new UIButton(sleepIcon,sleepIcon){
                @Override
                public void onClick() {
                    super.onClick();
                    game.Player.sleep();
                }
            };
            saveButtons[i].setBounds(Float.valueOf(points.get(i).getProperties().get("x").toString())*scale,Float.valueOf(points.get(i).getProperties().get("y").toString())*scale,2,4);
        }
    }
    public void load(){
        map = new TmxMapLoader().load(file);
       game.Player.getStage().clear();
       game.Player.map = this;
       game.stage.addActor(game.Player);
       game.Player.runSound = runSound;
       game.Player.runSoundId = runSound.loop();
       //body = Collision.tiledMapBodies(this);
        body = Collision.tiledMapBody(this);
        game.gameWorld.setContactListener(pgc);
        if(myGame.currentMusic != music){
            myGame.currentMusic.stop();
            myGame.currentMusic = music;
            myGame.currentMusic.play();
            myGame.currentMusic.setLooping(true);
            myGame.currentMusic.setVolume(0.33f);
        }
       createTransitions();
       for(Item i : items){
           i.spawn();
       }
       for(entity i : entities){
           i.spawn();
           game.stage.addActor(i);
       }
       for(UIButton i : saveButtons){
           game.stage.addActor(i);
       }
       //

    }
    public void Enter(int pointIndex){
        map = new TmxMapLoader().load(file); // загружается новая карта
        game.Player.getStage().clear(); // очищается сцена
        game.Player.map = this;
        game.stage.addActor(game.Player); // добавляется игрок
        game.Player.runSound = runSound;
        game.Player.runSoundId = runSound.loop();

        if(myGame.currentMusic != music){
            myGame.currentMusic.stop();
            myGame.currentMusic = music;
            myGame.currentMusic.play();
            myGame.currentMusic.setLooping(true);
            myGame.currentMusic.setVolume(0.33f);
        }

        game.currentMap = this; // меняется парамеметр "текущая карта"
        body = Collision.tiledMapBody(this); // создается физическая модель карты
        game.gameWorld.setContactListener(pgc); // устонавливается обработчик касаний
        if(enter[pointIndex].x > 50){
            game.Player.collosionRect.setTransform(enter[pointIndex].x-2,enter[pointIndex].y,0);
        }else{
            game.Player.collosionRect.setTransform(enter[pointIndex].x+2,enter[pointIndex].y,0);
        }
        createTransitions(); // создаются переходы
        for(Item i : items){ // добавляются предметы
           if(i.getInventory() == null){ i.spawn(); System.out.println(i.name);}
       }
        for(entity i : entities){ // добавляются враги
            i.spawn();
            game.stage.addActor(i);
        }
        for(UIButton i : saveButtons){ // добавляются точки сохранений
            game.stage.addActor(i);
        }
        game.paperMap.newMap(map); // устанавливается новая карта игрока
    }
    public void Exit(){
        game.Player.runSound.stop(game.Player.runSoundId);
        game.gameWorld.setContactListener(null); // удаляется обработчик касаний
        for(entity i : entities){
            i.destroy();
        }
        for(Fixture i : body.getFixtureList()){ // удаляется физическая модель карты
            body.destroyFixture(body.getFixtureList().first());
        }
        while(body.getFixtureList().size > 0){
            body.destroyFixture(body.getFixtureList().first());
        }
        deleteTranstitions(); //удаляются переходы
        map.dispose(); // карта выгружается
    }
    public void createTransitions(){}
    public void deleteTranstitions(){
        for(Transition i : transitions){
            i = null;
        }
    }
    public void updateTransitions(){
        for(Transition a : transitions){
            if(a != null){
                Map c = a .update();
                if(c != null){
                    Exit();
                    c.Enter(a.newMapPointIndex);
                    return;
                }
            }

        }
    }
    public void setItemsPos(){
        //System.out.println(map.getLayers().get(0).getName());
        if(map.getLayers().size() > 10){
            //System.out.println("mapid: "+id);
            MapLayer itemsLayer = map.getLayers().get("items");
                MapObjects itemsPoss = itemsLayer.getObjects();
                for(int i = 0; i < itemsPoss.getCount(); i++){
                    myGame.items.get(Integer.valueOf(itemsPoss.get(i).getProperties().get("iid").toString())).setPosition(Float.valueOf(itemsPoss.get(i).getProperties().get("x").toString())*scale,Float.valueOf(itemsPoss.get(i).getProperties().get("y").toString())*scale);
            }
        }

    }
    public void setMobPos(){
        if(map.getLayers().size() > 11){
            //System.out.println("mapid: "+id);
            MapLayer itemsLayer = map.getLayers().get("mobs");
            MapObjects itemsPoss = itemsLayer.getObjects();
            for(int i = 0; i < itemsPoss.getCount(); i++){
                entity j = myGame.entities.get(Integer.valueOf(itemsPoss.get(i).getProperties().get("iid").toString()));
                j.mainX = (Float.valueOf(itemsPoss.get(i).getProperties().get("x").toString())*scale);
                j.mainY = (Float.valueOf(itemsPoss.get(i).getProperties().get("y").toString())*scale);
            }
        }

    }
    public void createTips(){
        MapObjects points = map.getLayers().get("tips").getObjects();
        tips = new tip[points.getCount()];
        for(int i = 0; i < points.getCount(); i++){
            tips[i] = new tip(Float.valueOf(points.get(i).getProperties().get("x").toString())*scale,Float.valueOf(points.get(i).getProperties().get("y").toString())*scale,points.get(i).getProperties().get("text").toString());
        }
    }
    public tip updateTips(){
        for(tip i : tips){
            if(i.isPlayerNear()){
                return i;
            }
        }
        game.Player.grounded = isPlayerGrounded;
        return null;
    }
    class groundedChecker implements ContactListener{
        @Override
        public void beginContact(Contact contact) { }
        @Override
        public void endContact(Contact contact) {
            if(contact.getFixtureB() == game.Player.groundFixture || contact.getFixtureA() == game.Player.groundFixture){
                //System.out.println("grounded");
                game.Player.grounded = false;
            }
        }
        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
            if(contact.getFixtureB() == game.Player.groundFixture || contact.getFixtureA() == game.Player.groundFixture){
                //System.out.println("grounded");
                game.Player.jumpsUsed = 0;
                game.Player.grounded = true;
            }
        }
        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) { }
    }
    public boolean isPlayerGrounded = false;
    public class tip{
        public String text;
        float x;
        float y;
        public tip(float x,float y,String text){
            this.x = x;
            this.y = y;
            this.text = text;
        }
        public boolean isPlayerNear(){
            if(Math.abs(game.Player.collosionRect.getPosition().x-x)<2.5f && Math.abs(game.Player.collosionRect.getPosition().y-y)<3f){
                return true;
            }
            return false;
        }
    }
    public class Transition{
        public Map update(){
            if(Math.abs(game.Player.collosionRect.getPosition().y-enter[pointIndex].y) < 3){
                if(isR){
                    if( game.Player.collosionRect.getPosition().x>enter[pointIndex].x ){
                        //System.out.println(enter[pointIndex].toString()+" right");
                        return newMap;
                    }else return null;
                }else{
                    if( game.Player.collosionRect.getPosition().x<enter[pointIndex].x){
                       // System.out.println(enter[pointIndex].toString()+" left");
                        return newMap;
                    }else return null;
                }
            }
            return null;
        }
        public boolean isR =false;
        public int newMapPointIndex;
        public int pointIndex;
        public Map newMap;
        public Transition(int pointIndex,Map map,int newMapPointIndex){
            this.newMapPointIndex = newMapPointIndex;
            if(enter[pointIndex].x > 20) isR = true;
            newMap = map;
            this.pointIndex = pointIndex;
        }
    }

}
