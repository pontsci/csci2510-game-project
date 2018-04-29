package sprite.world;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import sprite.character.enemy.Enemy;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class Door extends Sprite{
    private boolean open;
    private ArrayList<Sprite> enemies;
    private BufferedImage spriteSheet;

    public Door(float startX, float startY, Vector2f scale, ArrayList<Sprite> enemies){
        super(startX, startY, scale);
        spriteSheet = loadFile("src/resources/world/foreground/door/door.png");
        setCurrentSpriteFrame(spriteSheet.getSubimage(0, 0, spriteSheet.getWidth()/2, spriteSheet.getHeight()));
        this.enemies = enemies;
        initializeHitboxes();
    }

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
