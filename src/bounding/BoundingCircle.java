package bounding;

import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;

public class BoundingCircle extends BoundingShape{

    public BoundingCircle(float radius, float startX, float startY, Color c){
        //coordinates for where the box starts in the world
        super(c);
        this.radius = radius;
        point = new Vector2f(startX, startY);
        originalPoint = new Vector2f(startX, startY);
    }

    //Render where the bounding circle is in the world
    @Override
    public void render(Graphics g){
        super.render(g);
        Matrix3x3f view = viewport;

        Vector2f topLeft = new Vector2f(point.x - radius, point.y + radius);
        topLeft = currentWorld.mul(topLeft);
        topLeft = view.mul(topLeft);

        Vector2f bottomRight = new Vector2f(point.x + radius, point.y - radius);
        bottomRight = currentWorld.mul(bottomRight);
        bottomRight = view.mul(bottomRight);

        int circleX = (int)topLeft.x;
        int circleY = (int)topLeft.y;
        int circleWidth = (int)(bottomRight.x - topLeft.x);
        int circleHeight = (int)(bottomRight.y - topLeft.y);

        g.drawOval(circleX, circleY, circleWidth, circleHeight);

    }

    @Override
    protected void resetDimensions() {
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

    //Does the same as the other alterShape but takes a square's points instead of a circle.
    public void alterShape(float radius, float startX, float startY){
        this.radius = radius;
        point = new Vector2f(startX, startY);
        originalPoint = new Vector2f(startX, startY);
    }
}
