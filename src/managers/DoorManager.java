package managers;

import spawning.Spawner;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.world.Door;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public class DoorManager extends Manager{
    private ArrayList<Sprite> players;
    private ArrayList<Sprite> enemies;

    public void initialize(ArrayList<Sprite> enemies, ArrayList<Sprite> players){
        this.enemies = enemies;
        this.players = players;
        getSprites().add(new Door(7.25f,-3.47f ,new Vector2f(.35f, .35f), enemies));
    }


    public void checkCollision(float delta, Matrix3x3f viewport){
        
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 1:
                getSprites().add(new Door(7.25f,-3.47f ,new Vector2f(.35f, .35f), enemies));
                break;
            case 2:
                getSprites().add(new Door(5.25f,-3.47f ,new Vector2f(.35f, .35f), enemies));
                break;
            case 3:
                getSprites().add(new Door(3.25f,-3.47f ,new Vector2f(.35f, .35f), enemies));
                break;
        }
    }

}
