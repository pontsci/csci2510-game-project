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
    private BufferedImage [] bgWallSprites;

    /**
     * BackgroundManager constructor. Loads images and breaks them into easy to use tiles
     */
    public BackgroundManager(){
        super();
        BufferedImage bgWallSpriteSheet = loadFile("src/resources/world/background/Building_WH_400x467_Wall.png");
        bgWallSprites = new BufferedImage [9];
        for(int i=0; i< bgWallSprites.length; i++){
            bgWallSprites[i] = bgWallSpriteSheet.getSubimage(400*i, 0 , 400, 467);
        }
    }

    /**
     * an enum for background types
     */
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

    /**
     * Depending on the level, switch the backgrounds we are using
     * @param level the current level
     * @param spawner the spawn system
     * @param viewport the viewport
     */
    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        getSprites().clear();
        getSprites().add(new Background(0, 0, new Vector2f(1,1), skyboxSpriteSheet));
        //you can line up background walls horizontally by having a 2.37 distance between their x positions
        /*
                 getSprites().add(new BackgroundWall(7, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 */
        switch(level){
            case 1:
                //first story
                 getSprites().add(new BackgroundWall(7, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.LITTLEHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOW.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-6.7f, -3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));

                 //second story
                 getSprites().add(new BackgroundWall(7, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-1.5f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(.75f, 0, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 
                 //third story
                 getSprites().add(new BackgroundWall(7, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));

                break;
            case 2:
                //first story
                 getSprites().add(new BackgroundWall(4.5f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(6.63f, -3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.LITTLEHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 
                 //second story
                 getSprites().add(new BackgroundWall(4.63f, 0, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOWCRACKED.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.BIGHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 
                 //third story
                 getSprites().add(new BackgroundWall(-.11f, 3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                break;
            case 3:
                //FIRST
                 getSprites().add(new BackgroundWall(7, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOW.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.BIGHOLE.getIndex()]));
                 
                //SECOND
                 getSprites().add(new BackgroundWall(7, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.LITTLEHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 0, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                
                //THIRD
                 getSprites().add(new BackgroundWall(7, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.MEDIUM.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOWCRACKED.getIndex()]));
                break;
                
            case 4:
                //FIRST
                 
                 getSprites().add(new BackgroundWall(2.26f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOW.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.BIGHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                
                 
                //SECOND
                 getSprites().add(new BackgroundWall(7, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, 0, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.LITTLEHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 0, new Vector2f(.55f,-.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 
                //THIRD
                 getSprites().add(new BackgroundWall(2.26f, 3, new Vector2f(-.55f,-.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOWCRACKED.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.MEDIUM.getIndex()]));
                break;
            case 5:
                //FIRST
                 getSprites().add(new BackgroundWall(7, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOW.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, -3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 
                //SECOND
                 getSprites().add(new BackgroundWall(7, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.LITTLEHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOW.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 0, new Vector2f(.55f,-.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 0, new Vector2f(-.55f,-.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, 0, new Vector2f(.55f,-.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                
                //THIRD
                 getSprites().add(new BackgroundWall(7, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.BIGHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOW.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.BIGHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOWCRACKED.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, 3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 break; 
            case 6:
                //FIRST
                 getSprites().add(new BackgroundWall(7, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.MEDIUM.getIndex()]));
                 
                 getSprites().add(new BackgroundWall(-4.85f, -3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 
                //SECOND
                 getSprites().add(new BackgroundWall(7, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WINDOWCRACKED.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.BIGHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 0, new Vector2f(.55f,-.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 0, new Vector2f(-.55f,-.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                
                //THIRD
                 getSprites().add(new BackgroundWall(7, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(4.63f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 getSprites().add(new BackgroundWall(2.26f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.LITTLEHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.MEDIUM.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 3, new Vector2f(-.55f,-.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 break;
            case 7:
                //FIRST
                 getSprites().add(new BackgroundWall(-2.48f, -3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, -3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.LITTLEHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, -3, new Vector2f(.55f,-.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 
                //SECOND
                 getSprites().add(new BackgroundWall(7, 0, new Vector2f(.55f,-.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 0, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.SMALLSEGMENT.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.BIGHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 0, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.SCRATCHED.getIndex()]));
                 getSprites().add(new BackgroundWall(-7.22f, 0, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.MEDIUM.getIndex()]));
                
                //THIRD
                 getSprites().add(new BackgroundWall(7, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.MEDIUM.getIndex()]));
                 getSprites().add(new BackgroundWall(-.11f, 3, new Vector2f(-.55f,.6f), bgWallSprites[BgWallType.MEDIUM.getIndex()]));
                 getSprites().add(new BackgroundWall(-2.48f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.LITTLEHOLE.getIndex()]));
                 getSprites().add(new BackgroundWall(-4.85f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.DIAGONAL.getIndex()]));
                // getSprites().add(new BackgroundWall(-7.22f, 3, new Vector2f(.55f,.6f), bgWallSprites[BgWallType.WALL.getIndex()]));
                 break;
        }
    }
}
