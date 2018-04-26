package sprite.character.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import bounding.BoundingBox;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.character.player.MainCharacter;
import status.VulnStatus;
import util.Collision;
import util.Matrix3x3f;
import util.Vector2f;

public abstract class Enemy extends CharacterSprite implements VulnStatus{
    protected BoundingBox footBox = new BoundingBox(new Vector2f(-1.1f, -2.4f), new Vector2f(-.9f, -1.8f), Color.GREEN);
    protected BoundingBox detectionBox = new BoundingBox(new Vector2f(-40f,-2f), new Vector2f(0f,4f), Color.CYAN);
    protected Vector2f bulletLine = new Vector2f(0,0);
    private float walkRate = 1.5f;
    protected float regenTimer;
    private final float REGEN_TIME = 10;
    private int currentDirection = 1;
    private int GOING_RIGHT = 0;
    private int GOING_LEFT = 1;
    private boolean footboxCollision = true;
    private boolean wallCollision = false;
    private boolean damaged = false;
    private boolean hit = false;
    private boolean playerDetected = false;
    private MainCharacter player;
    private Vector2f playerPos;
    private Vector2f bulletSpawn;


    /**
     * Creates a new enemy with references to objects it collides with and position data
     * @param startX starting x coord
     * @param startY starting y coord
     * @param scale starting scale
     * @param floors the floor
     * @param walls the walls
     * @param platforms the platforms
     * @param player the player
     */
    Enemy(float startX, float startY, Vector2f scale, ArrayList<Sprite> floors, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player){
        super(startX, startY, scale, floors, walls, platforms);
        initialize(player, 10);
    }

    /**
     * Creates a new enemy with references to objects it collides with and position data
     * @param startX starting x coord
     * @param startY starting y coord
     * @param scale starting scale
     * @param floors the floor
     * @param walls the walls
     * @param platforms the platforms
     * @param player the player
     * @param hp starting hp
     */
    Enemy(float startX, float startY, Vector2f scale, ArrayList<Sprite> floors, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player, int hp){
        super(startX, startY, scale, floors, walls, platforms);
        initialize(player, hp);
    }

    private void initialize(MainCharacter player, int hp){
        this.player = player;
        this.hp = hp;
        maxHp = hp;
        regenTimer = 0;
        gravity = -1;
        playerPos = player.getPos();
        bulletSpawn = new Vector2f(getPos().x, getPos().y);
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
        updateDetectionHitboxes(viewport);

        setViewport(viewport);
    }

    private void updateDetectionHitboxes(Matrix3x3f viewport)
    {
        footBox.setxTranslation(getxTranslation());
        footBox.setyTranslation(getyTranslation());
        footBox.setScale(getScale());
        footBox.updateWorld(viewport);
        detectionBox.setxTranslation(getxTranslation());
        detectionBox.setyTranslation(getyTranslation());
        detectionBox.setScale(getScale());
        detectionBox.updateWorld(viewport);
    }

    //For each hitbox, render the hitbox
    @Override
    public void renderHitboxes(Graphics g){
        super.renderHitboxes(g);
        footBox.render(g);
        detectionBox.render(g);
        if(playerDetected){
            Vector2f pPos = playerPos;
            Vector2f thisPos = bulletSpawn;

            float playerY = pPos.y;     //player y
            float thisY = thisPos.y;    //enemy Y
            Vector2f min;
            Vector2f max;
            g.setColor(Color.GREEN);

            //check for which point is the starting point
            if(pPos.x < thisPos.x){
                float maxX = thisPos.x;
                float minX = pPos.x;
                min = new Vector2f(minX, playerY);
                max = new Vector2f(maxX, thisY);
            }else{
                float maxX = pPos.x;
                float minX = thisPos.x;
                min = new Vector2f(minX, thisY);
                max = new Vector2f(maxX, playerY);
            }

            //set to screen
            Vector2f minScreen = viewport.mul(min);
            Vector2f maxScreen = viewport.mul(max);

            //draw
            g.drawLine((int)minScreen.x, (int)minScreen.y, (int)maxScreen.x, (int)maxScreen.y);
        }
    }

    @Override
    public void process(float delta)
    {
        super.process(delta);
        if(!playerDetected){
            processMovement(delta);
        }else{
            processShooting(delta);
        }
        processRegeneration(delta);
    }

    private void processShooting(float delta){
        //shoot stuff
    }

    private void processRegeneration(float delta)
    {
        //enemy is damaged under max hp
        damaged = hp < maxHp;
        if(damaged){
            regenTimer += delta;

            //if the enemy was recently hit by a bullet, reset the timer
            if(hit){
                regenTimer = delta;
                setHit(false);
            }

            //if the timer goes over specified amount, set back to full hp
            if(regenTimer > REGEN_TIME){
                regenTimer = 0;
                hp = maxHp;
                damaged = false;
            }
        }
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

        if(currentDirection == GOING_LEFT){
            bulletSpawn.x = getPos().x + .2f;
        }else{
            bulletSpawn.x = getPos().x - .2f;
        }
        bulletSpawn.y = getPos().y - .08f;
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

        playerDetected = Collision.checkCollision(detectionBox, player.getHitboxes().get(0));
        if(!playerDetected){
            detectionBox.setObjectColor(Color.CYAN);
        }else{
            playerPos = player.getPos();
            detectionBox.setObjectColor(Color.RED);
        }
    }

    @Override
    protected void checkWallCollision(float delta, Matrix3x3f viewport){
        wallCollision = false;
        for(int i = 0; i < walls.size(); i++){
            while(Collision.checkSpriteCollision(this, walls.get(i))){
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
        for(Sprite platform : platforms){
            if(Collision.checkCollision(footBox, platform.getHitboxes())){
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

    public void setHit(boolean isHit){
        hit = isHit;
    }
}
