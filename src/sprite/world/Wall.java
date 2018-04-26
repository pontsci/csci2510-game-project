package sprite.world;

import bounding.BoundingBox;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;

public class Wall extends Sprite{

    public Wall(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
        //Starting Frame
        setCurrentSpriteFrame(loadFile("src/resources/world/foreground/platform/PlatformResized_WH_448x119.png"));
        setRotation(1.57f);
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-.5f, -2.2f), new Vector2f( .5f, 2.2f),  Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.5f, -2.2f), new Vector2f( .5f, 2.2f), Color.RED));
    }

    @Override
    public void process(float delta){

    }
}
