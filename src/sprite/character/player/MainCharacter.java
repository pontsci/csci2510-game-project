package sprite.character.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.world.Floor;
import sprite.world.PowerUp;
import status.VulnStatus;
import util.Animation;
import util.Matrix3x3f;
import util.Vector2f;

public class MainCharacter extends CharacterSprite implements VulnStatus {
	private final int MOVE_ANIMATION = 0;
	private final int IDLE_ANIMATION = 1;
	private final int JUMP_ANIMATION = 2;
	private final int FALL_ANIMATION = 3;
	private final int LAND_ANIMATION = 4;
	private ArrayList<Sprite> rats;
	private ArrayList<Sprite> powerups;
	private Animation animation = new Animation();
	private int currentAnimation = 1;
	private float velocityY = 0;
	private float walkRate = 2.5f;// Walk rate per second. (The world is 16 by 9)
	private int hp = 3;
	private int healTicks = 0;//Tick values for hp and dmg
	private int dmgTicks = 0;

	public MainCharacter(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> rats, ArrayList<Sprite> powerups) {
		super(startX, startY, scale, floor, walls);
		BufferedImage idleAnimation = loadFile("src/resources/character/player/MainCharSprite_WH_237x356_Idle.png");
		BufferedImage jumpAnimation = loadFile("src/resources/character/player/MainCharSprite_WH_237x356_Jump.png");

		//Always set the frame, even if it runs without setting the frame, a null error can occur on animated sprites when you try to create a new one.
		setCurrentSpriteFrame(idleAnimation.getSubimage(0, 0, 237, 356));
		animation.addAnimation(loadFile("src/resources/character/player/MainCharSprite_WH_237x356_Move.png"), 6);
		animation.addAnimation(idleAnimation, 5);
		animation.addAnimation(jumpAnimation.getSubimage(0,0,474,356), 2);
		//falling part of the jump animation
		animation.addAnimation(jumpAnimation.getSubimage(474, 0, 711, 356),3);
		//landing part of the jump animation
		animation.addAnimation(jumpAnimation.getSubimage(1185, 0, 474, 356), 2);
		this.rats = rats;
		this.powerups = powerups;
		initializeHitboxes();
	}

	// Initialize the main character's hitboxes, the first box is the outer hitbox
	public void initializeHitboxes() {
		// Create the hitboxes
		hitboxes.add(new BoundingBox(new Vector2f(-1.2f, -2), new Vector2f(1.1f, 1.9f), Color.BLUE));
		hitboxes.add(new BoundingCircle(.4f, -.1f, 1.4f, Color.RED));
		hitboxes.add(new BoundingBox(new Vector2f(-1.1f, -1.9f), new Vector2f(1f, 1.1f), Color.RED));
	}

	// Process functions of the main character
	public void process(float delta) {
		//Set animation to falling if the character is falling.
		if(!onTheFloor() && currentAnimation != JUMP_ANIMATION){
			currentAnimation = FALL_ANIMATION;
		}

		//if not on the floor, change the character's velocity based on gravity.
		if(!onTheFloor()){
			if(velocityY > TERMINAL_VELOCITY){
				velocityY += getGravity() * delta;
			}
		}
		else{
			velocityY = 0 + (getGravity() * delta);
		}

		setyTranslation(getyTranslation() + velocityY * delta);

		processAnimations(delta);
		conditions.updateObjects(delta);
		processEffects(delta);
	}


	// Process which animation is playing, when an animation finishes, it returns true
	private void processAnimations(float delta) {
		switch (currentAnimation){
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
				if(onTheFloor())
					currentAnimation = LAND_ANIMATION;
				break;
			case FALL_ANIMATION:
				animation.playAnimation(delta, FALL_ANIMATION, this);
				if(onTheFloor())
					currentAnimation = LAND_ANIMATION;
				break;
			case LAND_ANIMATION:
				if(animation.playAnimation(delta, LAND_ANIMATION, this)){
					currentAnimation = IDLE_ANIMATION;
				}
				break;
		}
	}

	// Renders character and conditions
	public void render(Graphics g) {
		super.render(g);
		conditions.render(g);
	}

