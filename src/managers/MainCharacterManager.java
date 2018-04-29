package managers;

import spawning.Spawner;
import sprite.character.player.MainCharacter;
import sprite.Sprite;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public class MainCharacterManager extends Manager{
    //Get the MainCharacter's sprite sheets and make the main character.
    public void initialize(ArrayList<Sprite> floors, ArrayList<Sprite> screenWalls, ArrayList<Sprite> powerups, ArrayList<Sprite> platforms, ArrayList<Sprite> walls, BulletManager bm, ArrayList<Sprite> doors){
        getSprites().add(new MainCharacter(0, 0, new Vector2f(-.3f,.3f), floors, screenWalls, powerups, platforms, walls, bm, doors));
    }

    public void checkCollision(float delta, Matrix3x3f viewport){
        for(Sprite sprite : getSprites()){
            ((MainCharacter)sprite).checkCollision(delta, viewport);
        }
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        switch(level){
            case 1:
                getSprites().get(0).setxTranslation(-4);
                getSprites().get(0).setyTranslation(1.75f);
                break;
            case 2:
                getSprites().get(0).setxTranslation(-3);
                getSprites().get(0).setyTranslation(-4);
                break;
            case 3:
                getSprites().get(0).setxTranslation(-2);
                getSprites().get(0).setyTranslation(-4);
                break;
        }
    }

    //Tell all main characters to jump - Note, there should only be one
    public void processJump(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).jump();
        }
    }

    //Tell all main characters to move left
    public void processWalkLeft(float delta){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).walkLeft(delta);
        }
    }

    //Tell all main characters to move right
    public void processWalkRight(float delta){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).walkRight(delta);
        }
    }

    //Tell all main characters to be idle
    public void processIdle(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).idle();
        }
    }

    //Tell all main characters to shoot
    public void processShoot(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).shoot();
        }
    }

    public void processIgnorePlatformCollision(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).ignorePlatformCollision();
        }
    }

    public void processAllowPlatformCollision(){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).allowPlatformCollision();
        }
    }

    public void canGoThroughDoor(){
        for(Sprite player : getSprites()){
            ((MainCharacter)player).canGoThroughDoor();
        }
    }

    public boolean isDead(float xBound, float yBound){
        boolean dead = false;
        for(Sprite mainCharacter : getSprites()){
            if(((MainCharacter)mainCharacter).getHp() < 1){
                dead = true;
            }
            if((mainCharacter).getxTranslation() > xBound ||(mainCharacter).getxTranslation() < -xBound ||
                    (mainCharacter).getyTranslation() > yBound || (mainCharacter).getyTranslation() < -yBound){
                dead = true;
            }
        }
        return dead;
    }
    
    public void reset(){
         for(Sprite mainCharacter : getSprites()){
             ((MainCharacter)mainCharacter).setHp(3);
         }
    }
    
    public void die(){
        for(Sprite mainCharacter : getSprites()){
             ((MainCharacter)mainCharacter).setHp(0);
         }
    }
}
