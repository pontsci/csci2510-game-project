package bounding;

import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;

public class BoundingBox extends BoundingShape{
    public BoundingBox(Vector2f min, Vector2f max, float startX, float startY, Color c){
        super(min, max, startX, startY, c);
    }

    //Unused code from book - May be needed for something later
    public boolean pointInAABB(Vector2f p, Vector2f min, Vector2f max){
        return p.x > min.x && p.x < max.x && p.y > min.y && p.y < max.y;
    }

    //Given two boxes, returns if they collide
    public boolean intersectAABB(Vector2f minA, Vector2f maxA, Vector2f minB, Vector2f maxB){
        if(minA.x > maxB.x || minB.x > maxA.x)
            return false;
        if(minA.y > maxB.y || minB.y > maxA.y)
            return false;
        return true;
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
}