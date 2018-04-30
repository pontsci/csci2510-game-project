package managers;

import spawning.Spawner;
import sprite.character.player.MainCharacter;
import sprite.world.Health;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;

//This is used for the player right now
public class HealthManager extends Manager{
    private BufferedImage healthSpriteSheet = loadFile("src/resources/UI/UIElement_WH_131x203_Battery.png");
    private MainCharacter player;
    public void initialize(MainCharacter player){
        this.player = player;
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){
        //Not needed
    }

    @Override
    public void process(float delta){
        super.process(delta);
        switch(player.getHp()){
            case 1:
                if(getSprites().size() == 2){
                    getSprites().remove(1);
                }
                else if(getSprites().size() == 3){
                    getSprites().remove(2);
                    getSprites().remove(1);
                }
                if(getSprites().size() == 0)
                    getSprites().add(new Health(5,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(0,0,131,203)));
                break;
            case 2:
                if(getSprites().size() == 3)
                    getSprites().remove(2);
                else if(getSprites().size() == 1)
                    getSprites().add(new Health(6,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(131,0,131, 203)));
                break;
            case 3:
                if(getSprites().size() == 2){
                    getSprites().add(new Health(5,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(0,0,131,203)));
                }
                else if(getSprites().size() == 1){
                    getSprites().add(new Health(6,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(131,0,131, 203)));
                    getSprites().add(new Health(5,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(0,0,131,203)));
                }
                else if(getSprites().size() == 0){
                    getSprites().add(new Health(7,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(262,0,131, 203)));
                    getSprites().add(new Health(6,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(131,0,131, 203)));
                    getSprites().add(new Health(5,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(0,0,131,203)));
                }
                break;
        }
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        switch(level){
            case 1:
                getSprites().clear();
                getSprites().add(new Health(7,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(262,0,131, 203)));
                getSprites().add(new Health(6,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(131,0,131, 203)));
                getSprites().add(new Health(5,4, new Vector2f(.5f, .5f), healthSpriteSheet.getSubimage(0,0,131,203)));
                break;
        }
    }
}
