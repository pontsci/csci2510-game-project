package sprite.world;

import sprite.Sprite;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.lang.management.BufferPoolMXBean;

public class Health extends Sprite{
    public Health(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale);
        setCurrentSpriteFrame(spriteSheet);
    }

    @Override
    public void initializeHitboxes(){
        //Not needed
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}
