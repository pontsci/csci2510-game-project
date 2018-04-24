package sprite.character;

import util.Collision;
import sprite.Sprite;
import sprite.world.Floor;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public abstract class CharacterSprite extends Sprite{
    protected final int TERMINAL_VELOCITY = -5;
    protected final float ONE_PIXEL = .00833333f;
    protected Floor floor;
    protected ArrayList<Sprite> walls;
    protected ArrayList<Sprite> platforms;
    private float gravity = -10;
    protected int hp;
    protected int maxHp;

    public CharacterSprite(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms){
        super(startX, startY, scale);
        this.floor = floor;
        this.walls = walls;
        this.platforms = platforms;
    }

    @Override
    public void process(float delta){
        processGravity(delta);
    }

    protected void processGravity(float delta){
        setyTranslation(getyTranslation() + ((getGravity()) * delta));
    }

    //begin checking collision with floor and wall
    public void checkCollision(float delta, Matrix3x3f viewport){
        checkWallCollision(delta, viewport);
        checkFloorCollision(delta, viewport);
        checkPlatformCollision(delta, viewport);
    }

    protected void checkPlatformCollision(float delta, Matrix3x3f viewport){
        for(int i = 0; i < platforms.size(); i++){
            while(Collision.checkSpriteCollision(delta, viewport, this, platforms.get(i))){
                pushCharacter(delta, viewport, 'y', ONE_PIXEL);
            }
        }
    }

    protected void checkFloorCollision(float delta, Matrix3x3f viewport){
        while(Collision.checkSpriteCollision(delta, viewport, this, floor)){
            //If the character collided with the floor, push the character out of the floor and set onTheFloor to true
            pushCharacter(delta, viewport, 'y', ONE_PIXEL);
        }
    }

    protected void checkWallCollision(float delta, Matrix3x3f viewport){
        for(int i = 0; i < walls.size(); i++){
            while(Collision.checkSpriteCollision(delta, viewport, this, walls.get(i))){
                if(i == 0){
                    pushCharacter(delta, viewport, 'x', ONE_PIXEL);
                }
                //Right wall is being hit, move mouse footBox;
                else if(i == 1){
                    pushCharacter(delta, viewport, 'x', -ONE_PIXEL);
                }
            }
        }
    }

    //Push the character back into the world
    public void pushCharacter(float delta, Matrix3x3f viewport, char axis, float amount){
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

    public float getGravity(){
        return gravity;
    }
}
