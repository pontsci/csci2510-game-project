package managers;

import sprite.world.Wall;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WallManager extends Manager{
    //Get the wall's sprite sheet and make two walls on either side of the screen
    public void initialize(){
        ArrayList<BufferedImage> spriteAnimations = new ArrayList<>();
        spriteAnimations.add(loadFile("src/resources/world/foreground/wall/wall.png"));
        getSprites().add(new Wall(-7.9f, 1, new Vector2f(14,14), spriteAnimations));
        getSprites().add(new Wall(7.9f, 1, new Vector2f(-14,14), spriteAnimations));
    }
}

