package managers;

import spawning.Spawner;
import sprite.Sprite;
import sprite.world.Door;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DoorManager extends Manager{
    private ArrayList<Sprite> enemies;
    private BufferedImage doorSpriteSheet = loadFile("src/resources/world/foreground/door/door.png");

    /**
     * initialize variables to passed parameters
     * @param enemies all enemies
     */
    public void initialize(ArrayList<Sprite> enemies){
        this.enemies = enemies;
    }


    public void checkCollision(float delta, Matrix3x3f viewport){
        //Not needed, the character interacts with the door
    }

    /**
     * Depending on the level, switch where the door is
     * @param level the current level
     * @param spawner the spawn system
     * @param viewport the viewport
     */
    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        //Could just close the door and translate it to improve effecientcy rather tan completely recreate a new door.
        getSprites().clear();
        switch(level){
            case 1:
                getSprites().add(new Door(7.12f,-3.47f ,new Vector2f(.35f, .35f), enemies, doorSpriteSheet));
                break;
            case 2:
                //move and close the current door
                getSprites().add(new Door(-7.14f,1.82f ,new Vector2f(.35f, .35f), enemies, doorSpriteSheet));
                break;
            case 3:
                getSprites().add(new Door(7.12f,1.82f ,new Vector2f(.35f, .35f), enemies, doorSpriteSheet));
                break;
            case 4:
                getSprites().add(new Door(7, -0.68f, new Vector2f(.35f, .35f), enemies, doorSpriteSheet));
                break;
            case 5:
                getSprites().add(new Door(-7, -3.48f, new Vector2f(.35f, .35f), enemies, doorSpriteSheet));
                break;
            case 6:
                getSprites().add(new Door(7, 1.82f, new Vector2f(.35f, .35f), enemies, doorSpriteSheet));
                break;
        }
    }
}
