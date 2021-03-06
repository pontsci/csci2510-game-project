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

    /**
     * initialize the bullet manager, setting variables to passed parameters
     * @param player the player
     * @param enemies all enemies
     * @param walls all walls
     */
    public void initialize(MainCharacter player, ArrayList<Sprite> enemies, ArrayList<Sprite> walls){
        this.player = player;
        this.enemies = enemies;
        this.walls = walls;
    }

    /**
     * Add a MainCharacter bullet with specified parameters
     * @param x starting x
     * @param y starting y
     * @param direction direction to travel
     */
    public void addMainCharacterBullet(float x, float y, boolean direction){
        if(direction)
            getSprites().add(new PlayerBullet(x, y, new Vector2f(.8f, .4f), enemies, walls, player.isDmgUpActive() ? 3 : 1, player.isTaserActive(), playerBulletSpriteSheet));
        else
            getSprites().add(new PlayerBullet(x, y, new Vector2f(-.8f, -.4f), enemies, walls, player.isDmgUpActive() ? 3 : 1, player.isTaserActive(), playerBulletSpriteSheet));
    }

    /**
     * Add an Enemy bullet with specified parameters
     * @param x starting x
     * @param y starting y
     * @param direction direction to travel
     */
    public void addEnemyBullet(float x, float y, boolean direction){
        if(direction)
            getSprites().add(new EnemyBullet(x, y, new Vector2f(.6f, .4f), player, walls, enemyBulletSpriteSheet));
        else
            getSprites().add(new EnemyBullet(x, y, new Vector2f(-.6f, -.4f), player, walls, enemyBulletSpriteSheet));
    }

    /**
     * Loops through all bullets, if bullets collide with anything, they are removed.
     * @param delta time
     * @param viewport the viewport
     */
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

    /**
     * This removes all bullets when called
     * @param level the current level
     * @param spawner spawning system
     * @param viewport the viewport
     */
    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
    }

    /**
     * If the bullets go out of bounds, remove them
     * @param delta time
     */
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
