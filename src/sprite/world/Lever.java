package sprite.world;

import bounding.BoundingBox;
import sprite.CollidableSprite;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Lever extends CollidableSprite{
    private ArrayList<Sprite> enemies;
    private BufferedImage spriteSheet;
    public Lever(float startX, float startY, Vector2f scale, ArrayList<Sprite> enemies, BufferedImage spriteSheet){
        super(startX, startY, scale);
        this.spriteSheet = spriteSheet;
        setCurrentSpriteFrame(spriteSheet);
        this.enemies = enemies;
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        //Hitboxes are in the sprite class so you did not need to have a hitboxes variable in here.
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -1.0f), new Vector2f( .6f, 0.2f),  Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -1.0f), new Vector2f( .6f, 0.2f), Color.RED));
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}