package sprite.world;

import bounding.BoundingBox;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Wall extends Sprite {
    public Wall(float startX, float startY, Vector2f scale, ArrayList<BufferedImage> spriteAnimations){
        super(startX, startY, scale, spriteAnimations.get(0).getSubimage(0,0,4,72));
        initializeHitboxes();
    }

    //Initialize the wall's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-5f, -.5f), new Vector2f( .021f, .6f),  getxTranslation(), 0, Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-5f, -.5f), new Vector2f( .021f, -.195f),  getxTranslation(), 0, Color.RED));
        hitboxes.add(new BoundingBox(new Vector2f(-5f, -.195f), new Vector2f( .011f, .5f),  getxTranslation(), 0, Color.RED));
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}
