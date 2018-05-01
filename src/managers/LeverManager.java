package managers;

import spawning.Spawner;
import sprite.Sprite;
import sprite.world.Lever;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LeverManager extends Manager{
    ArrayList<Sprite> enemies;
    ArrayList<Sprite> players;
    BufferedImage leverSpriteSheet = loadFile("src/resources/world/foreground/lever/Interact_Switch_WH_285x203.png");

    public void initialize(ArrayList<Sprite> enemies, ArrayList<Sprite> players){
        this.enemies = enemies;
        this.players = players;
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){
        //Not needed, the character interacts with the door
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 4:
                getSprites().add(new Lever(7, -.75f, new Vector2f(.5f,.58f), enemies, leverSpriteSheet));
        }
    }
}
