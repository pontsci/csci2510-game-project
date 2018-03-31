package sprite;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainCharacter extends Sprite{
    private float animationTime = 0;//Keeps track of how long an animation has been going in order to show the correct frame
    private int playAnimation = 1;
    private ArrayList<BufferedImage> moveAnimation = new ArrayList<>();
    private ArrayList<BufferedImage> idleAnimation = new ArrayList<>();
    private ArrayList<BufferedImage> jumpAnimation = new ArrayList<>();
    private float frameTime = .1f; //time between each frame
    private float walkRate = 2.5f;//Walk rate per second. (The world is 16 by 9)

    public MainCharacter(float startX, float startY, Vector2f scale, ArrayList<BufferedImage> spriteAnimations){
        super(startX, startY, scale, spriteAnimations.get(1).getSubimage(0,0, 237, 356));

        //Pull the individual frames out now to prevent doing the work
        //of cutting out a subimage everytime the object needs a frame
        for(int i = 0; i < 6; i++)
            moveAnimation.add(spriteAnimations.get(0).getSubimage(237 * i,0, 237, 356));

        for(int i = 0; i < 5; i++)
            idleAnimation.add(spriteAnimations.get(1).getSubimage(237 * i,0, 237, 356));

        for(int i = 0; i < 7; i++)
            jumpAnimation.add(spriteAnimations.get(2).getSubimage(237 * i,0,237,356));

        initializeHitboxes();
    }

    //Initialize the main character's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        //Create the hitboxes
        hitboxes.add(new BoundingBox(new Vector2f(-1.25f, -2), new Vector2f( 1.1f, 1.9f),  getxTranslation(), getyTranslation(), Color.BLUE));
        hitboxes.add(new BoundingCircle(.35f,-.12f,1.4f, Color.RED));
        hitboxes.add(new BoundingBox(new Vector2f(-1.1f, -1.9f), new Vector2f( 1.1f, 1.15f),  getxTranslation(), getyTranslation(), Color.RED));
    }

    @Override
    //Process the constant gravity applied to the main character
    public void process(float delta){
        setyTranslation(getyTranslation() + (getGravity() * delta));
        processAnimations(delta);
    }

    //Process which animation is playing
    private void processAnimations(float delta){
        switch(playAnimation){
            //move Animation
            case 0:
                playAnimation(delta, moveAnimation);//Returns true when animation is complete
                break;
            //idle Animation
            case 1:
                playAnimation(delta, idleAnimation);//Returns true when animation is complete
                break;
            //jump Animation
            case 2:
                if(playAnimation(delta, jumpAnimation)){//Returns true when animation is complete
                    //Go back to idle when jump is complete
                    playAnimation = 1;
                }
                break;
        }
    }

    //Process an animation by setting the frame and setting how long the animation has been going on (time determines frame)
    //Returns true when animation is complete
    private boolean playAnimation(float delta, ArrayList<BufferedImage> animation){
        float animationTimeLength = animation.size() * frameTime;
        setFrame(delta, animation);
        if(animationTime >= animationTimeLength){
            animationTime = animationTime - animationTimeLength;
            return true;
        }
        return false;
    }

    //Switch an animation's frame based on time.
    private void setFrame(float delta, ArrayList<BufferedImage> animation){
        animationTime = animationTime + delta;
        for(int i = 1; i <= animation.size(); i++){
            if(animationTime < frameTime * i){
                setCurrentSpriteFrame(animation.get(i - 1));
                break;
            }
        }
    }

    //play left move animation
    public void walkLeft(float delta){
        if(playAnimation != 2){
            if(playAnimation != 0){
                animationTime = 0;
                playAnimation = 0;
            }
        }
        setxTranslation(getxTranslation() - (walkRate * delta));
        setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    //play right move animation
    public void walkRight(float delta){
        if(playAnimation != 2){
            if(playAnimation != 0){
                animationTime = 0;
                playAnimation = 0;
            }
        }
        setxTranslation(getxTranslation() + (walkRate * delta));
        setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    //play jump animation
    public void jump(){
        if(playAnimation != 2){
            animationTime = 0;
            playAnimation = 2;
        }
    }

    //Play idle animation as long as the main character is not jumping or already idle.
    public void idle(){
        if(playAnimation != 2 && playAnimation != 1){
            playAnimation = 1;
        }
    }
}
