package managers;

import spawning.SpawnRange;
import spawning.Spawner;
import sprite.Sprite;
import sprite.world.Floor;
import sprite.world.Platform;
import sprite.world.PlayerFloor;
import sprite.world.PlayerPlatform;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlatformManager extends Manager{
    private BufferedImage platformSpriteSheet = loadFile("src/resources/world/foreground/platform/PlatformResized_WH_448x119.png");

    /**
     * generates the platform spawns
     * @param viewport the viewport
     * @return spawn ranges for the platforms
     */
    private ArrayList<SpawnRange> getPlatFormSpawns(Matrix3x3f viewport){
        ArrayList<SpawnRange> spawnRanges = new ArrayList<>();
        for(Sprite platform: getSprites()){
            if(!(platform instanceof PlayerPlatform) && !(platform instanceof PlayerFloor)) {
                spawnRanges.add(((Platform) platform).getSpawnRange(viewport));
            }
        }
        return spawnRanges;
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        //not needed
    }

    /**
     * Depending on the level, change where platforms spawn
     * @param level the current level
     * @param spawner the spawn system
     * @param viewport the viewport
     */
    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        switch(level){
            case 1:
                //floor
                getSprites().add(new PlayerFloor(-6.4f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(-3.2f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(0.0f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(3.2f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(6.4f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));

                getSprites().add(new Platform(-5.85f, -1.5f, new Vector2f(.75f, .5f), platformSpriteSheet));
                getSprites().add(new Platform(-2.75f, -1.5f, new Vector2f(.75f, .5f), platformSpriteSheet));
                getSprites().add(new Platform(-1.18f, 1.0f, new Vector2f(.75f, .5f), platformSpriteSheet));
                getSprites().add(new Platform(2.05f, 1.0f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(5.86f, -1.5f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(7.0f, 1.0f, new Vector2f(.75f, .5f), platformSpriteSheet));
            break;
            case 2:
                //floor
                getSprites().add(new PlayerFloor(-6.4f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(-3.2f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(0.0f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(3.2f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new PlayerFloor(6.4f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));

                getSprites().add(new Platform(-6.4f, 1.0f,  new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(-6.4f, -1.5f,  new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(-3.2f, 1.0f,  new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(-3.2f, -1.5f,  new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(0.0f,  1.0f,  new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(0.0f,  -1.5f,  new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(3.2f,  -1.5f,  new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(6.4f,  1.0f,  new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(6.4f,  -1.5f,  new Vector2f(.75f,.5f), platformSpriteSheet));
            break;
            case 3:
                //floor
                getSprites().add(new PlayerFloor(-6.4f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(-3.2f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(0.0f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(5.2f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(6.4f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));

                getSprites().add(new Platform(-6.5f, 1.0f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(-2.0f, -1.5f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(0.87f, -1.5f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(0.0f, 1.0f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(6.5f, 1.0f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(6.5f, -1.5f, new Vector2f(.75f,.5f), platformSpriteSheet));
            break;
            case 4:
                //floor
                getSprites().add(new Floor(-3.2f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(0.0f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));
                getSprites().add(new Floor(2.05f, -4.3f,  new Vector2f(.85f,.5f), platformSpriteSheet));

                getSprites().add(new PlayerPlatform(-7.5f, -1.5f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(-3.72f, 1.0f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(-0.0f, -1.5f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(-0.5f, 1.0f, new Vector2f(.75f,.5f), platformSpriteSheet));
                getSprites().add(new Platform(6.75f, -1.5f, new Vector2f(.75f,.5f), platformSpriteSheet));
                break;
        }
        spawner.setSpawnRanges(getPlatFormSpawns(viewport));
    }
}
