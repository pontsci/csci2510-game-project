package sprite.world;

import bounding.BoundingBox;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;

public class ScreenWall extends Sprite {
    public ScreenWall(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
        //Starting Frame
        setCurrentSpriteFrame(loadFile("src/resources/world/foreground/wall/Wall_WH_85x1590.png"));
        initializeHitboxes();
    }

    //Initialize the wall's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-5f, -14f), new Vector2f( .45f, 14f),  Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-5f, -14f), new Vector2f( .45f, 14f), Color.RED));
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}
