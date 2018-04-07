package managers;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import sprite.character.CharacterSprite;
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

     //Checks if two sprites are intersecting returns true if they are
    public boolean innerHitboxCollision(ArrayList<BoundingShape> firstHitboxes, ArrayList<BoundingShape> secondHitboxes){
        BoundingShape fh;
        BoundingShape sh;
        for(int i = 1; i < firstHitboxes.size(); i++){
            fh = firstHitboxes.get(i);
            for(int j = 1; j < secondHitboxes.size(); j++){
                sh = secondHitboxes.get(j);
                //check if two shapes are intersecting and return true if so
                if(fh instanceof BoundingBox && sh instanceof BoundingBox){
                    if(Intersect.intersectAABB(((BoundingBox)fh).getCurrentMin(), ((BoundingBox)fh).getCurrentMax(), ((BoundingBox)sh).getCurrentMin(), ((BoundingBox)sh).getCurrentMax()))
                        return true;
                }
                else if(fh instanceof BoundingBox && sh instanceof BoundingCircle){
                    if(Intersect.intersectCircleAABB(((BoundingCircle)sh).getCurrentPoint(), ((BoundingCircle)sh).getCurrentRadius(), ((BoundingBox)fh).getCurrentMin(), ((BoundingBox)fh).getCurrentMax()))
                        return true;
                }
                else if(fh instanceof BoundingCircle && sh instanceof BoundingBox){
                    if(Intersect.intersectCircleAABB(((BoundingCircle)fh).getCurrentPoint(), ((BoundingCircle)fh).getCurrentRadius(), ((BoundingBox)sh).getCurrentMin(), ((BoundingBox)sh).getCurrentMax()))
                        return true;
                }
                else if(fh instanceof BoundingCircle && sh instanceof BoundingCircle){
                    if(Intersect.intersectCircle(((BoundingCircle)fh).getCurrentPoint(), ((BoundingCircle)fh).getCurrentRadius(), ((BoundingCircle)sh).getCurrentPoint(), ((BoundingCircle)sh).getCurrentRadius()))
                        return true;
                }
            }
        }
        return false;
    }

    public void checkCollision(float delta, Matrix3x3f viewport){
        for(Sprite sprite : sprites){
            if(sprite instanceof CharacterSprite){
                ((CharacterSprite)sprite).checkCollision(delta);
            }
            else//if not the correct sprite, back out. A Manager only contains one kind of sprite.
                return;
        }
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
