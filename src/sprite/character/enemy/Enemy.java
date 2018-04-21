package sprite.character.enemy;

import bounding.BoundingBox;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Collision;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy extends CharacterSprite{
    protected BoundingBox footBox = new BoundingBox(new Vector2f(-1.1f, -2.4f), new Vector2f(-.9f, -1.8f), Color.GREEN);
    private float walkRate = 1.5f;
    private int currentDirection = 1;
    private int GOING_RIGHT = 0;
    private int GOING_LEFT = 1;
    protected int hp;
    private boolean footboxCollision = true;
    private boolean wallCollision = false;
    private MainCharacter player;


    /**
     * Creates a new enemy with references to objects it collides with and position data
     * @param startX starting x coord
     * @param startY starting y coord
     * @param scale starting scale
     * @param floor the floor
     * @param walls the walls
     * @param platforms the platforms
     * @param player the player
     */
    Enemy(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player){
        super(startX, startY, scale, floor, walls, platforms);
        this.player = player;
        this.hp = 10;
    }

    /**
     * Creates a new enemy with references to objects it collides with and position data
     * @param startX starting x coord
     * @param startY starting y coord
     * @param scale starting scale
     * @param floor the floor
     * @param walls the walls
     * @param platforms the platforms
     * @param player the player
     * @param hp starting hp
     */
    Enemy(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player, int hp){
        super(startX, startY, scale, floor, walls, platforms);
        this.player = player;
        this.hp = hp;
    }

    @Override
    public abstract void initializeHitboxes();

    /**
     * Overrides super method to include the footBox in update calculations
     * @param delta time
     * @param viewport screen
     */
    @Override
    public void update(float delta, Matrix3x3f viewport){
        super.update(delta,viewport);
        footBox.setxTranslation(getxTranslation());
        footBox.setyTranslation(getyTranslation());
        footBox.setScale(getScale());
        footBox.updateWorld(viewport);

        setViewport(viewport);
    }

    //For each hitbox, render the hitbox
    @Override
    public void renderHitboxes(Graphics g){
        super.renderHitboxes(g);
        footBox.render(g);
    }

    @Override
    public void process(float delta)
    {
        super.process(delta);
        processMovement(delta);
    }

    private void processMovement(float delta){
        //If going left
        if(currentDirection == GOING_LEFT){
            //If foot box collides continue left,
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
                //else go left
            else{
                walkLeft(delta);
                currentDirection = GOING_LEFT;
            }
        }
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){
        super.checkCollision(delta, viewport);
        footboxCollision = true;

        if(getyTranslation()>-2) {
            if (!footboxCollidesWithPlatform())
                footboxCollision = false;
        }
        if(wallCollision)
            footboxCollision = false;
    }

    @Override
    protected void checkWallCollision(float delta, Matrix3x3f viewport){
        wallCollision = false;
        for(int i = 0; i < walls.size(); i++){
            while(Collision.checkSpriteCollision(delta, viewport, this, walls.get(i))){
                if(i == 0){
                    pushCharacter(delta, viewport, 'x', ONE_PIXEL);
                    wallCollision = true;
                }
                //Right wall is being hit, move mouse footBox;
                else if(i == 1){
                    pushCharacter(delta, viewport, 'x', -ONE_PIXEL);
                    wallCollision = true;
                }
            }
        }
    }

    private boolean footboxCollidesWithPlatform(){

        for(int i = 0; i < platforms.size(); i++){
            if(Collision.checkCollision(footBox, platforms.get(i).getHitboxes())){
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

    /**
     * Takes an amount and subtracts hp by that amount
     * @param amount amount to decrease hp by
     * @return whether or not the enemy is dead
     */
    public boolean decreaseHP(int amount){
        hp = hp - amount;
        return hp == 0;
    }
}
