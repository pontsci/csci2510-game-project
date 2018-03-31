package sprite;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Rat extends Sprite{
    private float aTime = 0;//Animation Time
    private float hTime = 0;//Horizontal Time
    private float vTime = 0;//Vertical Time
    private ArrayList<BufferedImage> walkAnimation = new ArrayList<>();
    private float frameTime = .1f; //time between each frame
    private float walkRate = 2f;//Walk rate per second.
    private boolean direction;//True means the rat faces right, false means left.
    private float jumpForce = 0;
    Random rand = new Random();

    public Rat(float startX, float startY, Vector2f scale, ArrayList<BufferedImage> spriteAnimations, boolean direction){
        super(startX, startY, scale, spriteAnimations.get(0).getSubimage(0,0,17,8));
        //true faces right, false faces left
        this.direction = direction;

        walkAnimation.add(spriteAnimations.get(0).getSubimage(0,0, 17, 8));
        walkAnimation.add(spriteAnimations.get(0).getSubimage(17,0, 17, 8));
        walkAnimation.add(spriteAnimations.get(0).getSubimage(34,0,17,8));
        walkAnimation.add(spriteAnimations.get(0).getSubimage(51,0, 17, 8));

        initializeHitboxes();
    }

    //Initialize the rat's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-.07f, -.05f), new Vector2f( .10f, .02f),  0, getyTranslation(), Color.BLUE));
        hitboxes.add(new BoundingCircle(.025f,.04f,-.02f,Color.RED));
        hitboxes.add(new BoundingBox(new Vector2f(-.03f, -.03f), new Vector2f( .09f, -.01f),  0, getyTranslation(), Color.RED));
    }

    @Override
    //Process what's affecting/changing the rat - location/animation
    public void process(float delta){
        horizontalMovement(delta);
        verticalMovement(delta);
        playAnimation(delta, walkAnimation);
    }

    //SIMPLE Random horizontal movement of the rat.
    private void horizontalMovement(float delta){
        hTime = hTime + delta;
        //Every second a random boolean is chosen to
        //determine the direction the mouse will go
        if(hTime > 1){
            direction = rand.nextBoolean();
            //True faces right
            if(direction)
                setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
            //False faces left
            else
                setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
            hTime = hTime - 1;
        }

        //move the mouse in a direction
        if(direction)
            setxTranslation(getxTranslation() + (walkRate * delta));
        else
            setxTranslation(getxTranslation() - (walkRate * delta));
    }

    //SIMPLE Random jump movement of the rat
    private void verticalMovement(float delta){
        vTime = vTime + delta;
        //Every 1.5 seconds a random boolean is chosen to
        //determine if the mouse will jump
        if(vTime > 1.5f){
            //if not jumping and the boolean is true, initiate a jump
            if(jumpForce == 0 && rand.nextBoolean()){
                setRotation(-.5f);
                jumpForce = 7f;
            }
            vTime = vTime - 1.5f;
        }
        //if jumping, adjust jump force by gravity
        if(jumpForce > 0){
            jumpForce = jumpForce + (getGravity() * delta);
            if(vTime > .50)
                setRotation(.5f);
            //if the new jump force is less than 0, make it 0
            if(jumpForce < 0)
                jumpForce = 0;
        }

        setyTranslation(getyTranslation() + ((getGravity() + jumpForce) * delta));
    }

    //Process an animation by setting the frame and setting how long the animation has been going on (time determines frame)
    private boolean playAnimation(float delta, ArrayList<BufferedImage> animation){
        float animationTimeLength = animation.size() * frameTime;
        setFrame(delta, animation);
        if(aTime >= animationTimeLength){
            aTime = aTime - animationTimeLength;
            return true;
        }
        return false;
    }

    //Switch an animation's frame based on time.
    private void setFrame(float delta, ArrayList<BufferedImage> animation){
        aTime = aTime + delta;
        for(int i = 1; i <= animation.size(); i++){
            if(aTime < frameTime * i){
                setCurrentSpriteFrame(animation.get(i - 1));
                break;
            }
        }
    }
}