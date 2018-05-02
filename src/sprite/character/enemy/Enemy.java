package sprite.character.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import bounding.BoundingBox;
import managers.BulletManager;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.character.player.MainCharacter;
import sprite.world.StatusIcon;
import util.Collision;
import util.Matrix3x3f;
import util.Vector2f;

public abstract class Enemy extends CharacterSprite{
    protected BoundingBox footBox = new BoundingBox(new Vector2f(-1.1f, -2.4f), new Vector2f(-.9f, -1.8f), Color.GREEN);
    protected BoundingBox detectionBox = new BoundingBox(new Vector2f(-40f,0f), new Vector2f(0f,2f), Color.CYAN);
    protected Vector2f bulletLine = new Vector2f(0,0);
    private float walkRate = 1.5f;
    private final float MAX_DISPLAY_TIME = .15f;
    private final float REGEN_TIME = 10;
    private int currentDirection = 1;
    private int GOING_RIGHT = 0;
    private int GOING_LEFT = 1;
    private boolean footboxCollision = true;
    private boolean wallCollision = false;
    private boolean damaged = false;
    private boolean playerInDetectionBox = false;
    private boolean vision = false;
    private boolean shotValid = false;
    private boolean platformShotValid = false;
    private boolean wallShotValid = false;
    protected boolean moving;
    protected boolean playShootAnimation = false;
    private MainCharacter player;
    private Vector2f playerPos;
    private Vector2f bulletSpawn;
    private float maxVisionTime;
    protected float regenTimer;
    protected float visionTimer = 0;
    private int dmgTicks = 0;
    private float tickTimer = 0;
    private float tickInterval = .5f;
    private float displayHitTimer;
    private boolean tasered = false;
    private boolean displayHitEffect;
    private StatusIcon taserEffect;
    private StatusIcon hitEffect;
    
    /**
     * Creates a new enemy with references to objects it collides with and position data
     * @param startX starting x coord
     * @param startY starting y coord
     * @param scale starting scale
     * @param screenWalls the walls
     * @param platforms the platforms
     * @param player the player
     */
    Enemy(float startX, float startY, Vector2f scale, ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, MainCharacter player, ArrayList<Sprite> walls, BulletManager bm, StatusIcon lightning, boolean direction, StatusIcon hitEffect){
        super(startX, startY, scale,  screenWalls, platforms, walls, bm);
        initialize(player, 5);
        taserEffect = lightning;
        this.hitEffect = hitEffect;
        currentDirection = direction ? 0 : 1;
    }

    /**
     * Creates a new enemy with references to objects it collides with and position data
     * @param startX starting x coord
     * @param startY starting y coord
     * @param scale starting scale
     * @param screenWalls the walls
     * @param platforms the platforms
     * @param player the player
     * @param hp starting hp
     */
    Enemy(float startX, float startY, Vector2f scale,  ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, MainCharacter player, ArrayList<Sprite> walls, BulletManager bm, int hp, StatusIcon lightning, StatusIcon hitEffect){
        super(startX, startY, scale,  screenWalls, platforms, walls, bm);
        initialize(player, hp);
        taserEffect = lightning;
        this.hitEffect = hitEffect;
    }

    private void initialize(MainCharacter player, int hp){
        this.player = player;
        this.hp = hp;
        maxHp = hp;
        regenTimer = 0;
        gravity = -1;
        playerPos = player.getPos();
        bulletSpawn = new Vector2f(getPos().x, getPos().y);

        displayHitEffect = false;
        displayHitTimer = 0;

        Random rand = new Random();
        maxVisionTime = (rand.nextFloat()*1)+1;
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
        taserEffect.update(delta, viewport);
        hitEffect.update(delta, viewport);
        setViewport(viewport);
    }

    /**
     * updates the detection hitboxes
     * @param viewport the viewport
     */
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

    /**
     * Renders lightning effect when tasered
     * @param g graphics
     */
    @Override
    public void render(Graphics g) {
    	super.render(g);
    	if(tasered)
    		renderDoT(g);
    	if(displayHitEffect){
    	    renderHitEffect(g);
        }
    }

