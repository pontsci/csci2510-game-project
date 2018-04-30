package managers;

import spawning.Spawner;
import sprite.world.Background;
import sprite.world.BackgroundWall;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;

//Could be used to handle which background to play on each level of the game
public class BackgroundManager extends Manager{
    private BufferedImage skyboxSpriteSheet = loadFile("src/resources/world/background/skyboxresized.png");
    private BufferedImage bgWallSpriteSheet = loadFile("src/resources/world/background/Building_WH_400x467_Wall.png");

    public enum BgWallType {
        WINDOWCRACKED(0), WINDOW(1), DIAGONAL(2), MEDIUM (3), BIGHOLE(4), LITTLEHOLE (5), SMALLSEGMENT(6), WALL(7), SCRATCHED(8);  
        private int i;
        BgWallType(int i) {
            this.i = i;
        }
        public int getIndex() {
            return i;
        }
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        //no collision needed
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        getSprites().add(new Background(0, 0, new Vector2f(1,1), skyboxSpriteSheet));
        switch(level){
            case 1:
                 getSprites().add(new BackgroundWall(7, -3, new Vector2f(.5f,.57f), BgWallType.WALL.getIndex(), bgWallSpriteSheet));
                break;
            case 2:
                
                
                break;
            case 3:
                
                break;
        }
    }
}
