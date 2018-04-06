package sprite.character.enemy;

import sprite.Sprite;
import util.Vector2f;

import java.awt.image.BufferedImage;

public class TriBot extends Sprite {

    public TriBot(float startX, float startY, Vector2f scale, BufferedImage currentSpriteFrame){
        super(startX,startY,scale,currentSpriteFrame);
    }
    @Override
    public void initializeHitboxes() {

    }

    @Override
    public void process(float delta) {

    }
}
