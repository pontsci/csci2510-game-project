package managers;

import spawning.Spawner;
import sprite.world.Wall;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;

public class WallManager extends Manager{
    private BufferedImage wallSpriteSheet = loadFile("src/resources/world/foreground/wall/WallBlock_WH_400x467_WallStand.png");

    /**
     * Get the wall's sprite sheet and make two walls on either side of the screen
     * @param delta time
     * @param viewport the viewport
     */
    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){

    }

    /**
     * Depending on the level, switch where walls are to be spawned
     * @param level the current level
     * @param spawner the spawn system
     * @param viewport the viewport
     */
    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 1:
                getSprites().add(new Wall(3.8f, -0.24f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(3.8f, -2.74f, new Vector2f(.5f,.58f), wallSpriteSheet));
                break;
            case 2:
                getSprites().add(new Wall(-4.67f, 3.35f, new Vector2f(.5f,.58f), wallSpriteSheet));//This wall is designed for the enemy to shoot through, but not the player.
                getSprites().add(new Wall(-4.67f, -0.26f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(-2.04f, -0.26f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(-2.04f, -3.11f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(1.74f, 5.14f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(1.74f, 2.24f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(4.35f, -0.24f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(4.35f, -3.11f, new Vector2f(.5f,.58f), wallSpriteSheet));
                break;
            case 3:
                getSprites().add(new Wall(-4.74f, 1f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(0.87f, -.28f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(0.37f, -2.78f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(4.44f, 2.59f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(4.44f, -0.28f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(4.44f, 5.1f, new Vector2f(.5f,.58f), wallSpriteSheet));
                break;
            case 4:
                getSprites().add(new Wall(-3.0f, 3.6f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(-3.0f, 0f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(1.24f, 2.24f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(1.24f, 4.96f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(3.48f, -2.79f, new Vector2f(.5f,.58f), wallSpriteSheet));
                getSprites().add(new Wall(4.25f, -0.25f, new Vector2f(.5f,.58f), wallSpriteSheet));
                break;
        }
    }
}
