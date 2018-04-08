package sprite.world;

import sprite.Sprite;
import util.Vector2f;
import java.awt.image.BufferedImage;

public abstract class Interactable extends Sprite{
	
	//Create the sprite
	public Interactable(float startX, float startY, Vector2f scale, BufferedImage sprite) {
		super(startX, startY, scale, sprite);
		initializeHitboxes();
	}
	
	 //Each sprite has hitboxes which should be set in subclasses
    //the first hitbox is the outer hitbox
    public abstract void initializeHitboxes();

    //Process anything that's constantly affecting the sprite
    public abstract void process(float delta);
}
