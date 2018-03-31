package sprite;

import bounding.BoundingBox;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Floor extends Sprite{
    public Floor(float startX, float startY, Vector2f scale, ArrayList<BufferedImage> spriteAnimations){
        super(startX, startY, scale, spriteAnimations.get(0).getSubimage(0,0,128,8));
        initializeHitboxes();
    }

    //Initialize the floor's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        //May consider adding an option where I don't need an inner box for the floor collision and such
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -5f), new Vector2f( .9f, .04f),  0, getyTranslation(), Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -5f), new Vector2f( .9f, .04f),  0, getyTranslation(), Color.RED));
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}