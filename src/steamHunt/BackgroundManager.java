package steamHunt;

import sprite.Background;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Could be used to handle which background to play on each level of the game
public class BackgroundManager extends Manager{
    //Get the background's sprite sheet and make a background.
    public void initialize(){
        ArrayList<BufferedImage> spriteAnimations = new ArrayList<>();
        spriteAnimations.add(loadFile("src/resources/background/dungeon.png"));
        getSprites().add(new Background(0, 0, new Vector2f(14,14), spriteAnimations));
    }
}
