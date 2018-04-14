package sprite.character.enemy;

import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.world.Floor;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy extends CharacterSprite{

    Vector2f velocity = new Vector2f(0,0);
    Enemy(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms){
        super(startX, startY, scale, floor, walls, platforms);
    }

    @Override
    public abstract void initializeHitboxes();

    @Override
    public void process(float delta){
        super.process(delta);
        for(Sprite p:platforms){
            //checkSpriteCollision();
        }
    }

}
