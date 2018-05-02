package sprite.world;

import util.Vector2f;

import java.awt.image.BufferedImage;

public class PlayerFloor extends Floor{
    public PlayerFloor(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale, spriteSheet);
    }
}
