package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import spawning.Spawner;
import sprite.Sprite;
import sprite.character.enemy.Enemy;
import sprite.character.enemy.TriBot;
import sprite.character.player.MainCharacter;
import sprite.world.StatusIcon;
import util.Matrix3x3f;
import util.Vector2f;

public class EnemyManager extends Manager
{
    private BufferedImage tribotSpriteSheet = loadFile("src/resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png");
    private BufferedImage hitEffectIcon = loadFile("src/resources/character/enemy/tribot/onHitEffect_WH_237x356_OnHitEffect.png");
    private BufferedImage effects = loadFile("src/resources/world/pickups/LightningShield.png");
    private StatusIcon taserEffect;
    private StatusIcon hitEffect;
    private ArrayList<Sprite> screenWalls;
    private ArrayList<Sprite> platforms;
    private ArrayList<Sprite> walls;
    private MainCharacter player;
    private BulletManager bm;
    private Random random = new Random();

    /**
     * Initialize variables to passed parameters
     * @param screenWalls the screen walls
     * @param platforms the platforms
     * @param player the player
     * @param walls the walls
     * @param bm the bullet manager
     */
    public void initialize(ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, MainCharacter player, ArrayList<Sprite> walls, BulletManager bm)
    {
    	taserEffect = new StatusIcon(new Vector2f(.21f, .14f), effects.getSubimage(0, 0, 237, 356));
    	hitEffect = new StatusIcon(new Vector2f(.2f,.2f), hitEffectIcon.getSubimage(0,0,237,356));
        this.screenWalls = screenWalls;
        this.walls = walls;
        this.platforms = platforms;
        this.player = player;
        this.bm = bm;
    }

    /**
     * adds a new tribot given a start position
     * @param pos starting position
     */
    private void addTriBot(Vector2f pos)
    {
        getSprites().add(new TriBot(pos.x, pos.y, new Vector2f(.3f, .3f),  screenWalls, platforms, player, walls, bm, tribotSpriteSheet, taserEffect, random.nextBoolean(), hitEffect));
    }

    /**
     * Have all enemies running their check collision methods
     * @param delta time
     * @param viewport the viewport
     */
    public void checkCollision(float delta, Matrix3x3f viewport){
        for(Sprite sprite : getSprites()){
            ((Enemy)sprite).checkCollision(delta, viewport);
        }
    }

    /**
     * Depending on the level, change enemy spawn areas, with more added the higher the level
     * @param level the current level
     * @param spawner the spawn system
     * @param viewport the viewport
     */
    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 1:
                spawnTriBots(spawner, 3);
                break;
            case 2:
                spawnTriBots(spawner, 4);
                break;
            case 3:
                spawnTriBots(spawner, 5);
                break;
            case 4:
                spawnTriBots(spawner, 6);
                break;
            case 5:
                spawnTriBots(spawner, 6);
                break;
            case 6:
                spawnTriBots(spawner, 6);
                break;
            case 7:
                spawnTriBots(spawner, 7);
                break;

        }
    }
    public void spawnTriBots(Spawner spawner, int num){
        for (int i = 0; i < num; i++){
            addTriBot(spawner.getSpawnPoint());
        }
    }
}
