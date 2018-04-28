package util;

public class Intersect {
    //Given two boxes, returns if they collide
    public static boolean intersectAABB(Vector2f minA, Vector2f maxA, Vector2f minB, Vector2f maxB){
        if(minA.x > maxB.x || minB.x > maxA.x)
            return false;
        if(minA.y > maxB.y || minB.y > maxA.y)
            return false;
        return true;
    }

    //Given two circles, return true if they collide
    public static boolean intersectCircle(Vector2f c0, float r0, Vector2f c1, float r1){
        Vector2f c = c0.sub(c1);
        float r = r0 + r1;
        return c.lenSqr() < r*r;
    }

    //Given a circle and a box, returns true if they collide
    public static boolean intersectCircleAABB(Vector2f c, float r, Vector2f min, Vector2f max){
        float dx = 0.0f;
        if(c.x < min.x) dx = c.x - min.x;
        if(c.x > max.x) dx = c.x - max.x;

        float dy = 0.0f;
        if(c.y < min.y) dy = c.y - min.y;
        if(c.y > max.y) dy = c.y - max.y;

        float d = dx * dx + dy * dy;
        return d < r * r;
    }

    public static boolean lineIntersect(Vector2f origin, Vector2f destination, Vector2f boxPointA, Vector2f boxPointB)
    {
        float denominator = ((destination.x - origin.x) * (boxPointB.y - boxPointA.y)) - ((destination.y - origin.y) * (boxPointB.x - boxPointA.x));
        float numerator1 = ((origin.y - boxPointA.y) * (boxPointB.x - boxPointA.x)) - ((origin.x - boxPointA.x) * (boxPointB.y - boxPointA.y));
        float numerator2 = ((origin.y - boxPointA.y) * (destination.x - origin.x)) - ((origin.x - boxPointA.x) * (destination.y - origin.y));

        // Detect coincident lines
        if (denominator == 0){
            return numerator1 == 0 && numerator2 == 0;
        }

        float r = numerator1 / denominator;
        float s = numerator2 / denominator;

        return (r >= 0 && r <= 1) && (s >= 0 && s <= 1);
    }

    //Unused code from book - May be needed for something later
    public static boolean pointInAABB(Vector2f p, Vector2f min, Vector2f max){
        return p.x > min.x && p.x < max.x && p.y > min.y && p.y < max.y;
    }

    //Unused code from book
    public static boolean pointInCircle(Vector2f p, Vector2f c, float r){
        Vector2f dist = p.sub(c);
        return dist.lenSqr() < r*r;
    }
}
