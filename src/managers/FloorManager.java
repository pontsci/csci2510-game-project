package managers;

import sprite.world.Floor;
import util.Matrix3x3f;
import util.Vector2f;

public class FloorManager extends Manager{
    //Get the floor's sprite sheet and make a floor.
    public FloorManager(){
        getSprites().add(new Floor(0, -3.75f, new Vector2f(14,14)));
    }


    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        //not needed
    }
}