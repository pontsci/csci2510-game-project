package sprite.character.enemy;

import bounding.BoundingBox;
import bounding.BoundingShape;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy extends CharacterSprite{
   protected BoundingBox footBox = new BoundingBox(new Vector2f(-1.1f, -2.4f), new Vector2f(-.9f, -1.8f), Color.GREEN);

   private MainCharacter player;
    Enemy(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player){
        super(startX, startY, scale, floor, walls, platforms);
        this.player = player;
    }

    @Override
    public abstract void initializeHitboxes();

    @Override
    public void update(float delta, Matrix3x3f viewport){
        for(BoundingShape bound : hitboxes){
            bound.setxTranslation(getxTranslation());
            bound.setyTranslation(getyTranslation());
            bound.setScale(getScale());
            //bound.setRot(rotation); Squares and circles do not do well with the formulas from the book.
            bound.updateWorld(viewport);
        }
        footBox.setxTranslation(getxTranslation());
        footBox.setyTranslation(getyTranslation());
        footBox.setScale(getScale());
        footBox.updateWorld(viewport);

        setViewport(viewport);
    }

    //For each hitbox, render the hitbox
    @Override
    public void renderHitboxes(Graphics g){
        super.renderHitboxes(g);
        footBox.render(g);
    }
}