    /**
     * For each hitbox, render the hitbox
     * @param g graphics
     */
    @Override
    public void renderHitboxes(Graphics g){
        super.renderHitboxes(g);
        footBox.render(g);
        detectionBox.render(g);

        Vector2f origin;
        Vector2f direction;
        Vector2f originScreen;
        Vector2f directionScreen;

        //if we have vision, draw our shot line
        if(vision){
            origin = bulletSpawn;
            direction  = playerPos;
            originScreen = viewport.mul(origin);
            directionScreen = viewport.mul(direction);

            //if shot is valid, draw a green line, if not, draw a red line
            if(!shotValid){
                g.setColor(Color.RED);
            }else{
                g.setColor(Color.GREEN);
            }

            //draw
            g.drawLine((int)originScreen.x, (int)originScreen.y, (int)directionScreen.x, (int)directionScreen.y);
        }
    }

    /**
     * process vision, movement, and shooting.
     * @param delta time
     */
    @Override
    public void process(float delta)
    {
        super.process(delta);

        if(hit){
            displayHitEffect = true;
            displayHitTimer = 0;
        }
        if(displayHitEffect){
            displayHitTimer += delta;
            hitEffect.setxTranslation(getPos().x);
            hitEffect.setyTranslation(getPos().y);
        }
        if(displayHitTimer > MAX_DISPLAY_TIME){
            displayHitEffect = false;
            displayHitTimer = 0;
        }
        //where to spawn the bullets when shot
        bulletSpawn.x = currentDirection == GOING_LEFT ? getPos().x + .2f : getPos().x - .2f;
        bulletSpawn.y = getPos().y - .08f;

        //if we don't have vision of the player, move
        if(!vision){
            moving = true;
            processMovement(delta);
        }
        //if we have vision, shoot the player
        else{
            moving = false;
            processShooting(delta);
        }
        //regenerate HP after 10 seconds of not being hit
        processRegeneration(delta);
        processEffects(delta);
    }

    /**
     * process the shooting, detects whether shots are valid, and whether the enemy should be playing shooting anaimations
     * @param delta time
     */
    private void processShooting(float delta){
        //detect if the player walks behind the enemy, turning the enemy around
        if(shotValid){
            if(playerPos.x > getPos().x){
                currentDirection = GOING_RIGHT;
                setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
            }else{
                currentDirection = GOING_LEFT;
                setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
            }
        }

        //if our shot is valid
        if(shotValid){
            //can we shoot
            if(canShoot){
                //we just shot, so we no longer can shoot
                canShoot = false;
                bulletTimer = 0;
                //we need to play our shooting animation
                playShootAnimation = true;
                //add a bullet
            }
        }
    }

    /**
     * Enemies regenerate health after 10 seconds of not being hit
     * @param delta time
     */
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

    /**
     * process whether the enemy goes left or right
     * @param delta time
     */
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

