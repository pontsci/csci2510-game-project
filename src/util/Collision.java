package util;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;

import java.util.ArrayList;

public class Collision {

    //Given two sprites, return if they collide
    public static boolean checkSpriteCollision(float delta, Matrix3x3f viewport, Sprite thisSprite, Sprite otherSprite){
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

}
