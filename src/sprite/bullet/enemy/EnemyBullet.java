package sprite.bullet.enemy;

import bounding.BoundingBox;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.bullet.Bullet;
import util.Collision;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyBullet extends Bullet{
    private MainCharacter player;

    /**
     * The constructor for an enemy bullet. The default bullet speed is 5, also sets frame of bullet and init. hitboxes
     * @param startX the starting x position
     * @param startY the starting y position
     * @param scale the scale
     * @param player the player
     * @param walls the walls
     * @param spriteSheet the spriteSheet
     */
    public EnemyBullet(float startX, float startY, Vector2f scale, MainCharacter player, ArrayList<Sprite> walls, BufferedImage spriteSheet){
        super(startX, startY, scale, walls);
        setCurrentSpriteFrame(spriteSheet.getSubimage(0,0,237,356));
        this.player = player;
        bulletSpeed = 5;
        initializeHitboxes();
    }


    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-.1f, -.4f), new Vector2f(.2f, -.1f), Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.1f, -.4f), new Vector2f(.2f, -.1f), Color.RED));
    }

    /**
     * Checks collision against player, if player is shielded then player health is not decreased
     * @param delta time
     * @param viewport the viewport
     * @return whether it has collided or not
     */
    @Override
    public boolean checkCollision(float delta, Matrix3x3f viewport){
        //if wall hit, delete bullet
        if(super.checkCollision(delta, viewport))
            return true;
        //if player hit, damage player and delete bullet
        if(Collision.checkSpriteCollision(this, player)){
        	if(!player.isShieldActive())
            	player.decreaseHP(bulletDamage);
            return true;
        }
        return false;
    }
}
