package sprite.character.enemy;

import sprite.Sprite;
import util.Vector2f;

import java.awt.image.BufferedImage;

public abstract class Enemy extends Sprite
{
    public Enemy(float startX, float startY, Vector2f scale, BufferedImage currentSpriteFrame){
        super(startX,startY,scale,currentSpriteFrame);
    }

    @Override
    public abstract void initializeHitboxes();

    @Override
    public abstract void process(float delta);
}
