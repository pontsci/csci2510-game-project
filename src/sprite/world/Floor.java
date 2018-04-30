package sprite.world;

import bounding.BoundingBox;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Floor extends Sprite {
    public Floor(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale);
        setCurrentSpriteFrame(spriteSheet);
        initializeHitboxes();
    }

    //Initialize the floor's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-2.2f, -5f), new Vector2f( 2.2f, .4f),  Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-2.2f, -5f), new Vector2f( 2.2f, .4f), Color.RED));
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}