package steamHunt;

import sprite.Floor;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FloorManager extends Manager{
    //Get the floor's sprite sheet and make a floor.
    public void initialize(){
        ArrayList<BufferedImage> spriteAnimations = new ArrayList<>();
        spriteAnimations.add(loadFile("src/resources/foreground/floor/floor.png"));
        getSprites().add(new Floor(0, -3.75f, new Vector2f(14,14), spriteAnimations));
    }
}