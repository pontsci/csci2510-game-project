package sprite.character;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import sprite.world.Floor;
import util.Intersect;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class CharacterSprite extends Sprite{
    Floor floor;
    ArrayList<Sprite> walls = new ArrayList<>();
    int health;
    boolean onFire = false;
    boolean dotHeal = false;
    public CharacterSprite(float startX, float startY, Vector2f scale, BufferedImage currentSpriteFrame, Floor floor, ArrayList<Sprite> walls){
        super(startX, startY, scale, currentSpriteFrame);
        this.floor = floor;
        this.walls = walls;
    }

    public void checkCollision(float delta, Matrix3x3f viewport){
        checkWallCollision(delta, viewport);
        checkFloorCollision(delta, viewport);
    }

    private void checkWallCollision(float delta, Matrix3x3f viewport){
        //Get the outer hitboxes' min and max to check collision
        Vector2f wallMin;
        Vector2f wallMax;
        Vector2f characterMin = ((BoundingBox)getHitboxes().get(0)).getCurrentMin();
        Vector2f characterMax = ((BoundingBox)getHitboxes().get(0)).getCurrentMax();

        for(int i = 0; i < walls.size(); i++){
            wallMin = ((BoundingBox)walls.get(i).getHitboxes().get(0)).getCurrentMin();
            wallMax = ((BoundingBox)walls.get(i).getHitboxes().get(0)).getCurrentMax();
            if(Intersect.intersectAABB(characterMin, characterMax, wallMin, wallMax)){
                while(checkInnerCollision(walls.get(i).getHitboxes())){
                    if(i == 0){
                        pushCharacter(delta, viewport, 'x', .0001f);
                    }
                    //Right wall is being hit, move mouse left;
                    else if(i == 1){
                        pushCharacter(delta, viewport, 'x', -.0001f);
                    }
                }
            }
        }
    }

    //Check if the character sprite is colliding with the floor.
    private void checkFloorCollision(float delta, Matrix3x3f viewport){
        //Get the outer hitboxes' min and max to check collision
        Vector2f floorMin = ((BoundingBox)floor.getHitboxes().get(0)).getCurrentMin();
        Vector2f floorMax = ((BoundingBox)floor.getHitboxes().get(0)).getCurrentMax();
        Vector2f characterMin = ((BoundingBox)getHitboxes().get(0)).getCurrentMin();
        Vector2f characterMax = ((BoundingBox)getHitboxes().get(0)).getCurrentMax();

        if(Intersect.intersectAABB(characterMin, characterMax, floorMin, floorMax)){
            while(checkInnerCollision(floor.getHitboxes())){
                pushCharacter(delta, viewport, 'y', .0001f);
            }
        }
    }

    //Check collision of the inner hitboxes of the character, and the foreign hitboxes.
    //returns true if the inner hitboxes collide.
    protected boolean checkInnerCollision(ArrayList<BoundingShape> foreignHitboxes){
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
}
