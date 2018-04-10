package spawning;


import sprite.character.enemy.Rat;
import util.Vector2f;

import java.util.Random;
import java.awt.*;
import java.util.ArrayList;

public class Spawner {
    // The first spawnRange will always be the player spawn.
    ArrayList<SpawnRange> spawnRanges= new ArrayList<SpawnRange>();
    private Random random;
    public Spawner(){
        random = new Random();
    }

    public void setSpawnRanges(ArrayList<SpawnRange> spawnRanges) {
        this.spawnRanges = spawnRanges;
    }

    public Vector2f getSpawnPoint(){
        return spawnRanges.get(random.nextInt(spawnRanges.size() - 1)+ 1).getSpawnPoint();
    }

    public Rat spawnRat(){
        Vector2f spawnpoint = getSpawnPoint();
        //Temporary null
        return null;
    }



    public void render(Graphics g){
        for (SpawnRange spawn: spawnRanges){
            spawn.render(g);
        }
    }

}
