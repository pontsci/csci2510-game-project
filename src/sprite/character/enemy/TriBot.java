package sprite.character.enemy;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import managers.BulletManager;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Animation;
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
    private int currentAnimation = 0;
    private int dmgTicks = 0;
    private boolean shoot = false;

    public TriBot(float startX, float startY, Vector2f scale, ArrayList<Sprite> floors, ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, MainCharacter player, ArrayList<Sprite> walls, BulletManager bm, BufferedImage spriteSheet)
    {
        super(startX, startY, scale, floors, screenWalls, platforms, player, walls, bm);
        // get the animations for the tri bot - follow the main character
        setCurrentSpriteFrame(spriteSheet.getSubimage(0, 0, 237, 356));
        animation.addAnimation(spriteSheet.getSubimage(0, 0, 1659, 356), 7);
        animation.addAnimation(spriteSheet.getSubimage(0, 356, 1185, 356), 5);
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
            bm.addEnemyBullet(getxTranslation(), getyTranslation(), getScale().x > 0);
            shoot = false;
        }
    }

    // Process which animation is playing, when an animation finishes, it returns
    // true
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
                    currentAnimation = MOVE_ANIMATION;
                    //now that the charge animation has finished, shoot a bullet
                    shoot = true;
                }
                break;

            case IDLE_ANIMATION:
                // jump Animation
        }
    }

    public void processEffects(float delta)
    {
        // DoT check
        if(conditions.getStatus(5).active){
            // At 3,2,and 1 on the timer, if the player is above 1 health, damage the player
            if((conditions.getTimer(5) > 2.0 && dmgTicks < 1)
                    || (conditions.getTimer(5) > 1.0 && conditions.getTimer(5) < 2.0 && dmgTicks < 2)
                    || (conditions.getTimer(5) > 0.0 && conditions.getTimer(5) < 1.0 && dmgTicks < 3)){
                if(hp > 1){
                    dmgOverTime();
                    dmgTicks++;
                }
            }
        }else{
            dmgTicks = 0;
        }
    }

    // DoT Effect
    public void dmgOverTime()
    {
        // System.out.println("DoT!!!");
        hp--;
    }
}
