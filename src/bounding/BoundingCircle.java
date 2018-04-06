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

    //Unused code from book
    public boolean pointInCircle(Vector2f p, Vector2f c, float r){
        Vector2f dist = p.sub(c);
        return dist.lenSqr() < r*r;
    }

    //Given two circles, return true if they collide
    public boolean intersectCircle(Vector2f c0, float r0, Vector2f c1, float r1){
        Vector2f c = c0.sub(c1);
        float r = r0 + r1;
        return c.lenSqr() < r*r;
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
}
