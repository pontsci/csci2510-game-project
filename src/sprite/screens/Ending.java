package sprite.screens;

import sprite.Sprite;
import util.Vector2f;

//If anything special were to happen to the background, such as sliding, it would occur here
public class Ending extends Sprite {
    public Ending(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
        setCurrentSpriteFrame(loadFile("src/resources/world/background/EndingScreen.png"));
        
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
