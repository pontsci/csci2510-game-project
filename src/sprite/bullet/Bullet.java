package sprite.bullet;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import util.Intersect;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public abstract class Bullet extends Sprite{
    private float bulletSpeed = 3.5f;

    public Bullet(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
    }

    @Override
    public void process(float delta){
        if(getScale().x < 0){
            shootRight(delta);
        }
        else{
            shootLeft(delta);
        }
    }

    //Shoot the bullet left
    private void shootLeft(float delta){
        setxTranslation(getxTranslation() - (bulletSpeed * delta));
        setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    // play right move animation
    private void shootRight(float delta){
        setxTranslation(getxTranslation() + (bulletSpeed * delta));
        setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
    }


    public boolean outOfBounds(){
        return getxTranslation() > 8.5 || getxTranslation() < -8.5;
    }

    public abstract void checkCollision(float delta, Matrix3x3f viewport);

    //returns true if collides with given sprite
    protected boolean checkSpriteCollision(float delta, Matrix3x3f viewport, Sprite sprite){
        Vector2f spriteMin = ((BoundingBox)sprite.getHitboxes().get(0)).getCurrentMin();
        Vector2f spriteMax = ((BoundingBox)sprite.getHitboxes().get(0)).getCurrentMax();
        Vector2f characterMin = ((BoundingBox)getHitboxes().get(0)).getCurrentMin();
        Vector2f characterMax = ((BoundingBox)getHitboxes().get(0)).getCurrentMax();

        return Intersect.intersectAABB(characterMin, characterMax, spriteMin, spriteMax) && checkInnerCollision(sprite.getHitboxes());
    }

    //Check collision of the inner hitboxes of the character, and the foreign hitboxes.
    //returns true if the inner hitboxes collide.
    private boolean checkInnerCollision(ArrayList<BoundingShape> foreignHitboxes){
        BoundingShape foreignHitbox;
        BoundingShape characterHitbox;

        //For every inner hitbox in the CharacterSprite
        for(int i = 1; i < getHitboxes().size(); i++){
            characterHitbox = getHitboxes().get(i);

            //For every inner hitbox in the foreign Sprite
            for(int j = 1; j < foreignHitboxes.size(); j++){
                foreignHitbox = foreignHitboxes.get(j);

                if(characterHitbox instanceof BoundingBox && foreignHitbox instanceof BoundingBox){
                    if(Intersect.intersectAABB(
                            ((BoundingBox)characterHitbox).getCurrentMin(),
                            ((BoundingBox)characterHitbox).getCurrentMax(),
                            ((BoundingBox)foreignHitbox).getCurrentMin(),
                            ((BoundingBox)foreignHitbox).getCurrentMax())){
                        return true;
                    }
                }
                else if(characterHitbox instanceof BoundingBox && foreignHitbox instanceof BoundingCircle){
                    if(Intersect.intersectCircleAABB(
                            ((BoundingCircle)foreignHitbox).getCurrentPoint(),
                            ((BoundingCircle)foreignHitbox).getCurrentRadius(),
                            ((BoundingBox)characterHitbox).getCurrentMin(),
                            ((BoundingBox)characterHitbox).getCurrentMax())){
                        return true;
                    }
                }
                else if(characterHitbox instanceof BoundingCircle && foreignHitbox instanceof BoundingBox){
                    if(Intersect.intersectCircleAABB(
                            ((BoundingCircle)characterHitbox).getCurrentPoint(),
                            ((BoundingCircle)characterHitbox).getCurrentRadius(),
                            ((BoundingBox)foreignHitbox).getCurrentMin(),
                            ((BoundingBox)foreignHitbox).getCurrentMax())){
                        return true;
                    }
                }
                else if(characterHitbox instanceof BoundingCircle && foreignHitbox instanceof BoundingCircle){
                    if(Intersect.intersectCircle(
                            ((BoundingCircle)characterHitbox).getCurrentPoint(),
                            ((BoundingCircle)characterHitbox).getCurrentRadius(),
                            ((BoundingCircle)foreignHitbox).getCurrentPoint(),
                            ((BoundingCircle)foreignHitbox).getCurrentRadius())){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
