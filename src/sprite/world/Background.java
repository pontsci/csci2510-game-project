package sprite.world;

import sprite.Sprite;
import util.Vector2f;

import java.awt.image.BufferedImage;

//If anything special were to happen to the background, such as sliding, it would occur here
public class Background extends Sprite {
    public Background(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale);
        setCurrentSpriteFrame(spriteSheet);
    }

    @Override
    public void process(float delta){
        //Not needed unless the background were to slide or something special like that
    }
}
