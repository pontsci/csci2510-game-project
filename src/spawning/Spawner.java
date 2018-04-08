package spawning;

import sprite.*;

import java.util.ArrayList;

public class Spawner {
    ArrayList<SpawnRange> spawnRanges= new ArrayList<SpawnRange>();

    public Spawner(){

    }

    public void setSpawnRanges(ArrayList<SpawnRange> spawnRanges) {
        this.spawnRanges = spawnRanges;
    }


}
