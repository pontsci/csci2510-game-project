package sprite.character.enemy;

import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.world.Floor;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy extends CharacterSprite
{
    Enemy(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls){
        super(startX,startY,scale, floor, walls);
    }

    @Override
    public abstract void initializeHitboxes();

    @Override
    public abstract void process(float delta);
}
