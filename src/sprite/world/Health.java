package sprite.world;

import java.awt.Graphics;
import sprite.Sprite;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.lang.management.BufferPoolMXBean;

public class Health extends Sprite{
    private boolean active;
    
    public Health(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale);
        setCurrentSpriteFrame(spriteSheet);
        active = true;
    }

    @Override
    public void process(float delta){
        //Not needed
    }
    
    @Override
    public void render(Graphics g){
        if(active){
            super.render(g);
        }
    }
    
    public void setActive (boolean change){
        active = change;
    }
    
    public boolean getActive(){
        return active;
    }
    
}
