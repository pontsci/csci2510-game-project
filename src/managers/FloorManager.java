package managers;

import spawning.Spawner;
import sprite.world.Floor;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;

public class FloorManager extends Manager{
    private BufferedImage floorSpriteSheet = loadFile("src/resources/world/foreground/platform/PlatformResized_WH_448x119.png");

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        //not needed
    }

    /**
     * Depending on the level, change where floors spawn
     * @param level the current level
     * @param spawner the spawn system
     * @param viewport the viewport
     */
    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 1:
                //floors



                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

        }
    }
}