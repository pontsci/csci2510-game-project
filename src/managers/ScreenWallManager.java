package managers;

import spawning.Spawner;
import sprite.world.ScreenWall;
import util.Matrix3x3f;
import util.Vector2f;

public class ScreenWallManager extends Manager{
    //Get the wall's sprite sheet and make two walls on either side of the screen
    public void initialize(){
        //No more than two outer walls can be added, walls use a very specific collision method that only pushes one direction
        getSprites().add(new ScreenWall(-7.8f, 0f, new Vector2f(.2f,.2f), loadFile("src/resources/world/foreground/wall/TallWall_WH_314_4200.png")));
        getSprites().add(new ScreenWall(7.8f, 0f, new Vector2f(-.2f,.2f), loadFile("src/resources/world/foreground/wall/TallWall_WH_314_4200.png")));
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        //not needed
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        //not needed
    }
}

