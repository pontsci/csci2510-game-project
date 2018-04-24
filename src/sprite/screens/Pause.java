package sprite.screens;

import sprite.world.*;
import sprite.Sprite;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

//If anything special were to happen to the background, such as sliding, it would occur here
public class Pause extends Sprite {
    public Pause(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
        setCurrentSpriteFrame(loadFile("src/resources/world/background/skyboxresized.png"));
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