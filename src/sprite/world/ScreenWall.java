package sprite.world;

import bounding.BoundingBox;
import sprite.CollidableSprite;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenWall extends CollidableSprite {
    public ScreenWall(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale);
        //Starting Frame
        setCurrentSpriteFrame(spriteSheet);
        initializeHitboxes();
    }

    //Initialize the wall's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-5f, -24f), new Vector2f( 1.5f, 24f),  Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-5f, -24f), new Vector2f( 1.5f, 24f), Color.RED));
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}
