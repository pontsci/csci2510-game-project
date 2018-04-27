package sprite.world;

import bounding.BoundingBox;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenWall extends Sprite {
    public ScreenWall(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
        //Starting Frame
        setCurrentSpriteFrame(loadFile("src/resources/world/foreground/wall/TallWall_WH_314_4200.png"));
        initializeHitboxes();
    }

    //Initialize the wall's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-5f, -24f), new Vector2f( 1.4f, 24f),  Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-5f, -24f), new Vector2f( 1.4f, 24f), Color.RED));
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}
