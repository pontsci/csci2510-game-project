package managers;

import sprite.Sprite;
import sprite.character.CharacterSprite;
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

    public void checkCollision(float delta, Matrix3x3f viewport){
        for(Sprite sprite : sprites){
            if(sprite instanceof CharacterSprite){
                ((CharacterSprite)sprite).checkCollision(delta, viewport);
            }
            else//if not the correct sprite, back out. A Manager only contains one kind of sprite.
                return;
        }
    }

    //Getter for the sprites
    public ArrayList<Sprite> getSprites(){
        return sprites;
    }
}
