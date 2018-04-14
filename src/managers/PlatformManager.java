package managers;

import sprite.world.Platform;
import util.Vector2f;

public class PlatformManager extends Manager{
    public PlatformManager(){
        getSprites().add(new Platform(6.5f, -1, new Vector2f(.75f,.5f)));
        getSprites().add(new Platform(3.5f, -1, new Vector2f(.75f, .5f)));
        getSprites().add(new Platform(5.5f, 1.5f, new Vector2f(.75f,.5f)));
        getSprites().add(new Platform(3.5f, 1.5f, new Vector2f(.75f, .5f)));
        getSprites().add(new Platform(-4, 1.5f, new Vector2f(.75f, .5f)));
    }
}
