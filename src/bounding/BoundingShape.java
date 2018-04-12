package bounding;

import util.Drawable;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;

public abstract class BoundingShape implements Drawable{
    Matrix3x3f currentWorld;
    Matrix3x3f viewport;
    private Color objectColor;

    private float rot = 0;
    private Vector2f scale = new Vector2f(1,1);
    private float xTranslation;
    private float yTranslation;

    boolean negativeXScale;//Used for correcting the box to work with inversion
    boolean negativeYScale;//Used for correcting the box to work with inversion

    //Constructor for a BoundingShape
    BoundingShape(Color c){
        objectColor = c;
    }

    //Update the world
    public void updateWorld(Matrix3x3f viewport){
        currentWorld = Matrix3x3f.identity();
        currentWorld = currentWorld.mul(Matrix3x3f.scale(scale.x, scale.y));
        currentWorld = currentWorld.mul(Matrix3x3f.rotate(rot));
        currentWorld = currentWorld.mul(Matrix3x3f.translate(xTranslation, yTranslation));
        this.viewport = viewport;
    }

    //Render the color, subclasses should implement rendering specifics
    public void render(Graphics g){
        g.setColor(objectColor);
    }

    //When the scale changes, the square and circle coordinates need to be altered accordingly
    //For example, a negative scale flips the picture, the points need to be flipped as well
    protected abstract void resetDimensions();

    //Sets the scale but also prepares the dimensions of a hitbox to change with inversion.
    public void setScale(Vector2f scale){
        if(scale.x < 0)
            negativeXScale = true;
        else
            negativeXScale = false;

        if(scale.y < 0)
            negativeYScale = true;
        else
            negativeYScale = false;

        resetDimensions();

        this.scale = new Vector2f(Math.abs(scale.x), Math.abs(scale.y));
    }
    public void setxTranslation(float xTranslation){
        this.xTranslation = xTranslation;
    }
    public void setyTranslation(float yTranslation){
        this.yTranslation = yTranslation;
    }

    Vector2f getScale(){
        return scale;
    }
}
