package sprite.character.player;

import animation.Animation;
import bounding.BoundingBox;
import bounding.BoundingCircle;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.world.Floor;
import util.Intersect;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainCharacter extends CharacterSprite{
    private final static int MOVE_ANIMATION = 0;
    private final static int IDLE_ANIMATION = 1;
    private final static int JUMP_ANIMATION = 2;
    private ArrayList<Sprite> rats;
    private Animation animation = new Animation();
    private int currentAnimation = 1;
    private float walkRate = 2.5f;//Walk rate per second. (The world is 16 by 9)

    public MainCharacter(float startX, float startY, Vector2f scale, ArrayList<BufferedImage> spriteAnimations, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> rats){
        super(startX, startY, scale, spriteAnimations.get(1).getSubimage(0, 0, 237, 356), floor, walls);
        animation.addAnimation(spriteAnimations.get(0), 6);
        animation.addAnimation(spriteAnimations.get(1), 5);
        animation.addAnimation(spriteAnimations.get(2), 7);
        this.rats = rats;
        initializeHitboxes();
    }

    //Initialize the main character's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        //Create the hitboxes
        hitboxes.add(new BoundingBox(new Vector2f(-1.25f, -2), new Vector2f(1.1f, 1.9f), Color.BLUE));
        hitboxes.add(new BoundingCircle(.35f, -.12f, 1.4f, Color.RED));
        hitboxes.add(new BoundingBox(new Vector2f(-1.1f, -1.9f), new Vector2f(1.1f, 1.15f), Color.RED));
    }

    //Process the constant gravity applied to the main character
    public void process(float delta){
        setyTranslation(getyTranslation() + (getGravity() * delta));
        processAnimations(delta);
    }

    //Process which animation is playing
    private void processAnimations(float delta){
        switch(currentAnimation){
            //move Animation
            case MOVE_ANIMATION:
                animation.playAnimation(delta, MOVE_ANIMATION, this);
                break;
            //idle Animation
            case IDLE_ANIMATION:
                animation.playAnimation(delta, IDLE_ANIMATION, this);
                break;
            //jump Animation
            case JUMP_ANIMATION:
                if(animation.playAnimation(delta, JUMP_ANIMATION, this))
                    currentAnimation = IDLE_ANIMATION;
                break;
        }
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){
        super.checkCollision(delta, viewport);
        //checkMouseCollision(delta, viewport);
        for(int i = 0; i < rats.size(); i++){
            if(checkCollision(delta,viewport,rats.get(i))){
                rats.remove(i);
                i--;
            }
        }
    }

    public boolean checkCollision(float delta, Matrix3x3f viewport, Sprite sprite){
        Vector2f spriteMin = ((BoundingBox)sprite.getHitboxes().get(0)).getCurrentMin();
        Vector2f spriteMax = ((BoundingBox)sprite.getHitboxes().get(0)).getCurrentMax();
        Vector2f characterMin = ((BoundingBox)getHitboxes().get(0)).getCurrentMin();
        Vector2f characterMax = ((BoundingBox)getHitboxes().get(0)).getCurrentMax();

        if(Intersect.intersectAABB(characterMin, characterMax, spriteMin, spriteMax)){
            if(checkInnerCollision(sprite.getHitboxes())){
                System.out.println("Hit! " + sprite.toString());
                return true;
            }
        }
        return false;
    }

    public void checkMouseCollision(float delta, Matrix3x3f viewport){
        //Get the outer hitboxes' min and max to check collision
        Vector2f ratMin;
        Vector2f ratMax;
        Vector2f characterMin = ((BoundingBox)getHitboxes().get(0)).getCurrentMin();
        Vector2f characterMax = ((BoundingBox)getHitboxes().get(0)).getCurrentMax();

        for(int i = 0; i < rats.size(); i++){
            ratMin = ((BoundingBox)rats.get(i).getHitboxes().get(0)).getCurrentMin();
            ratMax = ((BoundingBox)rats.get(i).getHitboxes().get(0)).getCurrentMax();
            if(Intersect.intersectAABB(characterMin, characterMax, ratMin, ratMax)){
                if(checkInnerCollision(rats.get(i).getHitboxes())){
                    rats.remove(i);
                    i--;
                }
            }
        }
    }

    //play left move animation
    public void walkLeft(float delta){
        if(currentAnimation != JUMP_ANIMATION && currentAnimation != MOVE_ANIMATION){
            currentAnimation = MOVE_ANIMATION;
        }
        setxTranslation(getxTranslation() - (walkRate * delta));
        setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    //play right move animation
    public void walkRight(float delta){
        if(currentAnimation != JUMP_ANIMATION && currentAnimation != MOVE_ANIMATION)
            currentAnimation = MOVE_ANIMATION;
        setxTranslation(getxTranslation() + (walkRate * delta));
        setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    //play jump animation
    public void jump(){
        if(currentAnimation != JUMP_ANIMATION)
            currentAnimation = JUMP_ANIMATION;
    }

    //Play idle animation as long as the main character is not jumping or already idle.
    public void idle(){
        if(currentAnimation != JUMP_ANIMATION && currentAnimation != IDLE_ANIMATION)
            currentAnimation = IDLE_ANIMATION;
    }
}