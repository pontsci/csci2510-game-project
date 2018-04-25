package managers;

import spawning.Spawner;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.bullet.Bullet;
import util.Matrix3x3f;

import java.awt.*;
import java.util.ArrayList;

//Managers deal with the entire population of sprites and how sprites interact with other sprites.
//This includes collision between sprites, and when to add/remove more sprites.
public abstract class Manager{
    private ArrayList<Sprite> sprites = new ArrayList<>();

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

    public abstract void checkCollision(float delta, Matrix3x3f viewport);

    //Getter for the sprites
    public ArrayList<Sprite> getSprites(){
        return sprites;
    }

    //Given an int, set a new level
    public abstract void switchLevel(int level, Spawner spawner, Matrix3x3f viewport);
}