    /**
     * Enemies do not fall of platforms, turn around when reaching an end of platform
     * @param delta time
     * @param viewport the viewport
     */
    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){
        super.checkCollision(delta, viewport);
        updatePlayerPos(delta);
        //check if the footbox collides with any ground
        footboxCollision = footboxCollidesWithGround();
        if(wallCollision)
            footboxCollision = false;

        //is player in the detection box
        playerInDetectionBox = Collision.checkCollision(detectionBox, player.getHitboxes().get(0));

        for(Sprite p : platforms){
            if(Collision.intersectSegment(bulletSpawn, playerPos, p, true)){
                platformShotValid = false;
                break;
            }else{
                platformShotValid = true;
            }
        }

        //if our shot line has not collided, shot is considered valid
        //for each wall, does our shot line collide

        for(Sprite w : walls){
            if(Collision.intersectSegment(bulletSpawn, playerPos, w, false)){
                wallShotValid = false;
                break;
            }else{
                wallShotValid = true;
            }
        }

        System.out.println("Shot valid = " + shotValid);
        if(wallShotValid && platformShotValid){
            shotValid = true;
        }else{
            shotValid = false;
        }

        if(playerInDetectionBox){
            //if our shot line has not collided, shot is considered valid
            //for each platform, does our shot line collide
            if(shotValid){
                detectionBox.setObjectColor(Color.RED);
                visionTimer = delta;
                vision = true;
            }else{
                visionTimer +=delta;
                if(visionTimer > maxVisionTime){
                    vision = false;
                }
            }
        }else{
            detectionBox.setObjectColor(Color.CYAN);
            
            if(!shotValid){
                visionTimer+=delta;
            }

            //if we go over maxVisionTime, we no longer have vision
            if(visionTimer > maxVisionTime){
                vision = false;
            }
        }
    }

    private void updatePlayerPos(float delta)
    {
            playerPos = player.getPos();
    }

    /**
     * check if the enemy has collided with a wall, push them out
     * @param delta time
     * @param viewport the viewport
     */
    @Override
    protected void checkScreenWallCollision(float delta, Matrix3x3f viewport){
        wallCollision = false;
        for(int i = 0; i < screenWalls.size(); i++){
            while(Collision.checkSpriteCollision(this, screenWalls.get(i))){
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

    /**
     * check if the enemy has collided with a wall, push them out
     * @param delta time
     * @param viewport the viewport
     */
    @Override
    protected void checkWallCollision(float delta, Matrix3x3f viewport){
        float xStartState = getxTranslation();
        float yStartState = getyTranslation();
        int magnitude = 1;
        if(!(walls == null)){
            for(Sprite wall : walls){
                while(Collision.checkSpriteCollision(this, wall)){
                    pushCharacter(delta, viewport, 'y', ONE_PIXEL * magnitude);
                    if(Collision.checkSpriteCollision(this, wall)){
                        setyTranslation(yStartState);
                    }
                    else{
                        break;
                    }
                    pushCharacter(delta, viewport, 'x', ONE_PIXEL * magnitude);
                    if(Collision.checkSpriteCollision(this, wall)){
                        setxTranslation(xStartState);
                    }
                    else{
                        wallCollision = true;
                        break;
                    }
                    pushCharacter(delta, viewport, 'x', -ONE_PIXEL * magnitude);
                    if(Collision.checkSpriteCollision(this, wall)){
                        setxTranslation(xStartState);
                    }
                    else{
                        wallCollision = true;
                        break;
                    }
                    pushCharacter(delta, viewport, 'y', -ONE_PIXEL * magnitude);
                    if(Collision.checkSpriteCollision(this, wall)){
                        setyTranslation(yStartState);
                    }
                    else{
                        break;
                    }
                    magnitude++;
                }
            }
        }
    }

    /**
     * checks if the footBox collides with the ground
     * @return whether or not it collided
     */
    private boolean footboxCollidesWithGround(){
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
     * Decrements enemy health when tasered
     * @param delta time
     */
    public void processEffects(float delta) {
		// DoT check
		if(tasered) {
			if(dmgTicks > 0) {
				tickTimer += delta;
				if(tickTimer >= tickInterval) {
					tickTimer -= tickInterval;
					hp--;
					dmgTicks--;
				}
			}
			else {
				tickTimer = 0;
				tasered = false;
			}
		}
		
	}

    /**
     * Renders the taser effect
     * @param g graphics
     */
    public void renderDoT(Graphics g) {
    	Vector2f pos = getPos(); 
    	
    	taserEffect.setxTranslation(pos.x);
    	taserEffect.setyTranslation(pos.y);
    	
    	taserEffect.render(g);
    }

    private void renderHitEffect(Graphics g){
        hitEffect.render(g);
    }

    /**
     * Starts damage over time effect
     */
	public void activateDoT() {
		dmgTicks = 4;
		tickTimer = 0;
		tasered = true;
	}

}
