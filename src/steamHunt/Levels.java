package steamHunt;

import sprite.Sprite;
import sprite.world.Platform;
import util.Vector2f;

import java.util.ArrayList;

public class Levels {

    public static ArrayList<Sprite> getLevel(int level){
        ArrayList<Sprite> platforms = new ArrayList<>();
        if(level == 1){
            platforms.add(new Platform(6.5f, -1, new Vector2f(.75f,.5f)));
            platforms.add(new Platform(3.5f, -1, new Vector2f(.75f, .5f)));
            platforms.add(new Platform(5.5f, 1.5f, new Vector2f(.75f,.5f)));
            platforms.add(new Platform(3.5f, 1.5f, new Vector2f(.75f, .5f)));
            platforms.add(new Platform(-4, 1.5f, new Vector2f(.75f, .5f)));
        }
        else if( level == 2){
            platforms.add(new Platform(0.0f, -1, new Vector2f(.75f,.5f)));
            platforms.add(new Platform(0.0f, 1.5f, new Vector2f(.75f,.5f)));
            platforms.add(new Platform(6.5f, -1, new Vector2f(.75f,.5f)));
            platforms.add(new Platform(-6.5f, -1, new Vector2f(.75f,.5f)));
        }
        return platforms;
    }
}
