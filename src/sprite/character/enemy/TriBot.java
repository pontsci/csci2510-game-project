package sprite.character.enemy;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import managers.BulletManager;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Animation;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TriBot extends Enemy {
	private Animation animation = new Animation();
	private final int MOVE_ANIMATION = 0;
	private final int ATTACK_ANIMATION = 1;
	private int currentAnimation = 0;
	private int dmgTicks = 0;

	public TriBot(float startX, float startY, Vector2f scale, ArrayList<Sprite> floors, ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, MainCharacter player, ArrayList<Sprite> walls, BulletManager bm, BufferedImage spriteSheet) {
		super(startX, startY, scale, floors, screenWalls, platforms, player, walls, bm);
		// get the animations for the tri bot - follow the main character
		setCurrentSpriteFrame(spriteSheet.getSubimage(0, 0, 237, 356));
		animation.addAnimation(spriteSheet.getSubimage(0, 0, 1659, 356), 7);
		animation.addAnimation(spriteSheet.getSubimage(0, 356, 1422, 356), 6);
		initializeHitboxes();
	}

	@Override
	public void initializeHitboxes() {
		hitboxes.add(new BoundingBox(new Vector2f(-1.2f, -3), new Vector2f(1.1f, 1.9f), Color.BLUE));
		hitboxes.add(new BoundingCircle(.4f, -.1f, 1.4f, Color.RED));
		hitboxes.add(new BoundingBox(new Vector2f(-.9f, -1.7f), new Vector2f(.8f, 1.1f), Color.RED));
	}

	@Override
	public void process(float delta) {
		super.process(delta);
		processAnimations(delta);
	}

	// Process which animation is playing, when an animation finishes, it returns
	// true
	private void processAnimations(float delta) {
		switch (currentAnimation) {
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

	public void processEffects(float delta) {
		// DoT check
		if (conditions.getStatus(5).active) {
			// At 3,2,and 1 on the timer, if the player is above 1 health, damage the player
			if ((conditions.getTimer(5) > 2.0 && dmgTicks < 1)
					|| (conditions.getTimer(5) > 1.0 && conditions.getTimer(5) < 2.0 && dmgTicks < 2)
					|| (conditions.getTimer(5) > 0.0 && conditions.getTimer(5) < 1.0 && dmgTicks < 3)) {
				if (hp > 1) {
					dmgOverTime();
					dmgTicks++;
				}
			}
		} else {
			dmgTicks = 0;
		}
	}

	// DoT Effect
	public void dmgOverTime() {
		// System.out.println("DoT!!!");
		hp--;
	}
}
