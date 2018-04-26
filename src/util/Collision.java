package util;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import sprite.character.CharacterSprite;

import java.util.ArrayList;

public class Collision {
    protected static final float ONE_PIXEL = .00833333f;

    //Given two sprites, return if they collide
    public static boolean checkSpriteCollision(Sprite thisSprite, Sprite otherSprite){
        Vector2f spriteMin = ((BoundingBox)otherSprite.getHitboxes().get(0)).getCurrentMin();
        Vector2f spriteMax = ((BoundingBox)otherSprite.getHitboxes().get(0)).getCurrentMax();
        Vector2f characterMin = ((BoundingBox)thisSprite.getHitboxes().get(0)).getCurrentMin();
        Vector2f characterMax = ((BoundingBox)thisSprite.getHitboxes().get(0)).getCurrentMax();

        return Intersect.intersectAABB(characterMin, characterMax, spriteMin, spriteMax) && checkInnerCollision(thisSprite.getHitboxes(), otherSprite.getHitboxes(), 1);
    }

    //given two sets of hitboxes, return if they collide
    //start indicates where to start in the array, typically 1
    public static boolean checkInnerCollision(ArrayList<BoundingShape> thisHitboxes, ArrayList<BoundingShape> otherHitboxes, int start){
        BoundingShape otherHitbox;
        BoundingShape thisHitbox;

        //For every inner hitbox in the CharacterSprite
        for(int i = start; i < thisHitboxes.size(); i++){
            thisHitbox = thisHitboxes.get(i);

            //For every inner hitbox in the foreign Sprite
            for(int j = start; j < otherHitboxes.size(); j++){
                otherHitbox = otherHitboxes.get(j);

                if(thisHitbox instanceof BoundingBox && otherHitbox instanceof BoundingBox){
                    if(Intersect.intersectAABB(
                            ((BoundingBox)thisHitbox).getCurrentMin(),
                            ((BoundingBox)thisHitbox).getCurrentMax(),
                            ((BoundingBox)otherHitbox).getCurrentMin(),
                            ((BoundingBox)otherHitbox).getCurrentMax())){
                        return true;
                    }
                }
                else if(thisHitbox instanceof BoundingBox && otherHitbox instanceof BoundingCircle){
                    if(Intersect.intersectCircleAABB(
                            ((BoundingCircle)otherHitbox).getCurrentPoint(),
                            ((BoundingCircle)otherHitbox).getCurrentRadius(),
                            ((BoundingBox)thisHitbox).getCurrentMin(),
                            ((BoundingBox)thisHitbox).getCurrentMax())){
                        return true;
                    }
                }
                else if(thisHitbox instanceof BoundingCircle && otherHitbox instanceof BoundingBox){
                    if(Intersect.intersectCircleAABB(
                            ((BoundingCircle)thisHitbox).getCurrentPoint(),
                            ((BoundingCircle)thisHitbox).getCurrentRadius(),
                            ((BoundingBox)otherHitbox).getCurrentMin(),
                            ((BoundingBox)otherHitbox).getCurrentMax())){
                        return true;
                    }
                }
                else if(thisHitbox instanceof BoundingCircle && otherHitbox instanceof BoundingCircle){
                    if(Intersect.intersectCircle(
                            ((BoundingCircle)thisHitbox).getCurrentPoint(),
                            ((BoundingCircle)thisHitbox).getCurrentRadius(),
                            ((BoundingCircle)otherHitbox).getCurrentPoint(),
                            ((BoundingCircle)otherHitbox).getCurrentRadius())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //given two hitboxes, return whether they collide
    public static boolean checkCollision(BoundingShape thisHitbox, BoundingShape otherHitbox){
        ArrayList<BoundingShape> thisHitboxes = new ArrayList<>();
        ArrayList<BoundingShape> otherHitboxes = new ArrayList<>();
        thisHitboxes.add(thisHitbox);
        otherHitboxes.add(otherHitbox);
        return checkInnerCollision(thisHitboxes,otherHitboxes,0);
    }

    //given a hitbox, check it against a list of hitboxes
    public static boolean checkCollision(BoundingShape thisHitbox, ArrayList<BoundingShape> otherHitboxes){
        ArrayList<BoundingShape> thisHitboxes = new ArrayList<>();
        thisHitboxes.add(thisHitbox);
        thisHitboxes.add(thisHitbox);
        return checkInnerCollision(thisHitboxes, otherHitboxes, 1);
    }

    public static boolean intersectSegment(Vector2f origin, Vector2f destination, Sprite object, float xPadding, float yPadding){
        BoundingBox box = ((BoundingBox)(object.getHitboxes().get(0)));

        float boxHalfX = (box.getCurrentMax().x - box.getCurrentMin().x)/2;
        float boxHalfY = (box.getCurrentMax().y - box.getCurrentMin().y)/2;
        Vector2f pos = object.getPos();


        float scaleX = 1.0f / destination.x;
        float scaleY = 1.0f / destination.y;
        float signX = Integer.signum((int)scaleX);
        float signY = Integer.signum((int)scaleY);
        float nearTimeX = (origin.x - signX * (boxHalfX + xPadding) - pos.x) * scaleX;
        float nearTimeY = (origin.y - signY * (boxHalfY + yPadding) - pos.y) * scaleY;
        float farTimeX = (origin.x + signX * (boxHalfX + xPadding) - pos.x) * scaleX;
        float farTimeY = (origin.y + signY * (boxHalfY + yPadding) - pos.y) * scaleY;


        return false;
    }

}
