package sprite.world;

import java.awt.Color;
import java.awt.image.BufferedImage;

import bounding.BoundingBox;
import sprite.Sprite;
import status.Status;
import util.Vector2f;

public class PowerUp extends Sprite{

	private final static int HEALTH_IMG = 0;
	private final static int FR_IMG = 1;
	private final static int DMG_IMG = 2;
	private final static int SHIELD_IMG = 3;
	private final static int TASER_IMG = 4;

	private Status effect;
	private float bobLen = 0;
	private boolean bobDown = true;
	private float bobRate = 1;
	
	//Create the sprite
	public PowerUp(float startX, float startY, Vector2f scale, int spriteImg, Status statusEffect) {
		super(startX, startY, scale);
		BufferedImage sprSheet = loadFile("src/resources/world/pickups/PickupObjects.png");

		switch(spriteImg){
			case HEALTH_IMG:
				//Using key as a stand in image
				setCurrentSpriteFrame(sprSheet.getSubimage(150, 0, 50, 50));
				break;
			case FR_IMG:
				setCurrentSpriteFrame(sprSheet.getSubimage(50, 0, 50, 50));
				break;
			case DMG_IMG:
				setCurrentSpriteFrame(sprSheet.getSubimage(0, 0, 50, 50));
				break;
			case SHIELD_IMG:
				setCurrentSpriteFrame(sprSheet.getSubimage(400, 0, 50, 50));
				break;
			case TASER_IMG:
				setCurrentSpriteFrame(sprSheet.getSubimage(100, 0, 50, 50));
				break;
		}
		effect = statusEffect;
		initializeHitboxes();
	}
	
	//Initialize hitboxes
	public void initializeHitboxes() {
		hitboxes.add(new BoundingBox(new Vector2f(-.25f, -.25f), new Vector2f(.25f, .25f), Color.BLUE));
		hitboxes.add(new BoundingBox(new Vector2f(-.25f, -.25f), new Vector2f(.25f, .25f), Color.RED));
	}
	
	//Bob the sprite up and down
	public void process(float delta) {
		//Bob up and down
		if(bobDown) {
			
			bobLen -= delta * bobRate;
			setyTranslation(getyTranslation() - delta * bobRate);
			
			if(bobLen <= -2)
				bobDown = false;
		}
		else {
			bobLen += delta * bobRate;
			setyTranslation(getyTranslation() + delta * bobRate);
			
			if(bobLen >= 0)
				bobDown = true;
		}
	}
	
	//Return powerup's status effect
	public Status getEffect() {
		return effect;
	}
}
