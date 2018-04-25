package managers;

import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.bullet.Bullet;
import sprite.bullet.enemy.EnemyBullet;
import sprite.bullet.player.PlayerBullet;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public class BulletManager extends Manager{
    private MainCharacter player;
    private ArrayList<Sprite> enemies;

    public BulletManager(MainCharacter player, ArrayList<Sprite> enemies){
        this.player = player;
        this.enemies = enemies;
    }

    public void addMainCharacterBullet(float x, float y, boolean direction){
        if(direction)
            getSprites().add(new PlayerBullet(x, y, new Vector2f(.8f, .4f), enemies));
        else
            getSprites().add(new PlayerBullet(x, y, new Vector2f(-.8f, -.4f), enemies));
    }

    public void addEnemyBullet(float x, float y, boolean direction){
        if(direction)
            getSprites().add(new EnemyBullet(x, y, new Vector2f(.8f, .4f), player));
        else
            getSprites().add(new EnemyBullet(x, y, new Vector2f(-.8f, -.4f), player));
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
    public void switchLevel(int level){

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
