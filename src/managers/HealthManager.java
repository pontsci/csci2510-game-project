package managers;

import spawning.Spawner;
import sprite.character.player.MainCharacter;
import sprite.world.Health;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;

//This is used for the player right now
public class HealthManager extends Manager{
    private BufferedImage [] healthSprites;
    private MainCharacter player;
    private float cumulativeTime;
    private int hpShowing;

    /**
     * The constructor. Loads the health resources and splits them into an array
     */
    public HealthManager(){
        super();
        BufferedImage sheet = loadFile("src/resources/UI/UIElement_WH_131x203_Battery.png");
        healthSprites = new BufferedImage [3];
        for(int i =0; i<healthSprites.length;i++){
            healthSprites[i] = sheet.getSubimage(131*i, 0, 131, 203);
        }
    }

    /**
     * initialize variables to passed parameters, as well as add the health sprites to sprites
     * @param player the player
     */
    public void initialize(MainCharacter player){
        this.player = player;
        getSprites().add(new Health(6,4, new Vector2f(.5f, .5f), healthSprites[0]));
        getSprites().add(new Health(6.5f,4, new Vector2f(.5f, .5f), healthSprites[0]));
        getSprites().add(new Health(7,4, new Vector2f(.5f, .5f), healthSprites[0]));
        hpShowing = 3;
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport){
        //Not needed
    }

    /**
     * process how health UI is displayed
     * @param delta time
     */
    @Override
    public void process(float delta){
        super.process(delta);
        cumulativeTime += delta;
        //does the HP bar need to be updated?
        if (player.getHp() != hpShowing){
            int empty = getSprites().size() - player.getHp();
            for(int i=0; i < getSprites().size(); i++){
                if(empty > i){
                    getSprites().get(i).setCurrentSpriteFrame(healthSprites[1]);
                }else{
                    getSprites().get(i).setCurrentSpriteFrame(healthSprites[0]);
                }
            }
            hpShowing = player.getHp();
        }
        
        if (hpShowing == 0){
            //flicker
            if(cumulativeTime > 1){
                
                getSprites().get(0).setCurrentSpriteFrame(healthSprites[2]);
                getSprites().get(1).setCurrentSpriteFrame(healthSprites[2]);
                getSprites().get(2).setCurrentSpriteFrame(healthSprites[2]);
            }else{
                getSprites().get(0).setCurrentSpriteFrame(healthSprites[1]);
                getSprites().get(1).setCurrentSpriteFrame(healthSprites[1]);
                getSprites().get(2).setCurrentSpriteFrame(healthSprites[1]);
            }
            
        }
        if (cumulativeTime > 2){
            cumulativeTime = 0;
        }
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        //not needed
    }
}
