package com.mygdx.adventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.adventure.actors.Map;
import com.mygdx.adventure.actors.player;

import java.util.ArrayList;

public class myGame extends Game {
	private androidMethods ads;
	public static boolean Android = false;

	public androidMethods getAds(){
		return ads;
	}
	public void setAds(androidMethods ads) {
		this.ads = ads;
	}

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final float worldWidth = 40;
	public static final float worldHeight = 22.5f;
	public static final float coef = WIDTH/worldWidth;
	public static boolean devoloperMode = false;
	public static int devoloperMapId = 5;
	public static final String version = "0.1";

	public static boolean started = false;

	public static boolean saveData = true;

	public boolean disableAds = true;


	Texture back;
	public Sound button;

	public static int screenWidth;
	public static int screenHeight;

	public static player Player;

	public static short profileIndex = 0;

	public Music caveMusic;
	public Music anotherMusic;
	public Music snowMusic;
	public Music menuMusic;
	public static Music currentMusic;

	public static Preferences[] itemSave;
	public static Preferences[] playerSave;
	public static Preferences[] storySave;

	public static Preferences[] itemRespawn;
	public static Preferences[] playerRespawn;
	public static Preferences[] storyRespawn;

	public static Preferences generalPrefs;

	public static ArrayList<Item> items;
	public static ArrayList<Map> maps;
	public static ArrayList<entity> entities;

	public Texture itemMapTexture;
	public TextureRegion[][] itemMap;

	public Texture preview;


	public Sound entityHit;
	public Sound entityDie;
	public Sound click;

	public Sound grassRun;
	public Sound snowRun;

	public Texture[] raresTxrs = new Texture[4];
	@Override
	public void create () {
		//hello world!
		preview = new Texture("preview.png");
		click = Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg"));
		grassRun = Gdx.audio.newSound(Gdx.files.internal("sounds/run.ogg"));
		snowRun = Gdx.audio.newSound(Gdx.files.internal("sounds/snowSound.ogg"));

		caveMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/caveMusic.mp3"));
		snowMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/snowMusic.mp3"));
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menuMusic.mp3"));
		anotherMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/another.mp3"));

		currentMusic = menuMusic;
		currentMusic.setLooping(true);
		menuMusic.play();

		setScreen(new menu(this));
		back = new Texture("backButtonR.png");
		Gdx.input.setCatchBackKey(true);

		itemMapTexture = new Texture("items/itemMap.png");
		itemMap = TextureRegion.split(itemMapTexture,32,32);

		Player = new player(new Texture("player/playerStay0.png"),grassRun);
		Player.setWidth(4);
		Player.setHeight(4);
		items = new ArrayList<Item>();
		maps = new ArrayList<Map>();
		entities = new ArrayList<entity>();


		itemSave = new Preferences[3];
		playerSave = new Preferences[3];
		itemSave[0] = Gdx.app.getPreferences("items0");
		playerSave[0] = Gdx.app.getPreferences("player0");
		itemSave[1] = Gdx.app.getPreferences("items1");
		playerSave[1] = Gdx.app.getPreferences("player1");
		itemSave[2] = Gdx.app.getPreferences("items2");
		playerSave[2] = Gdx.app.getPreferences("player2");

		itemRespawn = new Preferences[3];
		playerRespawn = new Preferences[3];
		itemRespawn[0] = Gdx.app.getPreferences("itemsCP0");
		playerRespawn[0] = Gdx.app.getPreferences("playerCP0");
		itemRespawn[1] = Gdx.app.getPreferences("itemsCP1");
		playerRespawn[1] = Gdx.app.getPreferences("playerCP1");
		itemRespawn[2] = Gdx.app.getPreferences("itemsCP2");
		playerRespawn[2] = Gdx.app.getPreferences("playerCP2");

		generalPrefs = Gdx.app.getPreferences("general preferences");
		disableAds = generalPrefs.getBoolean("isAdsDisabled");

		started = playerSave[profileIndex].getBoolean("started");
		entityDie = Gdx.audio.newSound(Gdx.files.internal("sounds/entityDeath.ogg"));
		entityHit = Gdx.audio.newSound(Gdx.files.internal("sounds/entityDamage.ogg"));

		raresTxrs[3] = new Texture("items/rares/rare"+4+".png");
        raresTxrs[0] = new Texture("items/rares/rare"+1+".png");
        raresTxrs[1] = new Texture("items/rares/rare"+2+".png");
        raresTxrs[2] = new Texture("items/rares/rare"+3+".png");

        button = Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg"));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		if(game.gameStarted && saveData){
		    playerSave[profileIndex].clear();
		    //playerSave.flush();
		    itemSave[profileIndex].clear();
		    //itemSave.flush();
            //itemSave = Gdx.app.getPreferences("items");
            //playerSave = Gdx.app.getPreferences("player");

			for(Item i:items){
				i.save(itemSave[profileIndex]);
			}
			Player.inventory.clearInv();
			playerSave[profileIndex].putInteger("playerMapId",Player.map.id);
			playerSave[profileIndex].putInteger("playerLvl",Player.level);
			playerSave[profileIndex].putInteger("playerXp",Player.getXp());
			playerSave[profileIndex].putInteger("playerHp",Player.health);
			playerSave[profileIndex].putInteger("playerMaxHp",Player.maxHealth);
			playerSave[profileIndex].putFloat("playerX",Player.collosionRect.getPosition().x);
			playerSave[profileIndex].putFloat("playerY",Player.collosionRect.getPosition().y);
			playerSave[profileIndex].putBoolean("started",true);
			itemSave[profileIndex].flush();
			playerSave[profileIndex].flush();

			Player.dispose();
			entityDie.dispose();
			entityHit.dispose();
		}

