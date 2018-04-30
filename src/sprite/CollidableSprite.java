package sprite;

import util.Vector2f;

public abstract class CollidableSprite extends Sprite {

    public CollidableSprite(float startX, float startY, Vector2f scale) {
        super(startX, startY, scale);
    }

    //Each collidable sprite has hitboxes which should be set in subclasses
    //the first hitbox is the outer hitbox
    public abstract void initializeHitboxes();
}
