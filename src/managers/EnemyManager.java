package managers;

import spawning.Spawner;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.character.enemy.Enemy;
import sprite.character.enemy.TriBot;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class EnemyManager extends Manager
{
    private BufferedImage tribotSpriteSheet = loadFile("src/resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png");
    private ArrayList<Sprite> floors;
    private ArrayList<Sprite> screenWalls;
    private ArrayList<Sprite> platforms;
    private ArrayList<Sprite> walls;
    private MainCharacter player;
    private BulletManager bm;

    public void initialize(ArrayList<Sprite> floors, ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, MainCharacter player, ArrayList<Sprite> walls, BulletManager bm)
    {
        this.floors = floors;
        this.screenWalls = screenWalls;
        this.walls = walls;
        this.platforms = platforms;
        this.player = player;
        this.bm = bm;
    }

    private  void addTriBot(Vector2f pos)
    {
        getSprites().add(new TriBot(pos.x, pos.y, new Vector2f(.3f, .3f), floors, screenWalls, platforms, player, walls, bm, tribotSpriteSheet));
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
