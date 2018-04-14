package managers;

import sprite.Sprite;
import sprite.character.enemy.TriBot;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager extends Manager
{
    private ArrayList<BufferedImage> triBotAnimations = new ArrayList<>();

    public EnemyManager(Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player)
    {
        getSprites().add(new TriBot(5, 0, new Vector2f(.4f, .4f), floor, walls, platforms, player));
    }
}
