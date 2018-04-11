package sprite.character;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import sprite.world.Floor;
import util.Intersect;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public abstract class CharacterSprite extends Sprite{
    protected final int TERMINAL_VELOCITY = -5;
    private Floor floor;
    private ArrayList<Sprite> walls = new ArrayList<>();
    private float gravity = -10;
    private boolean onTheFloor = false;
    int health;
    boolean onFire = false;
    boolean dotHeal = false;
    public CharacterSprite(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls){
        super(startX, startY, scale);
        this.floor = floor;
        this.walls = walls;
    }

    //begin checking collision with floor and wall
    public void checkCollision(float delta, Matrix3x3f viewport){
        for(int i = 0; i < walls.size(); i++){
            while(checkSpriteCollision(delta, viewport, walls.get(i))){
                if(i == 0){
                    pushCharacter(delta, viewport, 'x', .001f);
                }
                //Right wall is being hit, move mouse left;
                else if(i == 1){
                    pushCharacter(delta, viewport, 'x', -.001f);
                }
            }
        }
        onTheFloor = false;
        while(checkSpriteCollision(delta, viewport, floor)){
            //If the character collided with the floor, push the character out of the floor and set onTheFloor to true
            pushCharacter(delta, viewport, 'y', .001f);
            onTheFloor = true;
        }
    }

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

    //Push the character back into the world
    private void pushCharacter(float delta, Matrix3x3f viewport, char axis, float amount){
        if(axis == 'y'){
            setRotation(0);
            setyTranslation(getyTranslation() + amount);
            update(delta, viewport);
        }
        else if(axis == 'x'){
            setxTranslation(getxTranslation() + amount);
            update(delta, viewport);
        }
    }

    protected void setOnTheFloor(boolean onTheFloor){
        this.onTheFloor = onTheFloor;
    }

    protected boolean onTheFloor(){
        return onTheFloor;
    }

    public float getGravity(){
        return gravity;
    }
}
