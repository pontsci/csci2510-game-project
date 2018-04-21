package managers;

import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.character.enemy.Enemy;
import sprite.character.enemy.TriBot;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Matrix3x3f;
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
        getSprites().add(new TriBot(5, 0, new Vector2f(.3f, .3f), floor, walls, platforms, player));
        getSprites().add(new TriBot(5, -3, new Vector2f(.3f, .3f), floor, walls, platforms, player));

    }

    public void addTriBot()
    {
        /* Performance testing
        getSprites().add(new TriBot(7, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));
        getSprites().add(new TriBot(8, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));
        getSprites().add(new TriBot(4, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));
        getSprites().add(new TriBot(6.5f, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));
        getSprites().add(new TriBot(6.2f, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));
        getSprites().add(new TriBot(6, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));
        */
    }

    public void addTriBot(Vector2f pos)
    {
        getSprites().add(new TriBot(pos.x, pos.y, new Vector2f(.3f, .3f), floor, walls, platforms, player));
    }

    public void checkCollision(float delta, Matrix3x3f viewport){
        for(Sprite sprite : getSprites()){
            ((Enemy)sprite).checkCollision(delta, viewport);
        }
    }
}
