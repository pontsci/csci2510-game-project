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

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 1:
                getSprites().add(new Floor(-6.4f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(-3.2f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(0.0f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(3.2f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(6.4f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                break;
            case 2:
                getSprites().add(new Floor(-6.4f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(-3.2f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(0.0f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(3.2f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(6.4f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                break;
            case 3:
                getSprites().add(new Floor(-6.4f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(-3.2f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(0.0f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(5.2f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(6.4f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                break;
            case 4:
                getSprites().add(new Floor(-3.2f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(0.0f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
                getSprites().add(new Floor(2.05f, -4.3f,  new Vector2f(.85f,.5f), floorSpriteSheet));
        }
    }
}