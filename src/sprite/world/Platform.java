package sprite.world;

import bounding.BoundingBox;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;

public class Platform extends Sprite{

    public Platform(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
        setCurrentSpriteFrame(loadFile("src/resources/world/foreground/platform/PlatformResized_WH_448x119.png"));
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-2.2f, -.5f), new Vector2f( 2.2f, .4f),  Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-2.2f, -.5f), new Vector2f( 2.2f, .4f), Color.RED));

    }

    @Override
    public void process(float delta){

    }
}
