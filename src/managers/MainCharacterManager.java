package managers;

import sprite.character.player.MainCharacter;
import sprite.Sprite;
import sprite.world.Floor;
import util.Vector2f;

import java.util.ArrayList;

public class MainCharacterManager extends Manager{
    //Get the MainCharacter's sprite sheets and make the main character.
    public MainCharacterManager(Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> rats, ArrayList<Sprite> powerups, ArrayList<Sprite> platforms){
        getSprites().add(new MainCharacter(-7, -4, new Vector2f(.3f,.3f), floor, walls, rats, powerups, platforms));
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

    public void setBulletManager(BulletManager bm){
        for(Sprite mainCharacter : getSprites()){
            ((MainCharacter)mainCharacter).setBulletManager(bm);
        }
    }
}
