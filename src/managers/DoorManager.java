package managers;

import sprite.Sprite;
import sprite.character.enemy.Enemy;
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

    @Override
    public void update(float delta, Matrix3x3f viewport) {
        super.update(delta, viewport);
        if(enemies.isEmpty()){
            ((Door)getSprites().get(0)).setOpen();
        }
        else{
            ((Door)getSprites().get(0)).setClose();
        }
    }

    public void addDoor(Vector2f pos)
    {
        getSprites().clear();
        getSprites().add(new Door(pos.x,pos.y,new Vector2f(.3f, .3f)));
    }

    public void checkCollision(float delta, Matrix3x3f viewport){
        
    }

}
