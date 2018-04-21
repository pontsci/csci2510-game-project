package sprite.world;

import bounding.BoundingBox;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;

public class Floor extends Sprite {
    public Floor(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
        setCurrentSpriteFrame(loadFile("src/resources/world/foreground/floor/floor.png"));
        initializeHitboxes();
    }

    //Initialize the floor's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        //May consider adding an option where I don't need an inner box for the floor collision and such
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -5f), new Vector2f( .9f, .04f), Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -5f), new Vector2f( .9f, .04f), Color.RED));
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}