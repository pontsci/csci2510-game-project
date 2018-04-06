package bounding;

import util.Drawable;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;

public abstract class BoundingShape implements Drawable{
    protected Matrix3x3f currentWorld;
    protected Matrix3x3f viewport;
    private Color objectColor;

    private float rot = 0;
    private Vector2f scale = new Vector2f(1,1);
    private float xTranslation;
    private float yTranslation;

    protected Vector2f point;
    protected Vector2f min;
    protected Vector2f max;
    protected float radius;

    protected boolean negativeXScale;//Used for correcting the box to work with inversion
    protected boolean negativeYScale;//Used for correcting the box to work with inversion

    protected Vector2f originalMin;//this will only change if the entire shape of the object changes
    protected Vector2f originalMax;//this will only change if the entire shape of the object changes
    protected Vector2f originalPoint;//this will only change if the entire shape of the object changes


    //Constructor for a Box
    public BoundingShape(Vector2f min, Vector2f max, float startX, float startY, Color c){
        this.min = min;
        this.max = max;
        //coordinates around the object
        originalMin = new Vector2f(min.x, min.y);
        originalMax = new Vector2f(max.x, max.y);
        //coordinates for where the box starts in the world
        xTranslation = startX;
        yTranslation = startY;
        objectColor = c;
    }

    //Constructor for a Circle
    public BoundingShape(float radius, float startX, float startY, Color c){
        this.radius = radius;
        point = new Vector2f(startX, startY);
        originalPoint = new Vector2f(startX, startY);
        objectColor = c;
    }

    //This is a special method specifically for changing hitboxes with an animation
    //If hitbox's don't need to be altered to match an animation because it already fits,
    //Then this method isn't needed
    public void alterShape(Vector2f min, Vector2f max){
        this.min = min;
        this.max = max;
        originalMin = new Vector2f(min.x, min.y);
        originalMax = new Vector2f(max.x, max.y);
    }

    //Does the same as the other alterShape but takes a square's points instead of a circle.
    public void alterShape(float radius, float startX, float startY){
        this.radius = radius;
        point = new Vector2f(startX, startY);
        originalPoint = new Vector2f(startX, startY);
    }

    //Update the world
    public void updateWorld(Matrix3x3f viewport){
        currentWorld = Matrix3x3f.identity();
        currentWorld = currentWorld.mul(Matrix3x3f.scale(scale.x, scale.y));
        currentWorld = currentWorld.mul(Matrix3x3f.rotate(rot));
        currentWorld = currentWorld.mul(Matrix3x3f.translate(xTranslation, yTranslation));
        this.viewport = viewport;
    }

    //Given a circle and a box, returns true if they collide
    public boolean intersectCircleAABB(Vector2f c, float r, Vector2f min, Vector2f max){
        float dx = 0.0f;
        if(c.x < min.x) dx = c.x - min.x;
        if(c.x > max.x) dx = c.x - max.x;

        float dy = 0.0f;
        if(c.y < min.y) dy = c.y - min.y;
        if(c.y > max.y) dy = c.y - max.y;

        float d = dx * dx + dy * dy;
        return d < r * r;
    }

    //Render the color, subclasses should implement rendering specifics
    public void render(Graphics g){
        g.setColor(objectColor);
    }

    //When the scale changes, the square and circle coordinates need to be altered accordingly
    //For example, a negative scale flips the picture, the points need to be flipped as well
    protected abstract void resetDimensions();/*
    private void resetDimensions(){
        if(this instanceof BoundingBox){
            if(negativeXScale){
                min.x = -originalMax.x;
                max.x = -originalMin.x;
            }
            else{
                min.x = originalMin.x;
                max.x = originalMax.x;
            }
            if(negativeYScale){
                min.y = -originalMax.y;
                max.y = -originalMin.y;
            }
            else{
                min.y = originalMin.y;
                max.y = originalMax.y;
            }
        }
        else if(this instanceof BoundingCircle){
            if(negativeXScale){
                point.x = -originalPoint.x;
            }
            else{
                point.x = originalPoint.x;
            }
            if(negativeYScale){
                point.y = -originalPoint.y;
            }
            else{
                point.y = originalPoint.y;
            }
        }
    }*/

    public Vector2f getCurrentMin(){
        return currentWorld.mul(min);
    }
    public Vector2f getCurrentMax(){
        return currentWorld.mul(max);
    }
    public Vector2f getCurrentPoint(){
        return currentWorld.mul(point);
    }
    public float getCurrentRadius(){
        return radius * scale.x;
    }

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
}
