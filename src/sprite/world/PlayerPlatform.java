package sprite.world;

import util.Vector2f;

import java.awt.image.BufferedImage;

public class PlayerPlatform extends Platform {
    public PlayerPlatform(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale, spriteSheet);
    }
}
