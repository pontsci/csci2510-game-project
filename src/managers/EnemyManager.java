package managers;

import sprite.Sprite;
import sprite.character.enemy.TriBot;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Vector2f;
import java.util.ArrayList;

public class EnemyManager extends Manager
{
    private Floor floor;
    private ArrayList<Sprite> walls;
    private ArrayList<Sprite> platforms;
    private MainCharacter player;

    public EnemyManager(Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player)
    {
        this.floor = floor;
        this.walls = walls;
        this.platforms = platforms;
        this.player = player;
        getSprites().add(new TriBot(5, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));
    }

    public void addTriBot(){
        //getSprites().add(new TriBot(7, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));

        //getSprites().add(new TriBot(8, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));

        //getSprites().add(new TriBot(4, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));
    }

    public void addTriBot(Vector2f pos){
        
    }
}
