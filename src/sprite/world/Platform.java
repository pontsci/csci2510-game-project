package sprite.world;

import bounding.BoundingBox;
import sprite.Sprite;
import util.Matrix3x3f;
import util.Vector2f;
import spawning.SpawnRange;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Platform extends Sprite{

    public Platform(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale);
        setCurrentSpriteFrame(spriteSheet);
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
    public SpawnRange getSpawnRange(Matrix3x3f viewport){
        float ypadding = 0.1f;
        float xpadding = 1.5f;
        float minX;
        float maxX;
        float y;

        hitboxes.get(0).updateWorld(viewport);
        Vector2f point = ((BoundingBox)hitboxes.get(0)).getCurrentMax();
        minX = getxTranslation() - point.x / xpadding;
        y = getyTranslation() + ypadding + point.y / 2;

        maxX = getxTranslation() + point.x / xpadding;
        return new SpawnRange(minX,maxX,y, viewport);
    }

}
