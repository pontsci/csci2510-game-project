package sprite.world;

import bounding.BoundingShape;
import sprite.Sprite;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class Door extends Sprite{
    protected boolean open;
    protected ArrayList<BoundingShape> hitboxes = new ArrayList<>();
    BufferedImage spriteSheet;

    public Door(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
        spriteSheet = loadFile("src/resources/foreground/door/door.png");
        setCurrentSpriteFrame(spriteSheet.getSubimage(0, 0, spriteSheet.getWidth()/2, spriteSheet.getHeight()));
    }

    public  void initializeHitboxes(){

    }

    public void process(float delta){
    // not needed
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
