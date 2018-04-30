package sprite.world;

import bounding.BoundingBox;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends Sprite{

    public Wall(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale);
        //Starting Frame
        setCurrentSpriteFrame(spriteSheet.getSubimage(120, 0, 400, 467));
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-.3f, -2.6f), new Vector2f( .85f, 2.5f),  Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.3f, -2.6f), new Vector2f( .85f, 2.5f),  Color.RED));
    }

    @Override
    public void process(float delta){

    }
}
