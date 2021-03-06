package sprite.character.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import managers.BulletManager;
import sound.SteamSound;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.world.Door;
import sprite.world.Floor;
import sprite.world.PlayerFloor;
import sprite.world.PowerUp;
import status.VulnStatus;
import util.Animation;
import util.Collision;
import util.Matrix3x3f;
import util.Vector2f;

public class MainCharacter extends CharacterSprite implements VulnStatus{
    private final int MOVE_ANIMATION = 0;
    private final int IDLE_ANIMATION = 1;
    private final int JUMP_ANIMATION = 2;
    private final int FALL_ANIMATION = 3;
    private final int LAND_ANIMATION = 4;

    private boolean onTheFloor = true;
    private boolean onAPlatform = false;
    private boolean ignorePlatforms = false;
    private boolean changingLevel = false;
    //If falling, check platforms after 2 seconds (After the player has completely gone through a platform)
    private float platformTimer = 2;
    private float velocityY = 0;
    private ArrayList<Sprite> powerups;
    private ArrayList<Sprite> doors;
    private ArrayList<Sprite> levers;
    private boolean canCollideInteractable = false;
    private Animation animation = new Animation();
    private int currentAnimation = 1;
    private float walkRate = 2.5f;// Walk rate per second. (The world is 16 by 9)
    //private int hp = 3;
    private int healTicks = 0;//Tick values for hp and dmg
    private int dmgTicks = 0;
    private boolean end = false;

    public MainCharacter(float startX, float startY, Vector2f scale, ArrayList<Sprite> screenWalls, ArrayList<Sprite> powerups, ArrayList<Sprite> platforms, ArrayList<Sprite> walls, BulletManager bm, ArrayList<Sprite> doors, BufferedImage idleAnimation, BufferedImage jumpAnimation, BufferedImage moveAnimation, ArrayList<Sprite> levers){
        super(startX, startY, scale, screenWalls, platforms, walls, bm);

        //Always set the frame, even if it runs without setting the frame, a null error can occur on animated sprites when you try to create a new one.
        setCurrentSpriteFrame(idleAnimation.getSubimage(0, 0, 237, 356));
        animation.addAnimation(moveAnimation, 6);
        animation.addAnimation(idleAnimation, 5);
        animation.addAnimation(jumpAnimation.getSubimage(0, 0, 474, 356), 2);
        //falling part of the jump animation
        animation.addAnimation(jumpAnimation.getSubimage(474, 0, 711, 356), 3);
        //landing part of the jump animation
        animation.addAnimation(jumpAnimation.getSubimage(1185, 0, 474, 356), 2);
        this.powerups = powerups;
        this.doors = doors;
        this.hp = 3;
        this.levers = levers;
        bulletWaitTime = .25f;
        initializeHitboxes();
    }

