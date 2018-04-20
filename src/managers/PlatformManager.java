package managers;

import spawning.SpawnRange;
import sprite.Sprite;
import sprite.world.Platform;
import util.Vector2f;
import util.Matrix3x3f;

import java.util.ArrayList;

public class PlatformManager extends Manager{
    public PlatformManager(){

    }

    public ArrayList<SpawnRange> getPlatFormSpawns(Matrix3x3f viewport){
        ArrayList<SpawnRange> spawnRanges = new ArrayList<>();
        //Add floor spawn.
        spawnRanges.add(new SpawnRange(-5,7,-3,viewport));
        for(Sprite platform: getSprites()){
            spawnRanges.add(((Platform)platform).getSpawnRange(viewport));
        }
        return spawnRanges;
    }

}
