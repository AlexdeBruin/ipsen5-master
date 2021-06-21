package nl.hsleiden.inf2b.groep4.InitializePuzzleAssets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.inf2b.groep4.account.Role;
import nl.hsleiden.inf2b.groep4.puzzle.block.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by VincentSpijkers on 23/05/2018.
 */
@Singleton
public class InitializeDAO extends AbstractDAO<Object> {

    private ArrayList<ForgroundBlock> forgroundBlocksList = new ArrayList<>();
    private ArrayList<BackgroundBlock> backgroundBlocksList = new ArrayList<>();
    private ArrayList<Hero> heroList = new ArrayList<>();
	private ArrayList<Role> roles = new ArrayList<>();

    @Inject
    public InitializeDAO(SessionFactory sessionFactory) {super(sessionFactory);}

    public void insertData(){
        this.forgroundBlocksList.add(new ForgroundBlock(1, "/assets/Resources/img/tiles/forgroundTiles/steel_beam_floor.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(2, "/assets/Resources/img/tiles/forgroundTiles/stone_floor.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(3, "/assets/Resources/img/tiles/forgroundTiles/lava_block.png", BlockType.INSTAKILLBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(4, "/assets/Resources/img/tiles/forgroundTiles/water.png", BlockType.INSTAKILLBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(5, "/assets/Resources/img/tiles/forgroundTiles/crate.png", BlockType.MOVEABLEBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(6, "/assets/Resources/img/tiles/forgroundTiles/steel_beam_holder_body.png",BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(7, "/assets/Resources/img/tiles/forgroundTiles/steel_beam_holder_top.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(8, "/assets/Resources/img/tiles/forgroundTiles/grass_block.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(9, "/assets/Resources/img/tiles/forgroundTiles/greek_column_bottom.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(10, "/assets/Resources/img/tiles/forgroundTiles/greek_column_middle.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(11, "/assets/Resources/img/tiles/forgroundTiles/greek_column_top.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(12, "/assets/Resources/img/tiles/forgroundTiles/ice_block.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(13, "/assets/Resources/img/tiles/forgroundTiles/ice_column_top.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(14, "/assets/Resources/img/tiles/forgroundTiles/ice_column_middle.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(15, "/assets/Resources/img/tiles/forgroundTiles/ice_column_bottom.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(16, "/assets/Resources/img/tiles/forgroundTiles/ice_snow_block.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(17, "/assets/Resources/img/tiles/forgroundTiles/nature_stone_block.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(18, "/assets/Resources/img/tiles/forgroundTiles/sandblock.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(19, "/assets/Resources/img/tiles/forgroundTiles/sandstone.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(20, "/assets/Resources/img/tiles/forgroundTiles/cloud1.png", BlockType.SOLIDBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(21, "/assets/Resources/img/tiles/forgroundTiles/cloud2.png", BlockType.SOLIDBLOCK, null));

        TileMap energyMap = new TileMap();
        energyMap.add("energie", 0);

        this.forgroundBlocksList.add(new ForgroundBlock(22, "/assets/Resources/img/tiles/items/energy_tank5.png", BlockType.ITEM_ENERGYTANK,energyMap));
        this.forgroundBlocksList.add(new ForgroundBlock(23, "/assets/Resources/img/tiles/forgroundTiles/lamp.png", BlockType.SOLIDBLOCK, null));
        TileMap teleportBlue = new TileMap();
        teleportBlue.add("x", 0);
        teleportBlue.add("y", 0);

        TileMap teleportRed = new TileMap();
        teleportRed.add("x", 0);
        teleportRed.add("y", 0);

        this.forgroundBlocksList.add(new ForgroundBlock(24, "/assets/Resources/img/tiles/specialTiles/portal_blue.png", BlockType.TELEPORT, teleportBlue));
        this.forgroundBlocksList.add(new ForgroundBlock(25, "/assets/Resources/img/tiles/specialTiles/portal_red.png", BlockType.TELEPORT, teleportRed));
        this.forgroundBlocksList.add(new ForgroundBlock(26, "/assets/Resources/img/tiles/forgroundTiles/flying_block.png", BlockType.SOLIDBLOCK, null));

        TileMap jumpingMap = new TileMap();
        jumpingMap.add("omhoog", 0);

        this.forgroundBlocksList.add(new ForgroundBlock(27, "/assets/Resources/img/tiles/specialTiles/jump_pad2.png", BlockType.JUMPINGPAD, jumpingMap));

        TileMap speedMap = new TileMap();
        speedMap.add("vooruit", 0);

        this.forgroundBlocksList.add(new ForgroundBlock(28, "/assets/Resources/img/tiles/specialTiles/speed_boost.png", BlockType.SPEEDBOOST, speedMap));
        this.forgroundBlocksList.add(new ForgroundBlock(29, "/assets/Resources/img/tiles/specialTiles/spikes.png",BlockType.SPIKE, null));
        this.forgroundBlocksList.add(new ForgroundBlock(30, "/assets/Resources/img/tiles/specialTiles/firespikes.png",BlockType.SPIKE, null));
        this.forgroundBlocksList.add(new ForgroundBlock(31, "/assets/Resources/img/tiles/specialTiles/ice_spikes.png",BlockType.SPIKE, null));

        TileMap bombMap = new TileMap();
        bombMap.add("rondes", 0);

        this.forgroundBlocksList.add(new ForgroundBlock(32, "/assets/Resources/img/tiles/items/bomb.png",BlockType.BOMB, bombMap));

        TileMap doorMap = new TileMap();
		doorMap.add("id", 0);

        this.forgroundBlocksList.add(new ForgroundBlock(33, "/assets/Resources/img/tiles/specialTiles/door.png",BlockType.DOOR, doorMap));

        TileMap keyMap = new TileMap();
        keyMap.add("id", 0);

        this.forgroundBlocksList.add(new ForgroundBlock(34, "/assets/Resources/img/tiles/items/key.png",BlockType.ITEM_KEY, keyMap));
        this.forgroundBlocksList.add(new ForgroundBlock(35, "/assets/Resources/img/tiles/items/heart2.png",BlockType.ITEM_EXTRALIFE, null));
        this.forgroundBlocksList.add(new ForgroundBlock(36, "/assets/Resources/img/tiles/specialTiles/hero_block.png",BlockType.HEROBLOCK, null));
		this.forgroundBlocksList.add(new ForgroundBlock(37, "/assets/Resources/img/tiles/forgroundTiles/joker2.png",BlockType.VILLIANBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(38, "/assets/Resources/img/villians/loki.png",BlockType.VILLIANBLOCK, null));
        this.forgroundBlocksList.add(new ForgroundBlock(39, "/assets/Resources/img/villians/thanos.png",BlockType.VILLIANBLOCK, null));

        this.backgroundBlocksList.add(new BackgroundBlock(1, "/assets/Resources/img/tiles/backgroundTiles/brick_wall_gray.png", 0));
        this.backgroundBlocksList.add(new BackgroundBlock(2, "/assets/Resources/img/tiles/backgroundTiles/brick_wall_red.png", 5));
        this.backgroundBlocksList.add(new BackgroundBlock(3, "/assets/Resources/img/tiles/backgroundTiles/brick_wall_yellow.png", 3));
        this.backgroundBlocksList.add(new BackgroundBlock(4, "/assets/Resources/img/tiles/backgroundTiles/brick_wall_blue.png", 2));
        this.backgroundBlocksList.add(new BackgroundBlock(5, "/assets/Resources/img/tiles/backgroundTiles/brick_wall_purple.png", 6));
        this.backgroundBlocksList.add(new BackgroundBlock(6, "/assets/Resources/img/tiles/backgroundTiles/brick_wall_green.png", 4));
        this.backgroundBlocksList.add(new BackgroundBlock(7 ,"/assets/Resources/img/tiles/backgroundTiles/light_blue_background.png", 7));

      //  this.heroList.add(new Hero(1, "Batman", "batman", "/assets/Resources/img/heroes/batman_dark_knight.png", "-", "-"));
        this.heroList.add(new Hero(2, "Black Panther", "blackpanther", "/assets/Resources/img/heroes/black_panther.png", "stapVooruit, stapAchteruit, spring, draai", " “Sneakers”: Black panther zijn schoenen zijn van speciaal materiaal, hierdoor kan hij over spikes heenlopen"));
        this.heroList.add(new Hero(3, "Black Widow", "blackwidow", "/assets/Resources/img/heroes/black_widow.png", "stapVooruit, stapAchteruit, spring, draai", "“Bommen onschadelijk maken”: Black widow maakt de bom waar ze naar kijkt (afhankelijk van de kijkrichting) onschadelijk."));
        this.heroList.add(new Hero(4, "Captain America","captainamerica",  "/assets/Resources/img/heroes/captain_america_shield_back.png", "stapVooruit, stapAchteruit, spring, draai", "Captain america zijn stapVooruit en stapAchteruit zijn enhanced en verplaatsen hem 2 tiles in plaats van 1"));
        this.heroList.add(new Hero(5, "Deadpool", "deadpool" ,"/assets/Resources/img/heroes/deadpool.png", "stapVooruit, stapAchteruit, spring, draai", "Deadpool begint met 2 levens in plaats van 1"));
        this.heroList.add(new Hero(6, "Doctor Strange","doctorstrange", "/assets/Resources/img/heroes/doctor.png", "stapVooruit, stapAchteruit, vliegOmhoog vliegOmlaag, spring, draai", "“Eye of Agamotto”: Docter strange gaat terug de tijd in en gaat naar de locatie waar hij 5 stappen geleden was. Als hij nog minder dan 5 stappen heeft gelopen gaat hij terug naar het begin"));
        this.heroList.add(new Hero(7, "Hulk", "hulk", "/assets/Resources/img/heroes/hulk.png", "stapVooruit, stapAchteruit, spring, draai", "“HULK SMASH”: Hulk sloopt de solidblock waar hij naar kijkt (afhankelijk van de kijkrichting)"));
        this.heroList.add(new Hero(8, "Iron Man", "ironman", "/assets/Resources/img/heroes/iron_man.png", "stapVooruit, stapAchteruit, vliegVooruit, vliegOmlaag, vliegOmhoog (Let op: GEEN draai)", "“Energy blast”:  Ironman schiet een energie blast naar rechts, maar alleen als de tile naast hem geen solidblock is (Het zou onverstandig zijn om dat te doen!). De solidblock van de 2 tiles ernaast worden vervolgens gesloopt (Als die er zijn)"));
        this.heroList.add(new Hero(9,"Scarlet Witch", "scarletwitch", "/assets/Resources/img/heroes/scarlet_witch.png", "stapVooruit, stapAchteruit, spring, draai", "“Hexes”: Scarlett maakt een solidblock in de richting waar ze naar kijkt"));
     //   this.heroList.add(new Hero(10,"Spiderman", "spiderman", "/assets/Resources/img/heroes/spiderman.png", "", ""));
        this.heroList.add(new Hero(11, "Superman", "superman", "/assets/Resources/img/heroes/superman_flying.png", "draai, vliegOmhoog, vliegVooruit, vliegOmlaag" ,"“Super hero landing”: Superman stopt met vliegen valt naar beneden tot hij op een ondergrond staat, dit heeft geen “val kosten”"));
        this.heroList.add(new Hero(12, "Thor", "thor", "/assets/Resources/img/heroes/thor.png", "stapVooruit, stapAchteruit, vliegOmhoog vliegOmlaag, vliegVooruit, spring, draai", "“Thunder strike”:  Thor maakt bliksem boven zijn hoofd, hij sloopt daardoor alle solidblocks boven hem"));
        this.heroList.add(new Hero(13, "Wonder Woman", "wonderwoman", "/assets/Resources/img/heroes/wonder_woman.png", "stapVooruit, stapAchteruit, spring, draai", "“Zweep”: Wonder woman pakt haar zweep en gooit hem haar kijkrichting op (4 tiles), deze zweep pakt alle items op (key, extra life en energie tank)"));
        this.heroList.add(new Hero(14, "The Flash", "theflash", "/assets/Resources/img/heroes/the_flash.png", "stapVooruit, stapAchteruit, spring, draai", "“FLASH”: the Flash verplaatst 3 tiles naar de richting waar hij op kijkt. Als zijn bestemming een solidblock is teleporteert hij NIET"));

		roles.add(new Role(1, "GROUP"));
		roles.add(new Role(3, "ADMIN"));

        Session sessionForground = currentSession().getSession();
        for(ForgroundBlock f : forgroundBlocksList){
            sessionForground.save(f);
        }

        for(BackgroundBlock b : backgroundBlocksList){
            sessionForground.save(b);
        }

        for(Hero h : heroList){
            sessionForground.save(h);
        }

        for(Role r : roles){
        	sessionForground.save(r);
		}
        sessionForground.getTransaction().commit();
    }


    public ArrayList<ForgroundBlock> getList() {
        return forgroundBlocksList;
    }
}