		System.out.println("exit!");
	}
	public static void checkPointSave(){
	    playerRespawn[profileIndex].clear();
	    //playerRespawn.flush();
	    itemRespawn[profileIndex].clear();
	    //itemRespawn.flush();
        //itemRespawn = Gdx.app.getPreferences("itemsCP");
        //playerRespawn = Gdx.app.getPreferences("playerCP");

		for(Item i:items){
			i.save(itemRespawn[profileIndex]);
		}
		playerRespawn[profileIndex].putInteger("playerMaxHp",Player.maxHealth);
		playerRespawn[profileIndex].putInteger("playerMapId",Player.map.id);
		playerRespawn[profileIndex].putInteger("playerLvl",Player.level);
		playerRespawn[profileIndex].putInteger("playerXp",Player.getXp());
		playerRespawn[profileIndex].putFloat("playerX",Player.collosionRect.getPosition().x);
		playerRespawn[profileIndex].putFloat("playerY",Player.collosionRect.getPosition().y);


		itemRespawn[profileIndex].flush();
		playerRespawn[profileIndex].flush();
	}
    public static void respawnPlayer(){
        Player.collosionRect.setTransform(playerRespawn[profileIndex].getFloat("playerX"), playerRespawn[profileIndex].getFloat("playerY")+1,0);
        Player.level = playerRespawn[profileIndex].getInteger("playerLvl");
        Player.maxHealth = playerRespawn[profileIndex].getInteger("playerMaxHp");
        System.out.println(playerRespawn[profileIndex].getInteger("playerMaxHp"));
        Player.forceSetXp(playerRespawn[profileIndex].getInteger("playerXp"));
        Player.health = Player.maxHealth;
        game.currentMap = maps.get(playerRespawn[profileIndex].getInteger("playerMapId"));
        game.currentMap.load();
    }
    public static void respawnItems(){
        for(int i = 0;i < items.size(); i++){
            if(itemRespawn[profileIndex].getInteger("count"+i)>0){
                items.get(i).usesRemain = itemRespawn[profileIndex].getInteger("count"+i);
                items.get(i).setPosition(itemRespawn[profileIndex].getFloat("x"+i),itemRespawn[profileIndex].getFloat("y"+i));
                int a = itemRespawn[profileIndex].getInteger("map"+i);
                if(a >= 0 ){
                    maps.get(a).items.add(items.get(i));
                    items.get(i).map = maps.get(a);
                }else if (a < 0){
                    items.get(i).forceAdd(Player.inventory);
                    if(a == -2){
                        items.get(i).MainSlot();
                    }
                }
            }

        }
    }
	public static Map initMaps(){
		Player.collosionRect.setTransform(playerSave[profileIndex].getFloat("playerX"), playerSave[profileIndex].getFloat("playerY"),0);
		Player.level = playerSave[profileIndex].getInteger("playerLvl");
		Player.maxHealth = playerSave[profileIndex].getInteger("playerMaxHp");
		Player.health = playerSave[profileIndex].getInteger("playerHp");
		Player.forceSetXp(playerSave[profileIndex].getInteger("playerXp"));

		return maps.get(playerSave[profileIndex].getInteger("playerMapId"));
	}

	public static void initItems(){
		for(int i = 0;i < items.size();i++){
			if(itemSave[profileIndex].getInteger("count"+i)>0){
				items.get(i).usesRemain = itemSave[profileIndex].getInteger("count"+i);
				items.get(i).setPosition(itemSave[profileIndex].getFloat("x"+i),itemSave[profileIndex].getFloat("y"+i));
				int a = itemSave[profileIndex].getInteger("map"+i);
				if(a >= 0 ){
					maps.get(a).items.add(items.get(i));
					items.get(i).map = maps.get(a);
				}else if (a < 0){
					items.get(i).forceAdd(Player.inventory);
					if(a == -2){
						items.get(i).MainSlot();
					}
				}
			}

		}
	}

	public static void clearData(){
		playerSave[profileIndex].clear();
		playerSave[profileIndex].flush();
		itemSave[profileIndex].clear();
		itemSave[profileIndex].flush();
		playerRespawn[profileIndex].clear();
		playerRespawn[profileIndex].flush();
		itemRespawn[profileIndex].clear();
		itemRespawn[profileIndex].flush();

		Gdx.app.exit();
	}
}
