package managers;

import sprite.world.Background;
import util.Vector2f;

//Could be used to handle which background to play on each level of the game
public class BackgroundManager extends Manager{
    //Get the background's sprite sheet and make a background.
    public BackgroundManager(){
        getSprites().add(new Background(0, 0, new Vector2f(1,1)));
    }
}
