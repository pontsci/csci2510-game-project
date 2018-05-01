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
    private float xBound;
    private float yBound;
    
    public void initialize(ArrayList<Sprite> floors, ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, MainCharacter player, ArrayList<Sprite> walls, BulletManager bm, float xbounds, float ybounds)
    {
        this.floors = floors;
        this.screenWalls = screenWalls;
        this.walls = walls;
        this.platforms = platforms;
        this.player = player;
        this.bm = bm;
        xBound = xbounds;
        yBound = ybounds;
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
    public void update (float delta, Matrix3x3f viewport){
        float x;
        float y;
        for(int i=0; i< getSprites().size(); i++){
            x = getSprites().get(i).getxTranslation();
            y = getSprites().get(i).getyTranslation();
             if(x > xBound || x< -xBound || y> yBound ||y < -yBound){
                 getSprites().remove(i);
            }
        }
        super.update(delta, viewport);
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
