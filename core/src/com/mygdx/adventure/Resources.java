package com.mygdx.adventure;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.adventure.Items.Helmet;
import com.mygdx.adventure.Items.Sword;
import com.mygdx.adventure.Items.food;
import com.mygdx.adventure.actors.Map;

import java.util.HashMap;

public class Resources {
    //--------------------------------------------ITEMS-----------------------------------------------

    //public static Helmet doubleProtection;
    public static Helmet leatherHelmet;
    public static Helmet semiIronHelmet;
    public static Helmet ironHelmet;
    public static Helmet semiDiamondHelmet;
    public static Helmet semiCrystalHelmet;
    public static food waterBottle;
    public static food bandages;
    public static food instantBangades;
    public static Sword woodenSword;
    public static Sword stoneSword;
    public static Sword stoneSwordSharp;
    public static Sword stoneAxe;
    public static Sword ironSword;
    public static Sword ironAxe;
    public static Sword ironWideSword;
    public static Sword semiDiamondAxe;



    public static void initItems() {
        woodenSword = new Sword(game.main.itemMap[2][2],1,5,300,"деревянный меч",100,"большая отдача",village); //0
        stoneSword = new Sword(game.main.itemMap[2][0],1,15,300,"каменный меч",100,"",village); //1
        stoneSwordSharp = new Sword(game.main.itemMap[2][1],1,20,300,"каменный меч с шипами",100,"",third);//2
        waterBottle = new food(new Texture("items/waterBottle.png"),1,3,10,"пузырек воды",10,"",village);//3
        leatherHelmet = new Helmet(game.main.itemMap[3][2],1,"кожанный шлем",5,65,"",firstChallange);//4
        semiIronHelmet = new Helmet(game.main.itemMap[3][3],3,"кожанно-железный шлем",10,100,"кожанный шлем с элементами железа",third);//5
        stoneAxe = new Sword(game.main.itemMap[1][7],2,35,500,"каменный топор",150,"мощный, но медленный",fourth); stoneAxe.knockBack = 800;//6
        ironSword = new Sword(game.main.itemMap[1][2],2,23,280,"каменный топор",150,"",fourth);//7
        bandages = new food(game.main.itemMap[5][1],2,2,26,"бинты",5,"",fourth);//8
        ironHelmet = new Helmet(game.main.itemMap[5][5],2,"железный шлем",15,75,"",fourth);//9
        ironAxe = new Sword(game.main.itemMap[0][7],2,45,500,"железный топор",150,"мощный, но медленный \n -сильно откидывает",snow); ironAxe.knockBack = 1300;//10
        ironWideSword = new Sword(game.main.itemMap[1][2],2,45,350,"железный широки меч",200,"",snow);stoneAxe.knockBack = 800;//11
        semiDiamondHelmet = new Helmet(game.main.itemMap[3][1],3,"железно-алмазный шлем",25,150,"железный шлем с жлементами алмазнго\n-наносит 5 урона тому кто атакует",snow){
            @Override
            public void onAttack(entity attackingEntity) {
                super.onAttack(attackingEntity);
                attackingEntity.giveDamage(5);
            }
        };//12
        instantBangades = new food(game.main.itemMap[5][2],2,1,60,"аптечка",10,"моментально восстанавливает 60хп",snow);//13
        semiCrystalHelmet = new Helmet(game.main.itemMap[3][0],2,"железно-кристальный шлем",20,100,"увеличивает прыжок игрока",cave){
            @Override
            public void onMoveToMainSlot() {
                super.onMoveToMainSlot();
                game.Player.jumpModifier += 25;
            }

            @Override
            public void onMoveFromMainSlot() {
                super.onMoveFromMainSlot();
                game.Player.jumpModifier -= 25;
            }
        };//14
        semiDiamondAxe = new Sword(game.main.itemMap[1][1],3,55,500,"железный-алмазный топор",150,"мощный, но медленный \n -сильно откидывает",cave); semiDiamondAxe.knockBack = 1300;//15




    }
    public static void setItemsPos(){
        waterBottle.setPosition(43,22);
        woodenSword.setPosition(143,24);
        leatherHelmet.setPosition(373,14);
        semiIronHelmet.setPosition(345.11655f, 70.820366f);
        stoneSword.setPosition(275,86);
        stoneSwordSharp.setPosition(339.49738f, 70.87165f);
        for(Map i : myGame.maps){

            i.setItemsPos();
        }
    }
    //--------------------------------------------ENTITIES-----------------------------------------------

