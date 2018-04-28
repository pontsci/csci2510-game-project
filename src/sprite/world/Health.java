package sprite.world;

import sprite.Sprite;
import util.Vector2f;

public class Health extends Sprite{
    public Health(float startX, float startY, Vector2f scale, int healthType){
        super(startX, startY, scale);
        switch(healthType){
            case 1:
                setCurrentSpriteFrame(loadFile("src/resources/UI/UIElement_WH_131x203_Battery.png").getSubimage(0,0,131,203));
                break;
            case 2:
                setCurrentSpriteFrame(loadFile("src/resources/UI/UIElement_WH_131x203_Battery.png").getSubimage(131,0,131, 203));
                break;
            case 3:
                setCurrentSpriteFrame(loadFile("src/resources/UI/UIElement_WH_131x203_Battery.png").getSubimage(262,0,131, 203));
                break;
        }
    }

    @Override
    public void initializeHitboxes(){
        //Not needed
    }

    @Override
    public void process(float delta){
        //Not needed
    }
}