    // Initialize the main character's hitboxes, the first box is the outer hitbox
    public void initializeHitboxes(){
        // Create the hitboxes
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -2), new Vector2f(.7f, 1.9f), Color.BLUE));
        hitboxes.add(new BoundingCircle(.4f, -.1f, 1.4f, Color.RED));
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, -1.9f), new Vector2f(.7f, 1.1f), Color.RED));
    }

    // Process functions of the main character
    public void process(float delta){
        super.process(delta);
        processAnimations(delta);
        
        processEffects(delta);
        processAdjustPlatformTimer(delta);
    }

    @Override
    protected void processGravity(float delta){
        //Set animation to falling if the character is falling.
        if(velocityY < -1f){
            currentAnimation = FALL_ANIMATION;
        }

        //if not on the floor or on a platform, change the character's velocity based on gravity.
        if(!onTheFloor && !onAPlatform){
            if(velocityY > TERMINAL_VELOCITY){
                velocityY += getGravity() * delta;
            }
        }
        else{
            velocityY = 0 + (getGravity() * delta);
        }
        setyTranslation(getyTranslation() + velocityY * delta);
    }

    // Process which animation is playing, when an animation finishes, it returns true
    private void processAnimations(float delta){
        switch(currentAnimation){
            // move Animation
            case MOVE_ANIMATION:
                animation.playAnimation(delta, MOVE_ANIMATION, this);
                break;
            // idle Animation
            case IDLE_ANIMATION:
                animation.playAnimation(delta, IDLE_ANIMATION, this);
                break;
            // jump Animation
            case JUMP_ANIMATION:
                if(animation.playAnimation(delta, JUMP_ANIMATION, this)){
                    currentAnimation = FALL_ANIMATION;
                }
                if(onTheFloor || onAPlatform){
                    currentAnimation = LAND_ANIMATION;
                }
                break;
            case FALL_ANIMATION:
                animation.playAnimation(delta, FALL_ANIMATION, this);
                if(onTheFloor || onAPlatform){
                    currentAnimation = LAND_ANIMATION;
                }
                break;
            case LAND_ANIMATION:
                if(animation.playAnimation(delta, LAND_ANIMATION, this)){
                    currentAnimation = IDLE_ANIMATION;
                }
                break;
        }
    }

    //When a player presses 'S' when they are on a platform, it takes .7 seconds before checking platform collision.
    private void processAdjustPlatformTimer(float delta){
        if(platformTimer < .70f){
            platformTimer = platformTimer + delta;
        }
    }

    public void update(float delta, Matrix3x3f viewport) {
    	super.update(delta, viewport);
    	conditions.updateObjects(delta, viewport);
    }
    
    // Renders character and conditions
    public void render(Graphics g){
        super.render(g);
        conditions.render(g);
    }

    // checks rat and powerups collision
    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){
        super.checkCollision(delta, viewport);
        checkPowerupCollision(delta, viewport);
        changingLevel = false;
        if(canCollideInteractable){
            checkDoorCollision(delta, viewport);
            end = checkLeverCollision(delta, viewport);
        }
        canCollideInteractable = false;
    }

    private void checkDoorCollision(float delta, Matrix3x3f viewport){
        for(Sprite door : doors){
            if(Collision.checkSpriteCollision(this, door)){
                changingLevel = true;
            }
        }
    }

    private boolean checkLeverCollision(float delta, Matrix3x3f viewport){
        for(Sprite lever : levers){
            if(Collision.checkSpriteCollision(this, lever)){
                return true;
            }
        }
        return false;
    }

    private void checkPowerupCollision(float delta, Matrix3x3f viewport){
        //Checks powerups collision if no statuses are active, activate status effect with the same name in VulnStatus's conditions.
    	if(!conditions.anyStatusActive()) {
    		for(int i = 0; i < powerups.size(); i++){
            	if(Collision.checkSpriteCollision(this, powerups.get(i))){
                	conditions.activateStatus(((PowerUp)(powerups.get(i))).getEffect().name);
                	powerups.remove(i);
                	i--;
            	}
    		}
        }
    }

    @Override
    protected void checkPlatformCollision(float delta, Matrix3x3f viewport){
        //Negative velocity means the character is now falling, so check for platforms
        if(velocityY < 0 && !ignorePlatforms){
            float xStartState = getxTranslation();
            float yStartState = getyTranslation();
            int magnitude = 1;
            for(Sprite platform : platforms) {
                if (!(platform instanceof Floor) || !(platform instanceof PlayerFloor)) {
                    while (Collision.checkOuterCollision(this, platform)) {
                        pushCharacter(delta, viewport, 'y', ONE_PIXEL * magnitude);
                        if (Collision.checkOuterCollision(this, platform)) {
                            setyTranslation(yStartState);
                        } else {
                            onAPlatform = true;
                            break;
                        }
                        pushCharacter(delta, viewport, 'x', ONE_PIXEL * magnitude);
                        if (Collision.checkOuterCollision(this, platform)) {
                            setxTranslation(xStartState);
                        } else
                            break;
                        pushCharacter(delta, viewport, 'x', -ONE_PIXEL * magnitude);
                        if (Collision.checkOuterCollision(this, platform)) {
                            setxTranslation(xStartState);
                        } else
                            break;
                        magnitude++;
                    }
                }
            }
        }
    }

    @Override
    protected void checkWallCollision(float delta, Matrix3x3f viewport){
        float xStartState = getxTranslation();
        float yStartState = getyTranslation();
        int magnitude = 1;
        if(!(walls == null)){
            for(Sprite wall : walls){
                while(Collision.checkOuterCollision(this, wall)){
                    //Push character and check if it's colliding.
                    pushCharacter(delta, viewport, 'y', ONE_PIXEL * magnitude);
                    if(Collision.checkOuterCollision(this, wall)){
                        setyTranslation(yStartState);
                    }
                    else{
                        onAPlatform = true;
                        break;
                    }
                    pushCharacter(delta, viewport, 'x', ONE_PIXEL * magnitude);
                    if(Collision.checkOuterCollision(this, wall)){
                        setxTranslation(xStartState);
                    }
                    else{
                        break;
                    }
                    pushCharacter(delta, viewport, 'x', -ONE_PIXEL * magnitude);
                    if(Collision.checkOuterCollision(this, wall)){
                        setxTranslation(xStartState);
                    }
                    else{
                        break;}
                    pushCharacter(delta, viewport, 'y', -ONE_PIXEL * magnitude);
                    if(Collision.checkOuterCollision(this, wall)){
                        setyTranslation(yStartState);
                    }
                    else{
                        velocityY = 0;
                        break;
                    }
                    magnitude++;
                }
            }
        }
    }

    @Override
    //Push the character every direction, increases how much it pushes each loop until the character is no longer colliding.
    protected void checkFloorCollision(float delta, Matrix3x3f viewport){
        onTheFloor = false;
        float xStartState = getxTranslation();
        float yStartState = getyTranslation();
        int magnitude = 1;
        for(Sprite floor : platforms) {
            if (floor instanceof Floor || floor instanceof PlayerFloor) {
                while (Collision.checkSpriteCollision(this, floor)) {
                    pushCharacter(delta, viewport, 'y', ONE_PIXEL * magnitude);
                    if (Collision.checkSpriteCollision(this, floor)) {
                        setyTranslation(yStartState);
                        } else {
                            onTheFloor = true;
                            return;
                        }
                        pushCharacter(delta, viewport, 'x', ONE_PIXEL * magnitude);
                    if (Collision.checkSpriteCollision(this, floor)) {
                            setxTranslation(xStartState);
                        } else
                            return;
                        pushCharacter(delta, viewport, 'x', -ONE_PIXEL * magnitude);
                        if (Collision.checkSpriteCollision(this, floor)) {
                            setxTranslation(xStartState);
                        } else
                            return;
                        magnitude++;
                    }
                }
            }

        onAPlatform = false;
    }

    // play footBox move animation
    public void walkLeft(float delta){
        if(currentAnimation != MOVE_ANIMATION && currentAnimation != JUMP_ANIMATION  && currentAnimation != FALL_ANIMATION && currentAnimation != LAND_ANIMATION){
            currentAnimation = MOVE_ANIMATION;
        }
        setxTranslation(getxTranslation() - (walkRate * delta));
        setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    // play right move animation
    public void walkRight(float delta){
        if(currentAnimation != MOVE_ANIMATION && currentAnimation != JUMP_ANIMATION && currentAnimation != FALL_ANIMATION && currentAnimation != LAND_ANIMATION){
            currentAnimation = MOVE_ANIMATION;
        }
        setxTranslation(getxTranslation() + (walkRate * delta));
        setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));

    }

    // play jump animation
    public void jump(){
        if(currentAnimation != JUMP_ANIMATION && currentAnimation != FALL_ANIMATION){
            currentAnimation = JUMP_ANIMATION;
            velocityY = 7.5f;
            onTheFloor = false;
            onAPlatform = false;
        }
    }

    // Play idle animation
    public void idle(){
        if(currentAnimation != JUMP_ANIMATION && currentAnimation != IDLE_ANIMATION && currentAnimation != FALL_ANIMATION && currentAnimation != LAND_ANIMATION){
            currentAnimation = IDLE_ANIMATION;
        }
    }

    // Shoot a bullet
    public void shoot(){
        if(canShoot){
            bm.addMainCharacterBullet(getxTranslation(), getyTranslation(), getScale().x > 0);
            SteamSound.playerWeaponFire();
            canShoot = false;
            bulletTimer = 0;
        }
    }

    //Allows checking for door collision by setting 'canCollisdeWithDoor' to true.
    public void canGoThroughDoor(){
        if(!doors.isEmpty()){
            if(((Door)doors.get(0)).isOpen()){
                canCollideInteractable = true;
            }
        }
        if(!levers.isEmpty()){
            canCollideInteractable = true;
        }
    }

    public void ignorePlatformCollision(){
        //If on a platform, ignore Platform collision and reset the timer.
        if(onAPlatform){
            ignorePlatforms = true;
            platformTimer = 0;
        }
        //If not on a platform, and the player is holding s, check for platforms after .75 seconds.
        else
            allowPlatformCollision();
    }

    public void allowPlatformCollision(){
        //Allow platform collision after .70 seconds
        if(platformTimer >= .70f || onAPlatform)
            ignorePlatforms = false;
    }

    // Process status effects
    public void processEffects(float delta){
        //Heal check
        if(conditions.getStatus(0).active){

            //At 1.5, 1, and 0.5, if the player won't be healed above 3, heal the player.
            if((conditions.getTimer(0) > 1.0 && healTicks < 1)
                    || (conditions.getTimer(0) > 0.5 && conditions.getTimer(0) < 1.0 && healTicks < 2)
                    || (conditions.getTimer(0) > 0.0 && conditions.getTimer(0) < 0.5
                    && healTicks < 3)){
                if(hp < 3){
                    healthEffect();
                    healTicks++;
                }
            }

        }
        else{
            healTicks = 0;
        }

        //DoT check
        if(conditions.getStatus(5).active){
            //At 3,2,and 1 on the timer, if the player is above 1 health, damage the player
            if((conditions.getTimer(5) > 2.0 && dmgTicks < 1)
                    || (conditions.getTimer(5) > 1.0 && conditions.getTimer(5) < 2.0 && dmgTicks < 2)
                    || (conditions.getTimer(5) > 0.0 && conditions.getTimer(5) < 1.0
                    && dmgTicks < 3)){
                if(hp > 1){
                    dmgOverTime();
                    dmgTicks++;
                }
            }
        }
        else{
            dmgTicks = 0;
        }
        
        if(isFireRateActive()) 
        	bulletWaitTime = .10f;
        else
        	bulletWaitTime = .25f;
    }

    // Health Item effect
    public void healthEffect(){
        //System.out.println("HEAL!!!");
        hp++;
    }

    //DoT Effect
    public void dmgOverTime(){
        //System.out.println("DoT!!!");
        hp--;
    }
    
    public boolean isFireRateActive() {
    	return conditions.getStatus(1).active;
    }
    
    public boolean isDmgUpActive() {
    	return conditions.getStatus(2).active;
    }
    
    public boolean isShieldActive() {
    	return conditions.getStatus(3).active;
    }
    
    public boolean isTaserActive() {
    	return conditions.getStatus(4).active;
    }
    
    public int getHp()
    {
        return hp;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
    }

    public boolean isChangingLevel(){
        return changingLevel;
    }

    public boolean isEnd(){
        return end;
    }
}