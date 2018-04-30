package sprite.screens;

import sprite.Sprite;
import util.Vector2f;

import java.awt.image.BufferedImage;

public class DisplayScreen extends Sprite {
    public DisplayScreen(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale);
        setCurrentSpriteFrame(spriteSheet);

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