    public static entity[] spiders = new entity[4];
    public static entity[] bandits1 = new entity[10];
    public static entity[] bandits2 = new entity[11];
    public static entity[] caveSpiders = new entity[11];
    public static entity[] snowBandits = new entity[20];

    public static void initEntities(){
        Texture spiderTexture = new Texture("entities/spider.png");
        spiders[0] = new entity(spiderTexture,2,10,25,20,181,26,1.5F,1.5F,firstChallange);//0
        spiders[1] = new entity(spiderTexture,2,10,25,20,191,26,1.5F,1.5F,firstChallange);//1
        spiders[2] = new entity(spiderTexture,2,10,25,20,201,26,1.5F,1.5F,firstChallange);//2
        spiders[3] = new entity(spiderTexture,2,10,25,20,211,26,1.5F,1.5F,firstChallange);//3

        Texture banditTexture = new Texture("entities/bandit.png");
        bandits1[0] = new entity(banditTexture,4,30,4,50,35,126.513306f, 32.87166f,2,4,third);//4
        bandits1[1] = new entity(banditTexture,4,30,4,50,35,149.51242f, 32.871664f,2,4,third);//5
        bandits1[2] = new entity(banditTexture,4,30,4,50,35,166.94333f, 32.871664f,2,4,third);//6
        bandits1[3] = new entity(banditTexture,4,30,4,50,35,247.84644f, 16.871663f,2,4,third);//7
        bandits1[4] = new entity(banditTexture,4,30,4,50,35,268.4246f, 16.871665f,2,4,third);//8
        bandits1[5] = new entity(banditTexture,4,30,4,50,35,288.76065f, 16.871665f,2,4,third);//9
        bandits1[6] = new entity(banditTexture,4,30,4,50,35,292.49326f, 72.87165f,2,4,third);//10
        bandits1[7] = new entity(banditTexture,4,30,4,50,35,313.7977f, 72.87165f,2,4,third);//11
        bandits1[8] = new entity(banditTexture,4,30,4,50,35,390.08066f, 36.87166f,2,4,third);//12
        bandits1[9] = new entity(banditTexture,4,30,4,50,35,286.123f, 38.87166f,2,4,third);//13

        bandits2[0] = new entity(banditTexture,4,30,4,50,35,126.513306f, 32.87166f,2,4, fourth);//14
        bandits2[1] = new entity(banditTexture,4,30,4,50,35,149.51242f, 32.871664f,2,4, fourth);//15
        bandits2[2] = new entity(banditTexture,4,30,4,50,35,166.94333f, 32.871664f,2,4, fourth);//16
        bandits2[3] = new entity(banditTexture,4,30,4,50,35,247.84644f, 16.871663f,2,4, fourth);//17
        bandits2[4] = new entity(banditTexture,4,30,4,50,35,268.4246f, 16.871665f,2,4,  fourth);//18
        bandits2[5] = new entity(banditTexture,4,30,4,50,35,288.76065f, 16.871665f,2,4, fourth);//19
        bandits2[6] = new entity(banditTexture,4,30,4,50,35,292.49326f, 72.87165f,2,4,  fourth);//20
        bandits2[7] = new entity(banditTexture,4,30,4,50,35,313.7977f, 72.87165f,2,4,   fourth);//21
        bandits2[8] = new entity(banditTexture,4,30,4,50,35,390.08066f, 36.87166f,2,4,  fourth);//22
        bandits2[9] = new entity(banditTexture,4,30,4,50,35,286.123f, 38.87166f,2,4,    fourth);//23
        bandits2[10] = new entity(banditTexture,4,30,4,50,35,286.123f, 38.87166f,2,4,   fourth);//24

        Texture caveSpider = new Texture("entities/caveSpider.png");
        caveSpiders[0] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//25
        caveSpiders[1] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//26
        caveSpiders[2] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//27
        caveSpiders[3] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//28
        caveSpiders[4] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//29
        caveSpiders[5] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//30
        caveSpiders[6] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//31
        caveSpiders[7] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//32
        caveSpiders[8] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//33
        caveSpiders[9] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//34
       caveSpiders[10] = new entity(caveSpider,2,50,75,50,181,26,1.5F,1.5F,cave);//35

        Texture snowBanditTexture = new Texture("entities/snowBandit.png");
         snowBandits[0] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//36
         snowBandits[1] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//37
         snowBandits[2] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//38
         snowBandits[3] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//39
         snowBandits[4] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//40
         snowBandits[5] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//41
         snowBandits[6] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//42
         snowBandits[7] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//43
         snowBandits[8] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//44
         snowBandits[9] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//45
        snowBandits[10] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//46
        snowBandits[11] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//47
        snowBandits[12] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//48
        snowBandits[13] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//49
        snowBandits[14] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//50
        snowBandits[15] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//51
        snowBandits[16] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//52
        snowBandits[17] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//53
        snowBandits[18] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//54
        snowBandits[19] = new entity(snowBanditTexture,4,80,4,150,100,126.513306f, 32.87166f,2,4,snow);//55




        for(entity i : bandits1){
            i.jumpHeight = 3500;
        }
        for(Map i : myGame.maps){
            System.out.println(i.id);
            i.setMobPos();
        }


    }
    //--------------------------------------------MAPS-----------------------------------------------
    public static Map village;
    public static Map firstChallange;
    public static Map third;
    public static Map fourth;
    public static Map cave;
    public static Map snow;

