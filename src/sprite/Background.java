package sprite;

import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

//If anything special were to happen to the background, such as sliding, it would occur here
public class Background extends Sprite{
    public Background(float startX, float startY, Vector2f scale, ArrayList<BufferedImage> spriteAnimations){
        super(startX, startY, scale, spriteAnimations.get(0).getSubimage(0,0,128,72));
    }

    @Override
    public void initializeHitboxes(){
        //Not needed
    }

    @Override
    public void process(float delta){
        //Not needed unless the background were to slide or something special like that
    }
}
