package managers;

import sprite.world.Wall;
import util.Vector2f;

public class WallManager extends Manager{
    //Get the wall's sprite sheet and make two walls on either side of the screen
    public WallManager(){
        getSprites().add(new Wall(-7.9f, 1, new Vector2f(14,14)));
        getSprites().add(new Wall(7.9f, 1, new Vector2f(-14,14)));
    }
}

