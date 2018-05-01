package sprite.bullet.player;

import bounding.BoundingBox;
import sprite.Sprite;
import sprite.bullet.Bullet;
import sprite.character.enemy.Enemy;
import util.Collision;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlayerBullet extends Bullet{
    private ArrayList<Sprite> enemies;
    boolean taserActive;


    public PlayerBullet(float startX, float startY, Vector2f scale, ArrayList<Sprite> enemies, ArrayList<Sprite> walls, int dmg, boolean taser, BufferedImage spriteSheet){
        super(startX, startY, scale, dmg, walls);
        setCurrentSpriteFrame(spriteSheet.getSubimage(671,0,197,306));
        this.enemies = enemies;
        taserActive = taser;
        bulletSpeed = 7;
        this.bulletDamage = dmg;
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-.3f, .2f), new Vector2f(-.1f, .4f), Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.3f, .2f), new Vector2f(-.1f, .4f), Color.RED));
    }

    @Override
    public void process(float delta){
        super.process(delta);
    }

    /**
     * Checks all enemies against this bullet
     * @param delta time
     * @param viewport screen
     * @return true if collision, false if no collision
     */
    @Override
    public boolean checkCollision(float delta, Matrix3x3f viewport){
        //if wall hit, delete bullet
        if(super.checkCollision(delta, viewport))
            return true;
        //if enemy hit, decrease enemy hp or delete it if its hp hits 0
        for(int i = 0; i < enemies.size(); i++){
            //bullet collides with enemy
            if(Collision.checkSpriteCollision(this, enemies.get(i))){
                //if the enemy hp = 0 then remove the enemy
                ((Enemy) enemies.get(i)).setHit(true);
               
                if(taserActive) {
                	((Enemy) enemies.get(i)).activateDoT();
                }
                
                if(((Enemy)enemies.get(i)).decreaseHP(bulletDamage)){	
            		enemies.remove(i);
            	}
                
                return true;
            }
        }
        return false;
    }
}
