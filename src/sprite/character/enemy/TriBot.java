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
    private final int MOVE_ANIMATION = 0;
    private final int ATTACK_ANIMATION = 1;
    private int currentAnimation = 0;

    public TriBot(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player){
        super(startX, startY, scale, floor, walls, platforms, player);
        //get the animations for the tri bot - follow the main character
        BufferedImage spriteSheet = loadFile("src/resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png");
        setCurrentSpriteFrame(spriteSheet.getSubimage(0,0,237,356));
        animation.addAnimation(spriteSheet.getSubimage(0,0, 1659, 356), 7);
        animation.addAnimation(spriteSheet.getSubimage(0,356, 1422, 356), 6);
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-1.2f, -3), new Vector2f(1.1f, 1.9f), Color.BLUE));
        hitboxes.add(new BoundingCircle(.4f, -.1f, 1.4f, Color.RED));
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -1.7f), new Vector2f(.8f, 1.1f), Color.RED));
    }

    @Override
    public void process(float delta){
        super.process(delta);
        processAnimations(delta);
    }

    // Process which animation is playing, when an animation finishes, it returns true
    private void processAnimations(float delta){
        switch(currentAnimation){
            // move Animation
            case MOVE_ANIMATION:
                animation.playAnimation(delta, MOVE_ANIMATION, this);
                break;
            // idle Animation
            case ATTACK_ANIMATION:
                animation.playAnimation(delta, ATTACK_ANIMATION, this);
                break;
            // jump Animation
        }
    }
}
