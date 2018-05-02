package managers;

import spawning.Spawner;
import sprite.character.player.MainCharacter;
import sprite.Sprite;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainCharacterManager extends Manager{

    /**
     * Get the MainCharacter's sprite sheets and make the main character.
     * @param screenWalls the screen walls
     * @param powerups the power ups
     * @param platforms the platforms
     * @param walls the walls
     * @param bm the bulletManager
     * @param doors the doors
     * @param levers the levers
     */
    public void initialize( ArrayList<Sprite> screenWalls, ArrayList<Sprite> powerups, ArrayList<Sprite> platforms, ArrayList<Sprite> walls, BulletManager bm, ArrayList<Sprite> doors, ArrayList<Sprite> levers){
        getSprites().add(new MainCharacter(0, 0, new Vector2f(-.3f,.3f), screenWalls, powerups, platforms, walls, bm, doors,loadFile("src/resources/character/player/MainCharSprite_WH_237x356_Idle.png"), loadFile("src/resources/character/player/MainCharSprite_WH_237x356_Jump.png"), loadFile("src/resources/character/player/MainCharSprite_WH_237x356_Move.png"), levers));
    }

    /**
     * Run checkCollision for all MainCharacters
     * @param delta time
     * @param viewport the viewport
     */
    public void checkCollision(float delta, Matrix3x3f viewport){
        for(Sprite sprite : getSprites()){
            ((MainCharacter)sprite).checkCollision(delta, viewport);
        }
    }

    /**
     * Depending on the level, spawn the MainCharacter in different positions
     * @param level the current level
     * @param spawner the spawn system
     * @param viewport the viewport
     */
    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        switch(level){
            case 1:
                getSprites().get(0).setxTranslation(-7);
                getSprites().get(0).setyTranslation(-4);
                break;
            case 2:
                getSprites().get(0).setxTranslation(7);
                getSprites().get(0).setyTranslation(-4);
                break;
            case 3:
                getSprites().get(0).setxTranslation(-7);
                getSprites().get(0).setyTranslation(-4);
                break;
            case 4:
                getSprites().get(0).setxTranslation(-7);
                getSprites().get(0).setyTranslation(-.25f);
                break;
        }
    }

    /**
     * Tell all main characters to jump - Note, there should only be one
     */
    public void processJump(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).jump();
        }
    }

    /**
     * Tell all main characters to move left
     * @param delta time
     */
    public void processWalkLeft(float delta){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).walkLeft(delta);
        }
    }

    /**
     * Tell all main characters to move right
     * @param delta time
     */
    public void processWalkRight(float delta){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).walkRight(delta);
        }
    }

    /**
     * Tell all main characters to be idle
     */
    public void processIdle(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).idle();
        }
    }

    /**
     * Tell all main characters to shoot
     */
    public void processShoot(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).shoot();
        }
    }

    /**
     * Tell all main characters to ignore platforms
     */
    public void processIgnorePlatformCollision(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).ignorePlatformCollision();
        }
    }

    /**
     * Tell all main characters to not ignore platforms
     */
    public void processAllowPlatformCollision(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).allowPlatformCollision();
        }
    }

    /**
     * Tell all main characters that they can go through the door
     */
    public void canGoThroughDoor(){
        for(Sprite player : getSprites()){
            ((MainCharacter)player).canGoThroughDoor();
        }
    }

    /**
     * Determine if the player is dead
     * @param xBound x screen bounds
     * @param yBound y screen bounds
     * @return whether or not the player is dead
     */
    public boolean isDead(float xBound, float yBound){
        boolean dead = false;
        for(Sprite mainCharacter : getSprites()){
            if(((MainCharacter)mainCharacter).getHp() < 0){
                dead = true;
            }
            if((mainCharacter).getxTranslation() > xBound ||(mainCharacter).getxTranslation() < -xBound ||
                    (mainCharacter).getyTranslation() > yBound || (mainCharacter).getyTranslation() < -yBound){
                dead = true;
            }
        }
        return dead;
    }

    /**
     * reset all main character health to 3
     */
    public void reset(){
         for(Sprite mainCharacter : getSprites()){
             ((MainCharacter)mainCharacter).setHp(3);
         }
    }

    /**
     * set all main character health to -1
     */
    public void die(){
        for(Sprite mainCharacter : getSprites()){
             ((MainCharacter)mainCharacter).setHp(-1);
         }
    }
}
