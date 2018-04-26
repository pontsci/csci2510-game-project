package managers;

import spawning.SpawnRange;
import spawning.Spawner;
import sprite.Sprite;
import sprite.world.Platform;
import util.Matrix3x3f;
import util.Vector2f;

import java.util.ArrayList;

public class PlatformManager extends Manager{
    public void initialize(){

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

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        //not needed
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 1:
                getSprites().add(new Platform(6.5f, -1.5f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(6.5f, -1.5f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(3.5f, -1.5f, new Vector2f(.75f, .5f)));
                getSprites().add(new Platform(5.5f, 1.0f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(3.5f, 1.0f, new Vector2f(.75f, .5f)));
                getSprites().add(new Platform(-4, 1.0f, new Vector2f(.75f, .5f)));
            break;
            case 2:
                getSprites().add(new Platform(0.0f, -1.5f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(0.0f, 1.0f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(6.5f, -1.5f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(-6.5f, -1.5f, new Vector2f(.75f,.5f)));
            break;
            case 3:
                getSprites().add(new Platform(-6.5f, -1.5f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(-3.25f, -1.5f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(0.0f, -1.5f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(3.25f, -1.5f, new Vector2f(.75f,.5f)));
                getSprites().add(new Platform(6.5f, -1.5f, new Vector2f(.75f,.5f)));
            break;
        }
        spawner.setSpawnRanges(getPlatFormSpawns(viewport));
    }
}
