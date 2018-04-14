package sprite.character.enemy;

import util.Animation;
import bounding.BoundingBox;
import bounding.BoundingCircle;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.world.Floor;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Rat extends CharacterSprite{
    private final static int GRAY_RAT = 0;
    private final static int WHITE_RAT = 1;
    private final static int WALK_ANIMATION = 0;
    private final static boolean GO_RIGHT = true;
    private final static boolean GO_LEFT = false;
    private float hTime = 0;//Horizontal Movement Timer
    private float vTime = 0;//Vertical Movement Timer
    private Animation animation = new Animation();
    private float walkRate = 2f;//Walk rate per second.
    private boolean facingDirection;//True means the rat faces right, false means footBox.
    private float jumpForce = 0;
    private Random rand = new Random();

    public Rat(float startX, float startY, Vector2f scale, int spriteImg, boolean direction, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms){
        super(startX, startY, scale, floor, walls, platforms);
        this.facingDirection = direction;
        switch(spriteImg){
            case GRAY_RAT:
                animation.addAnimation(loadFile("src/resources/character/enemy/ratwalk.png"), 4);
                setCurrentSpriteFrame(loadFile("src/resources/character/enemy/ratwalk.png").getSubimage(0, 0, 17, 8));
                break;
            case WHITE_RAT:
                animation.addAnimation(loadFile("src/resources/character/enemy/whiteratwalk.png"), 4);
                setCurrentSpriteFrame(loadFile("src/resources/character/enemy/whiteratwalk.png").getSubimage(0, 0, 17, 8));
                break;
        }
        //true faces right, false faces footBox
        initializeHitboxes();
    }

    //Initialize the rat's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-.07f, -.05f), new Vector2f(.10f, .02f), Color.BLUE));
        hitboxes.add(new BoundingCircle(.025f, .04f, -.02f, Color.RED));
        hitboxes.add(new BoundingBox(new Vector2f(-.03f, -.03f), new Vector2f(.09f, -.01f), Color.RED));
    }

    @Override
    //Process what's affecting/changing the rat - location/animation
    public void process(float delta){
        horizontalMovement(delta);
        verticalMovement(delta);
        animation.playAnimation(delta, WALK_ANIMATION, this);
    }

    //SIMPLE Random horizontal movement of the rat.
    private void horizontalMovement(float delta){
        hTime = hTime + delta;
        //Every second a random boolean is chosen to
        //determine the facingDirection the mouse will go
        if(hTime > 1){
            facingDirection = rand.nextBoolean();
            //True faces right
            if(facingDirection == GO_RIGHT){
                setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
            }
            //False faces footBox
            else{
                setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
            }
            hTime = hTime - 1;
        }

        //move the mouse in a facingDirection
        if(facingDirection == GO_RIGHT){
            setxTranslation(getxTranslation() + (walkRate * delta));
        }
        else{
            setxTranslation(getxTranslation() - (walkRate * delta));
        }
    }

    private void verticalMovement(float delta){
        vTime = vTime + delta;
        //Every 1.5 seconds a random boolean is chosen to
        //determine if the mouse will jump
        if(vTime > 1.5f){
            //if not jumping and the boolean is true, initiate a jump
            if(jumpForce == 0 && rand.nextBoolean()){
                setRotation(-.5f);
                jumpForce = 7f;
            }
            vTime = vTime - 1.5f;
        }
        //if jumping, adjust jump force by gravity
        if(jumpForce > 0){
            jumpForce = jumpForce + (getGravity() * delta);
            if(vTime > .50){
                setRotation(.5f);
            }
            //if the new jump force is less than 0, make it 0
            if(jumpForce < 0){
                jumpForce = 0;
            }
        }
        processGravity(delta);
    }

    //SIMPLE Random jump movement of the rat
    @Override
    protected void processGravity(float delta){
        setyTranslation(getyTranslation() + ((getGravity() + jumpForce) * delta));
    }
}