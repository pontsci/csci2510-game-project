package bounding;

import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class BoundingBox extends BoundingShape{
    private Vector2f min;
    private Vector2f max;
    private Vector2f originalMin;//this will only change if the entire shape of the object changes
    private Vector2f originalMax;//this will only change if the entire shape of the object changes

    //Constructor for a Box
    public BoundingBox(Vector2f min, Vector2f max, Color c){
        //coordinates for where the box starts in the world
        super(c);
        this.min = min;
        this.max = max;
        //coordinates around the object
        originalMin = new Vector2f(min.x, min.y);
        originalMax = new Vector2f(max.x, max.y);
    }

    //Render where the bounding box is in the world
    @Override
    public void render(Graphics g){
        super.render(g);

        Matrix3x3f view = viewport;

        Vector2f topLeft = new Vector2f(min.x, max.y);
        topLeft = currentWorld.mul(topLeft);
        topLeft = view.mul(topLeft);

        Vector2f bottomRight = new Vector2f(max.x, min.y);
        bottomRight = currentWorld.mul(bottomRight);
        bottomRight = view.mul(bottomRight);

        int rectX = (int)topLeft.x;
        int rectY = (int)topLeft.y;

        int rectWidth = (int)(bottomRight.x - topLeft.x);
        int rectHeight = (int)(bottomRight.y - topLeft.y);
        g.drawRect(rectX, rectY, rectWidth, rectHeight);
    }

    @Override
    protected void resetDimensions(){
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

    //This is a special method specifically for changing hitboxes with an animation
    //If hitbox's don't need to be altered to match an animation because it already fits,
    //Then this method isn't needed
    public void alterShape(Vector2f min, Vector2f max){
        this.min = min;
        this.max = max;
        originalMin = new Vector2f(min.x, min.y);
        originalMax = new Vector2f(max.x, max.y);
    }
    public Vector2f getCurrentMin(){
        return currentWorld.mul(min);
    }
    public Vector2f getCurrentMax(){
        return currentWorld.mul(max);
    }

    public List<Vector2f> getUpperLine(){
        Vector2f min = getCurrentMin();
        Vector2f max = getCurrentMax();
        return Arrays.asList(new Vector2f(min.x, max.y), max);
    }

    public List<Vector2f> getLowerLine(){
        Vector2f min = getCurrentMin();
        Vector2f max = getCurrentMax();
        return Arrays.asList(min, new Vector2f(max.x, min.y));
    }

    public List<Vector2f> getLeftLine(){
        Vector2f min = getCurrentMin();
        Vector2f max = getCurrentMax();
        return Arrays.asList(min, new Vector2f(min.x, max.y));
    }

    public List<Vector2f> getRightLine(){
        Vector2f min = getCurrentMin();
        Vector2f max = getCurrentMax();
        return Arrays.asList(new Vector2f(max.x, min.y), max);
    }
}