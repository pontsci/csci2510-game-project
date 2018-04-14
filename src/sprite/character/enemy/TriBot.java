package sprite.character.enemy;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Animation;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TriBot extends Enemy{
    private Animation animation = new Animation();

    public TriBot(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player){
        super(startX, startY, scale, floor, walls, platforms);
        //get the animations for the tri bot - follow the main character
        //set the current frame
        //animation.addAnimation(loadFile("file path here.png"), #of frames);
        //Do this for each animation series.
        setCurrentSpriteFrame(loadFile("src/resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png").
                getSubimage(0,0,237,356));
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-1.2f, -3), new Vector2f(1.1f, 1.9f), Color.BLUE));
        hitboxes.add(new BoundingCircle(.4f, -.1f, 1.4f, Color.RED));
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -1.9f), new Vector2f(.8f, 1.1f), Color.RED));
    }

    @Override
    public void process(float delta){
        super.process(delta);
    }
}
