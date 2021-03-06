package managers;

import spawning.Spawner;
import sprite.Sprite;
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

    /**
     * For every sprite in the manager, process what's constantly affecting the sprite
     * Example: Gravity, or when a mouse is supposed to change directions/jump
     * @param delta time
     */
    public void process(float delta){
        for(Sprite sprite : sprites){
            sprite.process(delta);
        }
    }

    /**
     * For every sprite in the manager, where it's supposed to be located in the world
     * @param delta time
     * @param viewport the viewport
     */
    public void update(float delta, Matrix3x3f viewport){
        for(Sprite sprite : sprites){
            sprite.update(delta, viewport);
        }
    }

    /**
     * For every sprite in the manager, render it's location in the world
     * @param g graphics
     */
    public void render(Graphics g){
        for(Sprite sprite : sprites){
            sprite.render(g);
        }
    }

    /**
     * For every sprite in the manager, render it's hitboxes' location in the world
     * @param g graphics
     */
    public void renderHitboxes(Graphics g){
        for(Sprite sprite : sprites){
            sprite.renderHitboxes(g);
        }
    }

    /**
     * Check sprite collision
     * @param delta time
     * @param viewport the viewport
     */
    public abstract void checkCollision(float delta, Matrix3x3f viewport);

    /**
     * Getter for the sprites
     * @return sprites
     */
    public ArrayList<Sprite> getSprites(){
        return sprites;
    }

    /**
     * Given an int, switch to a new level
     * @param level the current level
     * @param spawner the spawn system
     * @param viewport the viewport
     */
    public abstract void switchLevel(int level, Spawner spawner, Matrix3x3f viewport);

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
}
