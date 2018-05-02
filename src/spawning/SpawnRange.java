package spawning;

import util.Drawable;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.util.Random;

public class SpawnRange implements Drawable
{
    private float minx;
    private float maxx;
    private float y;
    private Random random;
    private Matrix3x3f viewport;

    public SpawnRange(float minx, float maxx, float y, Matrix3x3f viewport){
        this.minx = minx;
        this.maxx = maxx;
        this.y = y;
        random = new Random();
        this.viewport = viewport;
    }

    /**
     * / SpawnRange for a point.
     * @param x x position
     * @param y y position
     * @param viewport the viewport
     */
    public SpawnRange(float x, float y, Matrix3x3f viewport){
        this(x,x,y,viewport);
    }

    /**
     * Returns a random vector location from the spawn range.
     * @return a vector location
     */
    public Vector2f getSpawnPoint(){

        return new Vector2f(minx + random.nextFloat() * (maxx - minx),y);
    }

    @Override
    public void updateWorld(Matrix3x3f viewport)
    {
        this.viewport = viewport;
    }

    /**
     * render the spawn range
     * @param g graphics
     */
    public void render(Graphics g){
        g.setColor(Color.CYAN);
        Vector2f min = new Vector2f(minx, y);
        Vector2f max = new Vector2f(maxx, y);
        Vector2f minScreen = viewport.mul(min);
        Vector2f maxScreen = viewport.mul(max);
        g.drawLine((int)minScreen.x, (int)minScreen.y, (int)maxScreen.x, (int)maxScreen.y);
    }
}
