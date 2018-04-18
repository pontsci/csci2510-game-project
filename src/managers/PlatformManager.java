package managers;

import spawning.SpawnRange;
import sprite.Sprite;
import sprite.world.Platform;
import util.Vector2f;
import util.Matrix3x3f;

import java.util.ArrayList;

public class PlatformManager extends Manager{
    public PlatformManager(){
        getSprites().add(new Platform(6.5f, -1, new Vector2f(.75f,.5f)));
        getSprites().add(new Platform(3.5f, -1, new Vector2f(.75f, .5f)));
        getSprites().add(new Platform(5.5f, 1.5f, new Vector2f(.75f,.5f)));
        getSprites().add(new Platform(3.5f, 1.5f, new Vector2f(.75f, .5f)));
        getSprites().add(new Platform(-4, 1.5f, new Vector2f(.75f, .5f)));
    }

    public ArrayList<SpawnRange> getPlatFormSpawns(Matrix3x3f viewport){
        ArrayList<SpawnRange> spawnRanges = new ArrayList<>();
        for(Sprite platform: getSprites()){
            spawnRanges.add(((Platform)platform).getSpawnRange(viewport));
        }
        return spawnRanges;
    }
}
