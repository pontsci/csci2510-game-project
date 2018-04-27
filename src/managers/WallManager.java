package managers;

import spawning.Spawner;
import sprite.world.Wall;
import util.Matrix3x3f;
import util.Vector2f;

public class WallManager extends Manager{

    //Get the wall's sprite sheet and make two walls on either side of the screen
    public void initialize(){
        //No more than two outer walls can be added, walls use a very specific collision method that only pushes one direction
        getSprites().add(new Wall(0, -.28f, new Vector2f(.5f,.57f)));
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){

    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 1:
                break;
            case 2:
                getSprites().add(new Wall(0, -.28f, new Vector2f(.5f,.57f)));
                break;
            case 3:
                getSprites().add(new Wall(-4.64f, 1f, new Vector2f(.5f,.57f)));
                getSprites().add(new Wall(0.87f, -.28f, new Vector2f(.5f,.57f)));
                getSprites().add(new Wall(0.87f, -2.78f, new Vector2f(.5f,.57f)));
                getSprites().add(new Wall(4.64f, 2.59f, new Vector2f(.5f,.57f)));
                getSprites().add(new Wall(4.64f, -0.28f, new Vector2f(.5f,.57f)));
                getSprites().add(new Wall(4.64f, 5.1f, new Vector2f(.5f,.57f)));
                break;
        }
    }
}