	// checks rat and powerups collision
	@Override
	public void checkCollision(float delta, Matrix3x3f viewport) {
		super.checkCollision(delta, viewport);
		for (int i = 0; i < rats.size(); i++) {
			if (checkSpriteCollision(delta, viewport, rats.get(i))) {
				rats.remove(i);
				i--;
			}
		}
		
		//Checks powerups collision, activate status effect with the same name in VulnStatus's conditions.
		for (int i = 0; i < powerups.size(); i++) {
			if (checkSpriteCollision(delta, viewport, powerups.get(i))) {
				conditions.activateStatus(((PowerUp)(powerups.get(i))).getEffect().name);
				powerups.remove(i);
				i--;
			}
		}
	}

	// play left move animation
	public void walkLeft(float delta) {
		if (currentAnimation != JUMP_ANIMATION && currentAnimation != MOVE_ANIMATION) {
			currentAnimation = MOVE_ANIMATION;
		}
		setxTranslation(getxTranslation() - (walkRate * delta));
		setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
	}

	// play right move animation
	public void walkRight(float delta) {
		if (currentAnimation != JUMP_ANIMATION && currentAnimation != MOVE_ANIMATION)
			currentAnimation = MOVE_ANIMATION;
		setxTranslation(getxTranslation() + (walkRate * delta));
		setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
	}

	// play jump animation
	public void jump() {
		if (currentAnimation != JUMP_ANIMATION){
			currentAnimation = JUMP_ANIMATION;
			velocityY = 5f;
			setOnTheFloor(false);
		}
	}

	// Play idle animation as long as the main character is not jumping or already
	// idle.
	public void idle() {
		if (currentAnimation != JUMP_ANIMATION && currentAnimation != IDLE_ANIMATION)
			currentAnimation = IDLE_ANIMATION;
	}

	// Process status effects
	public void processEffects(float delta) {
		//Heal check
		if (conditions.getStatus(0).active) {
			
			//At 1.5, 1, and 0.5, if the player won't be healed above 3, heal the player.
			if ((conditions.getTimer(0) > 1.0 && healTicks < 1)
					|| (conditions.getTimer(0) > 0.5 && conditions.getTimer(0) < 1.0 && healTicks < 2)
					|| (conditions.getTimer(0) > 0.0 && conditions.getTimer(0) < 0.5
							&& healTicks < 3)) {
				if (hp < 3) {
					healthEffect();
					healTicks++;
				}
			}
			
		}
		else
			healTicks = 0;

		//Fire Rate Check
		if(conditions.getStatus(1).active) {
			//System.out.println("FR!!!");
		}
		
		//DMG Up Check
		if(conditions.getStatus(2).active) {
			//System.out.println("DMG+!!!");
		}
		
		//Shield Check
		if(conditions.getStatus(3).active) {
			//System.out.println("SHIELD!!!");
		}
		
		//Taser Check
		if(conditions.getStatus(4).active) {
			//System.out.println("TASER!!!");
		}
		
		//DoT check
		if (conditions.getStatus(5).active) {
			//At 3,2,and 1 on the timer, if the player is above 1 health, damage the player
			if ((conditions.getTimer(5) > 2.0 && dmgTicks < 1)
					|| (conditions.getTimer(5) > 1.0 && conditions.getTimer(5) < 2.0 && dmgTicks < 2)
					|| (conditions.getTimer(5) > 0.0 && conditions.getTimer(5) < 1.0
							&& dmgTicks < 3)) {
				if (hp > 1) {
					dmgOverTime();
					dmgTicks++;
				}
			}
		}
		else
			dmgTicks = 0;
		
		
	}

	// Health Item effect
	public void healthEffect() {
		//System.out.println("HEAL!!!");
		hp++;
	}

	//These effects are handled by a manager, or are not implemented yet (on/off values)
	public void fireRateEffect() {}
	public void dmgEffect() {}
	public void shieldEffect() {}
	public void taserEffect() {}

	//DoT Effect
	public void dmgOverTime() {
		//System.out.println("DoT!!!");
		hp--;
	}
}