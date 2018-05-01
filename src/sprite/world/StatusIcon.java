package sprite.world;

import java.awt.image.BufferedImage;

import sprite.Sprite;
import util.Vector2f;

public class StatusIcon extends Sprite{
	
	public StatusIcon(Vector2f scale, BufferedImage icon) {
		super(5.5f, 4, scale);
		super.setCurrentSpriteFrame(icon);
	}
	
	public void process(float delta) {}
}
