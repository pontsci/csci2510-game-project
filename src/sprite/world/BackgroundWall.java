package sprite.world;

import java.awt.image.BufferedImage;
import sprite.Sprite;
import util.Vector2f;

//If anything special were to happen to the background, such as sliding, it would occur here
public class BackgroundWall extends Sprite {
    public BackgroundWall(float startX, float startY, Vector2f scale, int whichWall){
        super(startX, startY, scale);
        BufferedImage spriteSheet= (loadFile("src/resources/world/background/Building_WH_400x467_Wall.png"));
        setCurrentSpriteFrame(spriteSheet.getSubimage(whichWall *400, 0, 400, 467)); 
    }

    @Override
    public void initializeHitboxes(){
        //Not needed
    }

    @Override
    public void process(float delta){
        //Not needed unless the background were to slide or something special like that
    }
}
