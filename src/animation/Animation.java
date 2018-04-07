package animation;

import sprite.Sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation{
    ArrayList<ArrayList<BufferedImage>> animations = new ArrayList<>();
    private float animationTime = 0;//Keeps track of how long an animation has been going in order to show the correct frame
    private float frameTime = .1f; //time between each frame
    private int lastAnimation;

    //Add a new animation to animations
    public void addAnimation(BufferedImage spriteSheet, int numberOfFrames){
        ArrayList<BufferedImage> frames = new ArrayList<>();
        int frameHeight = spriteSheet.getHeight();
        int frameWidth = spriteSheet.getWidth() / numberOfFrames;
        for(int i = 0; i < numberOfFrames; i++)
            frames.add(spriteSheet.getSubimage(frameWidth * i,0, frameWidth, frameHeight));
        animations.add(frames);
    }

    //Process an animation by setting the frame and setting how long the animation has been going on (time determines frame)
    //Returns turn when the animation is complete
    public boolean playAnimation(float delta, int animation, Sprite sprite){
        if(lastAnimation != animation){
            animationTime = 0;
            lastAnimation = animation;
        }
        ArrayList<BufferedImage> currentAnimation = animations.get(animation);
        float animationTimeLength = currentAnimation.size() * frameTime;
        setFrame(delta, currentAnimation, sprite);
        if(animationTime >= animationTimeLength){
            animationTime = animationTime - animationTimeLength;
            return true;
        }
        return false;
    }

    //Switch an animation's frame based on time.
    private void setFrame(float delta, ArrayList<BufferedImage> animation, Sprite sprite){
        animationTime = animationTime + delta;
        for(int i = 1; i <= animation.size(); i++){
            if(animationTime < frameTime * i){
                sprite.setCurrentSpriteFrame(animation.get(i - 1));
                break;
            }
        }
    }
}
