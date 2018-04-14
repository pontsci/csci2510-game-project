package sprite.character.enemy;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Animation;
import util.Intersect;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TriBot extends Enemy{
    private Animation animation = new Animation();
    private final int MOVE_ANIMATION = 0;
    private final int ATTACK_ANIMATION = 1;
    private int currentAnimation = 0;
    private float walkRate = 1.5f;
    private int currentDirection = 1;
    private int GOING_RIGHT = 0;
    private int GOING_LEFT = 1;
    private boolean footboxCollision = true;
    private boolean wallCollision = false;

    public TriBot(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player){
        super(startX, startY, scale, floor, walls, platforms, player);
        //get the animations for the tri bot - follow the main character
        BufferedImage spriteSheet = loadFile("src/resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png");
        //set the current frame
        //animation.addAnimation(loadFile("file path here.png"), #of frames);
        //Do this for each animation series.
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
        System.out.println(wallCollision);
        processAnimations(delta);
        processMovement(delta);
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

    private void processMovement(float delta){
        //If going footBox
        if(currentDirection == GOING_LEFT){
            //If foot box collides continue footBox,
            if(footboxCollision)
                walkLeft(delta);
            //else go right
            else{
                walkRight(delta);
                currentDirection = GOING_RIGHT;
            }
        }
        //if going right
        else if(currentDirection == GOING_RIGHT){
            //If foot box collides, continue right
            if(footboxCollision)
                walkRight(delta);
            //else go footBox
            else{
                walkLeft(delta);
                currentDirection = GOING_LEFT;
            }
        }
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){
        super.checkCollision(delta, viewport);

        if(footboxCollidesWithPlatform())
            footboxCollision = true;
        else
            footboxCollision = false;

        if(wallCollision)
            footboxCollision = false;
    }

    @Override
    protected void checkWallCollision(float delta, Matrix3x3f viewport){
        wallCollision = false;
        for(int i = 0; i < walls.size(); i++){
            while(checkSpriteCollision(delta, viewport, walls.get(i))){
                if(i == 0){
                    pushCharacter(delta, viewport, 'x', .001f);
                    wallCollision = true;
                }
                //Right wall is being hit, move mouse footBox;
                else if(i == 1){
                    pushCharacter(delta, viewport, 'x', -.001f);
                    wallCollision = true;
                }
            }
        }
    }

    private boolean footboxCollidesWithPlatform(){
        for(int i = 0; i < platforms.size(); i++){
            if(checkFootBoxCollision(platforms.get(i).getHitboxes())){
                return true;
            }
        }
        return false;
    }

    private boolean checkFootBoxCollision(ArrayList<BoundingShape> platformHitboxes){
        BoundingShape platformHitbox;

        //For every inner hitbox in the foreign Sprite
       for(int j = 1; j < platformHitboxes.size(); j++){
            platformHitbox = platformHitboxes.get(j);
            if(platformHitbox instanceof BoundingBox && Intersect.intersectAABB(footBox.getCurrentMin(), footBox.getCurrentMax(), ((BoundingBox)platformHitbox).getCurrentMin(), ((BoundingBox)platformHitbox).getCurrentMax())){
                return true;
            }
            else if(platformHitbox instanceof BoundingCircle && Intersect.intersectCircleAABB(((BoundingCircle)platformHitbox).getCurrentPoint(), ((BoundingCircle)platformHitbox).getCurrentRadius(), footBox.getCurrentMin(), footBox.getCurrentMax())){
                return true;
            }
        }
        return false;
    }

    private void walkRight(float delta){
        setxTranslation(getxTranslation() + (walkRate * delta));
        setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    private void walkLeft(float delta){
        setxTranslation(getxTranslation() - (walkRate * delta));
        setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
    }
}
