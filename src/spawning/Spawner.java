package spawning;


import managers.Manager;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.Random;
import java.awt.*;
import java.util.ArrayList;

public class Spawner extends Manager
{
    // The first spawnRange will always be the player spawn.
    ArrayList<SpawnRange> spawnRanges= new ArrayList<SpawnRange>();
    private Random random;
    public void initialize(){
        random = new Random();
    }

    public void setSpawnRanges(ArrayList<SpawnRange> spawnRanges) {
        this.spawnRanges = spawnRanges;
    }

    public Vector2f getSpawnPoint(){
        return spawnRanges.get(random.nextInt(spawnRanges.size())).getSpawnPoint();
    }

    //technically a SpawnRange, not a hitbox
    public void renderHitboxes(Graphics g){
        for (SpawnRange spawn: spawnRanges){
            spawn.render(g);
        }
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        //not needed
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){

    }
}
