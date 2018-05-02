package sprite.world;

import java.awt.Color;
import java.awt.image.BufferedImage;

import bounding.BoundingBox;
import sprite.CollidableSprite;
import sprite.Sprite;
import status.Status;
import util.Vector2f;

public class PowerUp extends CollidableSprite {
	private Status effect;
	private float bobLen = 0;
	private boolean bobDown = false;
	private float bobRate = .75f;

	//Create the sprite
	public PowerUp(float startX, float startY, Vector2f scale, BufferedImage spriteSheet, Status statusEffect) {
		super(startX, startY, scale);
		setCurrentSpriteFrame(spriteSheet);
		effect = statusEffect;
		initializeHitboxes();
		setyTranslation(getyTranslation() + 0.3f);
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

			if(bobLen <= 0)
				bobDown = false;
		}
		else {
			bobLen += delta * bobRate;
			setyTranslation(getyTranslation() + delta * bobRate);

			if(bobLen >= 1)
				bobDown = true;
		}
	}
	
	//Return powerup's status effect
	public Status getEffect() {
		return effect;
	}
}
