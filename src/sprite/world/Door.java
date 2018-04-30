package sprite.world;

import bounding.BoundingBox;
import sprite.CollidableSprite;
import sprite.Sprite;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class Door extends CollidableSprite {
    private boolean open;
    private ArrayList<Sprite> enemies;
    private BufferedImage spriteSheet;

    public Door(float startX, float startY, Vector2f scale, ArrayList<Sprite> enemies, BufferedImage spriteSheet){
        super(startX, startY, scale);
        this.spriteSheet = spriteSheet;
        setCurrentSpriteFrame(spriteSheet.getSubimage(0, 0, spriteSheet.getWidth()/2, spriteSheet.getHeight()));
        this.enemies = enemies;
        initializeHitboxes();
    }

    @Override
    public  void initializeHitboxes(){
        //Hitboxes are in the sprite class so you did not need to have a hitboxes variable in here.
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -1.8f), new Vector2f( .9f, 1.3f),  Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -1.8f), new Vector2f( .9f, 1.3f), Color.RED));
    }

    public void process(float delta){
        //Process whether the door is open or closed
        if (enemies.isEmpty()){
            setOpen();
        }
        else{
            setClose();
        }
    }

    public void setOpen(){
        open = true;
        setCurrentSpriteFrame(spriteSheet.getSubimage(spriteSheet.getWidth()/2, 0, spriteSheet.getWidth()/2, spriteSheet.getHeight()));
    }

    public void setClose(){
        open = false;
        setCurrentSpriteFrame(spriteSheet.getSubimage(0, 0, spriteSheet.getWidth()/2, spriteSheet.getHeight()));
    }

    public boolean isOpen(){
        return open;
    }
}
