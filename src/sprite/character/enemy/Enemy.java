package sprite.character.enemy;

import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.world.Floor;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy extends CharacterSprite{
    Enemy(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms){
        super(startX, startY, scale, floor, walls, platforms);
    }

    @Override
    public abstract void initializeHitboxes();

    @Override
    public abstract void process(float delta);
}
