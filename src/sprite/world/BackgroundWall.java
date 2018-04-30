package sprite.world;

import java.awt.image.BufferedImage;
import sprite.Sprite;
import util.Vector2f;

//If anything special were to happen to the background, such as sliding, it would occur here
public class BackgroundWall extends Sprite {
      public BackgroundWall(float startX, float startY, Vector2f scale, BufferedImage spriteSheet){
        super(startX, startY, scale);
        setCurrentSpriteFrame(spriteSheet); 
    }
    public BackgroundWall(float startX, float startY, Vector2f scale, int whichWall, BufferedImage spriteSheet){
        super(startX, startY, scale);
        setCurrentSpriteFrame(spriteSheet.getSubimage(whichWall *400, 0, 400, 467)); 
    }

    @Override
    public void process(float delta){
        //Not needed unless the background were to slide or something special like that
    }
}
