package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    private BufferedImage effects = loadFile("src/resources/world/pickups/LightningShield.png");
    private StatusIcon taserEffect;
    private ArrayList<Sprite> floors;
    private ArrayList<Sprite> screenWalls;
    private ArrayList<Sprite> platforms;
    private ArrayList<Sprite> walls;
    private MainCharacter player;
    private BulletManager bm;

    public void initialize(ArrayList<Sprite> floors, ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, MainCharacter player, ArrayList<Sprite> walls, BulletManager bm)
    {
    	taserEffect = new StatusIcon(new Vector2f(.21f, .14f), effects.getSubimage(0, 0, 237, 356));
        this.floors = floors;
        this.screenWalls = screenWalls;
        this.walls = walls;
        this.platforms = platforms;
        this.player = player;
        this.bm = bm;
    }

    private void addTriBot(Vector2f pos)
    {
        getSprites().add(new TriBot(pos.x, pos.y, new Vector2f(.3f, .3f), floors, screenWalls, platforms, player, walls, bm, tribotSpriteSheet, taserEffect));
    }

    public void checkCollision(float delta, Matrix3x3f viewport){
        for(Sprite sprite : getSprites()){
            ((Enemy)sprite).checkCollision(delta, viewport);
        }
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 1:
                for (int i = 0; i < 3; i++){
                    addTriBot(spawner.getSpawnPoint());
                }
                break;
            case 2:
                for (int i = 0; i < 4; i++){
                    addTriBot(spawner.getSpawnPoint());
                }
                break;
            case 3:
                for (int i = 0; i < 5; i++){
                    addTriBot(spawner.getSpawnPoint());
                }
                break;
            case 4:
                for (int i = 0; i < 6; i++){
                    addTriBot(spawner.getSpawnPoint());
                }
                break;
        }
    }
}
