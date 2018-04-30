package managers;

import spawning.Spawner;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.bullet.Bullet;
import sprite.bullet.enemy.EnemyBullet;
import sprite.bullet.player.PlayerBullet;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BulletManager extends Manager{
    private BufferedImage enemyBulletSpriteSheet = loadFile("src/resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png").getSubimage(1422, 356, 237, 356);
    private BufferedImage playerBulletSpriteSheet = loadFile("src/resources/character/player/MainCharBullet_WH_237x356_Bullet.png");
    private MainCharacter player;
    private ArrayList<Sprite> enemies;
    private ArrayList<Sprite> walls;

    public void initialize(MainCharacter player, ArrayList<Sprite> enemies, ArrayList<Sprite> walls){
        this.player = player;
        this.enemies = enemies;
        this.walls = walls;
    }

    public void addMainCharacterBullet(float x, float y, boolean direction){
        if(direction)
            getSprites().add(new PlayerBullet(x, y, new Vector2f(.8f, .4f), enemies, walls, playerBulletSpriteSheet));
        else
            getSprites().add(new PlayerBullet(x, y, new Vector2f(-.8f, -.4f), enemies, walls, playerBulletSpriteSheet));
    }

    public void addEnemyBullet(float x, float y, boolean direction){
        if(direction)
            getSprites().add(new EnemyBullet(x, y, new Vector2f(.6f, .4f), player, walls, enemyBulletSpriteSheet));
        else
            getSprites().add(new EnemyBullet(x, y, new Vector2f(-.6f, -.4f), player, walls, enemyBulletSpriteSheet));
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        for(int i = 0; i < getSprites().size(); i++){
            Bullet sprite = ((Bullet)(getSprites().get(i)));
            if(sprite.checkCollision(delta, viewport)){
                getSprites().remove(i);
                i--;
            }
        }
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
    }

    @Override
    public void process(float delta){
        super.process(delta);
        if(!getSprites().isEmpty()){
            for(int i = 0; i <getSprites().size(); i++){
                if(((Bullet)getSprites().get(i)).outOfBounds()){
                    getSprites().remove(i);
                    i--;
                }
            }
        }
    }
}
