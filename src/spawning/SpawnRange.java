package spawning;

import util.Vector2f;

import java.util.Random;

public class SpawnRange {
    private float minx;
    private float maxx;
    private float y;

    public SpawnRange(float minx, float maxx, float y){
        this.minx = minx;
        this.maxx = maxx;
        this.y = y;
    }

    public SpawnRange(float x, float y){
        this.minx = x;
        this.maxx = x;
        this.y = y;
    }

    /* Returns a random vector location from the spawn range. */
    public Vector2f getSpawnPoint(){
        Random random = new Random();
        return new Vector2f(minx + random.nextFloat() * (maxx - minx),y);
    }
}
