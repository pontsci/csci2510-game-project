package managers;

import spawning.Spawner;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.world.Door;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public class DoorManager extends Manager{
    private MainCharacter player;
    private ArrayList<Sprite> enemies;

    public DoorManager(ArrayList<Sprite> enemies, MainCharacter player)
    {
        this.enemies = enemies;
        this.player = player;

    }


    public void addDoor(Vector2f pos)
    {
        getSprites().clear();
        getSprites().add(new Door(pos.x,pos.y,new Vector2f(.3f, .3f), enemies));
    }

    public void checkCollision(float delta, Matrix3x3f viewport){
        
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){

    }

}
