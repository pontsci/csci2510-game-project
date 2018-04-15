package sprite.character.enemy;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import sprite.character.CharacterSprite;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import util.Intersect;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy extends CharacterSprite{
    protected BoundingBox footBox = new BoundingBox(new Vector2f(-1.1f, -2.4f), new Vector2f(-.9f, -1.8f), Color.GREEN);
    private float walkRate = 1.5f;
    private int currentDirection = 1;
    private int GOING_RIGHT = 0;
    private int GOING_LEFT = 1;
    private boolean footboxCollision = true;
    private boolean wallCollision = false;
   private MainCharacter player;
    Enemy(float startX, float startY, Vector2f scale, Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms, MainCharacter player){
        super(startX, startY, scale, floor, walls, platforms);
        this.player = player;
    }

    @Override
    public abstract void initializeHitboxes();

    @Override
    public void update(float delta, Matrix3x3f viewport){
        super.update(delta,viewport);
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

    @Override
    public void process(float delta)
    {
        super.process(delta);
        processMovement(delta);
    }

    private void processMovement(float delta){
        //If going footBox
        if(currentDirection == GOING_LEFT){
            //If foot box collides continue footBox,
            if(footboxCollision)
                walkLeft(delta);
                //else go right
            else{
                walkRight(delta);
                currentDirection = GOING_RIGHT;
            }
        }
        //if going right
        else if(currentDirection == GOING_RIGHT){
            //If foot box collides, continue right
            if(footboxCollision)
                walkRight(delta);
                //else go footBox
            else{
                walkLeft(delta);
                currentDirection = GOING_LEFT;
            }
        }
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){
        super.checkCollision(delta, viewport);

        if(footboxCollidesWithPlatform())
            footboxCollision = true;
        else
            footboxCollision = false;

        if(wallCollision)
            footboxCollision = false;
    }

    @Override
    protected void checkWallCollision(float delta, Matrix3x3f viewport){
        wallCollision = false;
        for(int i = 0; i < walls.size(); i++){
            while(checkSpriteCollision(delta, viewport, walls.get(i))){
                if(i == 0){
                    pushCharacter(delta, viewport, 'x', ONE_PIXEL);
                    wallCollision = true;
                }
                //Right wall is being hit, move mouse footBox;
                else if(i == 1){
                    pushCharacter(delta, viewport, 'x', -ONE_PIXEL);
                    wallCollision = true;
                }
            }
        }
    }

    private boolean footboxCollidesWithPlatform(){
        for(int i = 0; i < platforms.size(); i++){
            if(checkFootBoxCollision(platforms.get(i).getHitboxes())){
                return true;
            }
        }
        return false;
    }

    private boolean checkFootBoxCollision(ArrayList<BoundingShape> platformHitboxes){
        BoundingShape platformHitbox;

        //For every inner hitbox in the foreign Sprite
        for(int j = 1; j < platformHitboxes.size(); j++){
            platformHitbox = platformHitboxes.get(j);
            if(platformHitbox instanceof BoundingBox && Intersect.intersectAABB(footBox.getCurrentMin(), footBox.getCurrentMax(), ((BoundingBox)platformHitbox).getCurrentMin(), ((BoundingBox)platformHitbox).getCurrentMax())){
                return true;
            }
            else if(platformHitbox instanceof BoundingCircle && Intersect.intersectCircleAABB(((BoundingCircle)platformHitbox).getCurrentPoint(), ((BoundingCircle)platformHitbox).getCurrentRadius(), footBox.getCurrentMin(), footBox.getCurrentMax())){
                return true;
            }
        }
        return false;
    }

    private void walkRight(float delta){
        setxTranslation(getxTranslation() + (walkRate * delta));
        setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    private void walkLeft(float delta){
        setxTranslation(getxTranslation() - (walkRate * delta));
        setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
    }
}
