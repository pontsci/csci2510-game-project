package sprite.bullet;

import bounding.BoundingBox;
import bounding.BoundingCircle;
import bounding.BoundingShape;
import sprite.Sprite;
import util.Intersect;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public abstract class Bullet extends Sprite{
    private float bulletSpeed = 3.5f;

    public Bullet(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
    }

    @Override
    public void process(float delta){
        if(getScale().x < 0){
            shootRight(delta);
        }
        else{
            shootLeft(delta);
        }
    }

    //Shoot the bullet left
    private void shootLeft(float delta){
        setxTranslation(getxTranslation() - (bulletSpeed * delta));
        setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    // play right move animation
    private void shootRight(float delta){
        setxTranslation(getxTranslation() + (bulletSpeed * delta));
        setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
    }


    public boolean outOfBounds(){
        return getxTranslation() > 8.5 || getxTranslation() < -8.5;
    }

    public abstract void checkCollision(float delta, Matrix3x3f viewport);

}