    public static void initMaps(){
        village = new Map("map/village.tmx",game.gameCamera,new int[]{3,4},new int[]{0,1,2}){
            @Override
            public void createTransitions() {
                transitions[1] = new Transition(1,firstChallange,0);
                transitions[2] = new Transition(2,firstChallange,1);
            }
        };
        firstChallange = new Map("map/firstChall.tmx",game.gameCamera,new int[]{3,4},new int[]{0,1,2}){
            @Override
            public void createTransitions() {
                transitions[0] = new Transition(0,village,1);
                transitions[1] = new Transition(1,village,2);
                transitions[2] = new Transition(2,third,0);
            }
        };
        third = new Map("map/third.tmx",game.gameCamera,village.fLayers,village.bLayers){
            @Override
            public void createTransitions() {
                transitions[0] = new Transition(0,firstChallange,2);
                transitions[1] = new Transition(1,fourth,0);
            }
        };
        fourth = new Map("map/fourth.tmx",game.gameCamera,village.fLayers,village.bLayers){
            @Override
            public void createTransitions() {
                transitions[0] = new Transition(0,third,1);
                transitions[1] = new Transition(1,cave,0);
            }
        };
        cave = new Map("map/cave.tmx",game.gameCamera,village.fLayers,village.bLayers,game.main.caveMusic){
            @Override
            public void createTransitions() {
                transitions[0] = new Transition(0,fourth,1);
                transitions[1] = new Transition(1,snow,0);
            }
        };
        snow = new Map("map/snow.tmx",game.gameCamera,village.fLayers,village.bLayers,game.main.snowMusic){
            @Override
            public void createTransitions() {
                transitions[0] = new Transition(0,cave,1);
                //transitions[1] = new Transition();
            }
        };
        //------------------------------------------------------------------------

    }
    public static void firstInit(){
        for(Item i : myGame.items){
            i.addToMap();
        }
    }
    //-----------------------------------------languages----------------------------------------------
    public static HashMap<String, String> texts = new HashMap<String, String>();
    public static String lang = "en";
    public static void init(){

    }
    public static String get(String key){
        return texts.get(key+lang);
    }



}
