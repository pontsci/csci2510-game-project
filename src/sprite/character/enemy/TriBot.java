package sprite.character.enemy;

import sprite.Sprite;
import sprite.world.Floor;
import util.Animation;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TriBot extends Enemy{
    private Animation animation = new Animation();

    public TriBot(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls){
        super(startX, startY, scale, floor, walls);
        //get the animations for the tri bot - follow the main character
        //set the current frame
        //animation.addAnimation(loadFile("file path here.png"), #of frames);
        //Do this for each animation series.
    }

    @Override
    public void initializeHitboxes(){

    }

    @Override
    public void process(float delta){

    }
}
