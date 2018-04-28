package sprite.character;

import util.Collision;
import sprite.Sprite;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public abstract class CharacterSprite extends Sprite{
    protected final int TERMINAL_VELOCITY = -5;
    protected final float ONE_PIXEL = .00833333f;
    protected ArrayList<Sprite> floors;
    protected ArrayList<Sprite> screenWalls;
    protected ArrayList<Sprite> walls;
    protected ArrayList<Sprite> platforms;
    protected float gravity = -10;
    protected int hp;
    protected int maxHp;
    protected boolean hit = false;

    public CharacterSprite(float startX, float startY, Vector2f scale, ArrayList<Sprite> floors, ArrayList<Sprite> screenWalls, ArrayList<Sprite> platforms, ArrayList<Sprite> walls){
        super(startX, startY, scale);
        this.floors = floors;
        this.screenWalls = screenWalls;
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
        checkScreenWallCollision(delta, viewport);
        checkFloorCollision(delta, viewport);
        checkPlatformCollision(delta, viewport);
        checkWallCollision(delta, viewport);
    }

    protected void checkPlatformCollision(float delta, Matrix3x3f viewport){
        for(Sprite platform : platforms){
            while(Collision.checkSpriteCollision(this, platform)){
                pushCharacter(delta, viewport, 'y', ONE_PIXEL/2);
            }
        }
    }

    protected void checkFloorCollision(float delta, Matrix3x3f viewport){
        for(Sprite floor : floors){
            while(Collision.checkSpriteCollision(this, floor)){
                pushCharacter(delta, viewport, 'y', ONE_PIXEL/2);
            }
        }
    }

    protected void checkScreenWallCollision(float delta, Matrix3x3f viewport){
        for(int i = 0; i < screenWalls.size(); i++){
            while(Collision.checkSpriteCollision(this, screenWalls.get(i))){
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

    protected void checkWallCollision(float delta, Matrix3x3f viewport){
        float xStartState = getxTranslation();
        float yStartState = getyTranslation();
        int magnitude = 1;
        if(!(walls == null)){
            for(Sprite wall : walls){
                while(Collision.checkSpriteCollision(this, wall)){
                    pushCharacter(delta, viewport, 'y', ONE_PIXEL * magnitude);
                    if(Collision.checkSpriteCollision(this, wall)){
                        setyTranslation(yStartState);
                    }
                    else{
                        break;
                    }
                    pushCharacter(delta, viewport, 'x', ONE_PIXEL * magnitude);
                    if(Collision.checkSpriteCollision(this, wall)){
                        setxTranslation(xStartState);
                    }
                    else
                        break;
                    pushCharacter(delta, viewport, 'x', -ONE_PIXEL * magnitude);
                    if(Collision.checkSpriteCollision(this, wall)){
                        setxTranslation(xStartState);
                    }
                    else
                        break;
                    pushCharacter(delta, viewport, 'y', -ONE_PIXEL * magnitude);
                    if(Collision.checkSpriteCollision(this, wall)){
                        setyTranslation(yStartState);
                    }
                    else{
                        break;
                    }
                    magnitude++;
                }
            }
        }
    }

    //Push the character
    public void pushCharacter(float delta, Matrix3x3f viewport, char axis, float amount){
        if(axis == 'y'){
            setyTranslation(getyTranslation() + amount);
            update(delta, viewport);
        }
        else if(axis == 'x'){
            setxTranslation(getxTranslation() + amount);
            update(delta, viewport);
        }
    }

    protected float getGravity(){
        return gravity;
    }

    /**
     * Takes an amount and subtracts hp by that amount
     * @param amount amount to decrease hp by
     * @return whether or not the enemy is dead
     */
    public boolean decreaseHP(int amount){
        hp = hp - amount;
        return hp == 0;
    }

    public void setHit(boolean isHit){
        hit = isHit;
    }
}
