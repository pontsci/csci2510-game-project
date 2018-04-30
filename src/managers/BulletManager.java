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
    private MainCharacter player;
    private ArrayList<Sprite> enemies;
    BufferedImage enemyBulletSpriteSheet;
    BufferedImage playerBulletSpriteSheet;

    public void initialize(MainCharacter player, ArrayList<Sprite> enemies){
        enemyBulletSpriteSheet = loadFile("src/resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png").getSubimage(1422, 356, 237, 356);
        playerBulletSpriteSheet = loadFile("src/resources/character/player/MainCharBullet_WH_237x356_Bullet.png");
        this.player = player;
        this.enemies = enemies;
    }

    public void addMainCharacterBullet(float x, float y, boolean direction){
        if(direction)
            getSprites().add(new PlayerBullet(x, y, new Vector2f(.8f, .4f), enemies, playerBulletSpriteSheet));
        else
            getSprites().add(new PlayerBullet(x, y, new Vector2f(-.8f, -.4f), enemies, playerBulletSpriteSheet));
    }

    public void addEnemyBullet(float x, float y, boolean direction){
        if(direction)
            getSprites().add(new EnemyBullet(x, y, new Vector2f(.8f, .4f), player, enemyBulletSpriteSheet));
        else
            getSprites().add(new EnemyBullet(x, y, new Vector2f(-.8f, -.4f), player, enemyBulletSpriteSheet));
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
