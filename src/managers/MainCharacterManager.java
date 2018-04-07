package managers;

import bounding.BoundingBox;
import sprite.character.player.MainCharacter;
import sprite.Sprite;
import sprite.world.Floor;
import util.Intersect;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainCharacterManager extends Manager{
    //Get the MainCharacter's sprite sheets and make the main character.

    public MainCharacterManager(Floor floor, ArrayList<Sprite> walls){
        ArrayList<BufferedImage> spriteAnimations = new ArrayList<>();
        spriteAnimations.add(loadFile("src/resources/character/player/MainCharSprite_WH_237x356_Move.png"));
        spriteAnimations.add(loadFile("src/resources/character/player/MainCharSprite_WH_237x356_Idle.png"));
        spriteAnimations.add(loadFile("src/resources/character/player/MainCharSprite_WH_237x356_Jump.png"));
        getSprites().add(new MainCharacter(-7, -4, new Vector2f(.4f,.4f), spriteAnimations, floor, walls));
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

    //Check for collision with the mice
    public void checkMouseCollision(ArrayList<Sprite> mice, float delta, Matrix3x3f viewport){
        for(Sprite mainCharacter : getSprites()){
            BoundingBox spriteOutHitbox = ((BoundingBox)mainCharacter.getHitboxes().get(0));//get the mainCharacter's outer hitbox
            for(int i = 0; i < mice.size(); i++){
                BoundingBox rOH = ((BoundingBox)mice.get(i).getHitboxes().get(0));//get the rat's outer hitbox
                if(Intersect.intersectAABB(spriteOutHitbox.getCurrentMin(), spriteOutHitbox.getCurrentMax(), rOH.getCurrentMin(), rOH.getCurrentMax())){
                    //if some innerbox collides, remove a mouse
                    if(innerHitboxCollision(mainCharacter.getHitboxes(), mice.get(i).getHitboxes())){
                        mice.remove(i);
                        i--;
                    }
                }
            }
        }
    }
}
