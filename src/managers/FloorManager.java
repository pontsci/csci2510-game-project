package managers;

import spawning.Spawner;
import sprite.world.Floor;
import util.Matrix3x3f;
import util.Vector2f;

public class FloorManager extends Manager{
    //Get the floor's sprite sheet and make a floor.
    public void initialize(){
        getSprites().add(new Floor(-6.4f, -4.3f,  new Vector2f(.75f,.5f)));
        getSprites().add(new Floor(-3.2f, -4.3f,  new Vector2f(.75f,.5f)));
        getSprites().add(new Floor(0.0f, -4.3f,  new Vector2f(.75f,.5f)));
        getSprites().add(new Floor(3.2f, -4.3f,  new Vector2f(.75f,.5f)));
        getSprites().add(new Floor(6.4f, -4.3f,  new Vector2f(.75f,.5f)));
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        //not needed
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){

    }
}