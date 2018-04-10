package spawning;

import util.Vector2f;

import java.awt.*;
import java.util.Random;

public class SpawnRange {
    private float minx;
    private float maxx;
    private float y;
    private Random random;

    public SpawnRange(float minx, float maxx, float y){
        this.minx = minx;
        this.maxx = maxx;
        this.y = y;
        random = new Random();
    }

    public SpawnRange(float x, float y){
        this.minx = x;
        this.maxx = x;
        this.y = y;
        random = new Random();
    }

    /* Returns a random vector location from the spawn range. */
    public Vector2f getSpawnPoint(){

        return new Vector2f(minx + random.nextFloat() * (maxx - minx),y);
    }

    public void render(Graphics g){
        g.drawLine((int)minx, (int)y, (int)maxx, (int)y);
    }

}
