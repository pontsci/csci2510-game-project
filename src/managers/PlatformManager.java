package managers;

import sprite.world.Platform;
import util.Vector2f;

public class PlatformManager extends Manager{
    public PlatformManager(){
        getSprites().add(new Platform(6, -1, new Vector2f(.75f,.5f)));
    }
}
