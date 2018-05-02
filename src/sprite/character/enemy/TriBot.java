package sprite.character.enemy;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import managers.BulletManager;
import sound.SteamSound;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.world.StatusIcon;
import util.Animation;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TriBot extends Enemy
{
    private Animation animation = new Animation();
    private final int MOVE_ANIMATION = 0;
    private final int ATTACK_ANIMATION = 1;
    private final int IDLE_ANIMATION = 2;
    private BufferedImage idle;
    private int currentAnimation = 0;
    private int dmgTicks = 0;
    private boolean shoot = false;

    public TriBot(float startX, float startY, Vector2f scale, ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, MainCharacter player, ArrayList<Sprite> walls, BulletManager bm, BufferedImage spriteSheet, StatusIcon lightning)
    {
        super(startX, startY, scale, screenWalls, platforms, player, walls, bm, lightning);
        // get the animations for the tri bot - follow the main character
        setCurrentSpriteFrame(spriteSheet.getSubimage(0, 0, 237, 356));
        animation.addAnimation(spriteSheet.getSubimage(0, 0, 1659, 356), 7);
        animation.addAnimation(spriteSheet.getSubimage(0, 356, 1185, 356), 5);
        idle = spriteSheet.getSubimage(0,0,237,356);
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes()
    {
        hitboxes.add(new BoundingBox(new Vector2f(-1.2f, -3), new Vector2f(1.1f, 1.9f), Color.BLUE));
        hitboxes.add(new BoundingCircle(.4f, -.1f, 1.4f, Color.RED));
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -1.7f), new Vector2f(.8f, 1.1f), Color.RED));
    }

    @Override
    public void process(float delta)
    {
        super.process(delta);


        //we need to play our shooting animation, so set it
        if(playShootAnimation){
            currentAnimation = ATTACK_ANIMATION;
            //now that it is playing, we no longer need to set the animation to it.
            playShootAnimation = false;
        }
        processAnimations(delta);

		//shoot is controlled by animation, it is set to true only when the animation is done playing
		if(shoot){
            SteamSound.enemyWeaponFire();
			bm.addEnemyBullet(getxTranslation(), getyTranslation(), getScale().x > 0);
			shoot = false;
		}

	}

    @Override
    protected void checkFloorCollision(float delta, Matrix3x3f viewport)
    {
        //not needed
    }

    /**
     * Process which animation is playing, when an animation finishes, it returns
     * true
     * @param delta time
     */
    private void processAnimations(float delta)
    {
        switch(currentAnimation){
            // move Animation
            case MOVE_ANIMATION:
                animation.playAnimation(delta, MOVE_ANIMATION, this);
                break;
            // idle Animation
            case ATTACK_ANIMATION:
                if(animation.playAnimation(delta, ATTACK_ANIMATION, this)){
                    currentAnimation = IDLE_ANIMATION;
                    //now that the charge animation has finished, shoot a bullet
                    shoot = true;
                }
                break;

            case IDLE_ANIMATION:
                setCurrentSpriteFrame(idle);
                currentAnimation = moving ? MOVE_ANIMATION : IDLE_ANIMATION;
                break;
                // jump Animation
        }
    }


}
