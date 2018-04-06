package managers;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import util.Intersect;
import util.Matrix3x3f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//Managers deal with the entire population of sprites and how sprites interact with other sprites.
//This includes collision between sprites, and when to add/remove more sprites.
public abstract class Manager{
    private ArrayList<Sprite> sprites = new ArrayList<>();

    //Managers will load an image and make a sprite with that image
    public abstract void initialize();

    //Load an image and return the found image
    protected BufferedImage loadFile(String fileName) {
        BufferedImage spriteSheet;
        try {
            spriteSheet = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            spriteSheet = null;
        }
        return spriteSheet;
    }

    //For every sprite in the manager, process what's constantly affecting the sprite
    // Example) Gravity, or when a mouse is supposed to change directions/jump
    public void process(float delta){
        for(Sprite sprite : sprites){
            sprite.process(delta);
        }
    }

    //For every sprite in the manager, where it's supposed to be located in the world
    public void update(float delta, Matrix3x3f viewport){
        for(Sprite sprite : sprites){
            sprite.update(delta, viewport);
        }
    }

    //For every sprite in the manager, render it's location in the world
    public void render(Graphics g){
        for(Sprite sprite : sprites){
            sprite.render(g);
        }
    }

    //For every sprite in the manager, render it's hitboxes' location in the world
    public void renderHitboxes(Graphics g){
        for(Sprite sprite : sprites){
            sprite.renderHitboxes(g);
        }
    }

    //I'm planning on going over collision with Mason more to make this cleaner. It may be moved to the sprite superclass instead of a manager.

    //Checks if two sprites are intersecting returns true if they are
    public boolean innerHitboxCollision(ArrayList<BoundingShape> sprite1Hitboxes, ArrayList<BoundingShape> sprite2Hitboxes){
        for(int i = 1; i < sprite1Hitboxes.size(); i++){
            for(int j = 1; j < sprite2Hitboxes.size(); j++){
                //check if two shapes are intersecting and return true if so
                if(sprite1Hitboxes.get(i) instanceof BoundingBox && sprite2Hitboxes.get(j) instanceof BoundingBox){
                    if(Intersect.intersectAABB(sprite1Hitboxes.get(i).getCurrentMin(), sprite1Hitboxes.get(i).getCurrentMax(), sprite2Hitboxes.get(j).getCurrentMin(), sprite2Hitboxes.get(j).getCurrentMax()))
                        return true;
                }
                else if(sprite1Hitboxes.get(i) instanceof BoundingBox && sprite2Hitboxes.get(j) instanceof BoundingCircle){
                    if(Intersect.intersectCircleAABB(sprite2Hitboxes.get(j).getCurrentPoint(), sprite2Hitboxes.get(j).getCurrentRadius(), sprite1Hitboxes.get(i).getCurrentMin(), sprite1Hitboxes.get(i).getCurrentMax()))
                        return true;
                }
                else if(sprite1Hitboxes.get(i) instanceof BoundingCircle && sprite2Hitboxes.get(j) instanceof BoundingBox){
                    if(Intersect.intersectCircleAABB(sprite1Hitboxes.get(i).getCurrentPoint(), sprite1Hitboxes.get(i).getCurrentRadius(), sprite2Hitboxes.get(j).getCurrentMin(), sprite2Hitboxes.get(j).getCurrentMax()))
                        return true;
                }
                else if(sprite1Hitboxes.get(i) instanceof BoundingCircle && sprite2Hitboxes.get(j) instanceof BoundingCircle){
                    if(Intersect.intersectCircle(sprite1Hitboxes.get(i).getCurrentPoint(), sprite1Hitboxes.get(i).getCurrentRadius(), sprite2Hitboxes.get(j).getCurrentPoint(), sprite2Hitboxes.get(j).getCurrentRadius()))
                        return true;
                }
            }
        }
        return false;
    }

    //Check for collision with the wall
    public void checkWallCollision(ArrayList<Sprite> walls, float delta, Matrix3x3f viewport){
        for(Sprite spriteOutHitbox : getSprites()){
            BoundingBox SOH = ((BoundingBox)spriteOutHitbox.getHitboxes().get(0));//get the sprite's outer hitbox
            for(int i = 0; i < 2; i++){
                BoundingBox WOH = ((BoundingBox)walls.get(i).getHitboxes().get(0));//get the wall's outer hitbox
                if(Intersect.intersectAABB(SOH.getCurrentMin(), SOH.getCurrentMax(), WOH.getCurrentMin(), WOH.getCurrentMax())) {
                    //While some innerbox collides
                    while(innerHitboxCollision(spriteOutHitbox.getHitboxes(), walls.get(i).getHitboxes())){
                        //Left wall hit, move mouse right
                        if(i == 0){
                            spriteOutHitbox.setxTranslation(spriteOutHitbox.getxTranslation() + .0001f);
                            spriteOutHitbox.update(delta, viewport);
                        }
                        //Right wall hit, move mouse left
                        else if(i == 1){
                            spriteOutHitbox.setxTranslation(spriteOutHitbox.getxTranslation() - .0001f);
                            spriteOutHitbox.update(delta, viewport);
                        }
                    }
                }
            }
        }
    }

    //Check for collision with the floor
    public void checkFloorCollision(ArrayList<Sprite> floors, float delta, Matrix3x3f viewport){
        for(Sprite cat : getSprites()){
            BoundingBox cOH = ((BoundingBox)cat.getHitboxes().get(0));//get the cat's outer hitbox
            BoundingBox fOH = ((BoundingBox)floors.get(0).getHitboxes().get(0));//get the cat's outer hitbox
            if(Intersect.intersectAABB(cOH.getCurrentMin(), cOH.getCurrentMax(), fOH.getCurrentMin(), fOH.getCurrentMax())){
                //While some innerbox collides
                while(innerHitboxCollision(cat.getHitboxes(), floors.get(0).getHitboxes())){
                    cat.setRotation(0);//Make character level with the ground
                    cat.setyTranslation(cat.getyTranslation() + .0001f);
                    cat.update(delta, viewport);
                }
            }
        }
    }

    //Getter for the sprites
    public ArrayList<Sprite> getSprites(){
        return sprites;
    }
}
