package steamHunt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import managers.BackgroundManager;
import managers.FloorManager;
import managers.MainCharacterManager;
import managers.Manager;
import managers.PowerUpManager;
import managers.RatManager;
import managers.WallManager;
import sprite.world.Floor;
import status.StatusArchive;
import util.SimpleFramework;
import util.Vector2f;

//The driver's job is to direct information between managers.
//It does not deal with individual sprites, that is left for the manager to do.
public class SteamHuntDriver extends SimpleFramework{
    private Manager[] managers = new Manager[6];
    private boolean renderHitboxes = false;


    public SteamHuntDriver(){
        appBackground = Color.BLACK;
        appBorder = Color.BLACK;
        appWidth = 1920;
        appHeight = 1080;
        appMaintainRatio = true;
        appSleep = 10L;
        appTitle = "SteamHunt";
        appWorldWidth = 16f;
        appWorldHeight = 9f;
    }

    @Override
    //Initialize the sprites each manager starts with
    protected void initialize(){
        super.initialize();
        managers[0] = new BackgroundManager();
        managers[1] = new FloorManager();
        managers[4] = new WallManager();
        managers[5] = new PowerUpManager();
        
        //Add six power up items
        ((PowerUpManager)managers[5]).addPowerUp(StatusArchive.getHealthStatus(), new Vector2f(-6,0));
        ((PowerUpManager)managers[5]).addPowerUp(StatusArchive.getFireRateStatus(), new Vector2f(-4,0));
        ((PowerUpManager)managers[5]).addPowerUp(StatusArchive.getDmgStatus(), new Vector2f(-2,0));
        ((PowerUpManager)managers[5]).addPowerUp(StatusArchive.getShieldStatus(), new Vector2f(2,0));
        ((PowerUpManager)managers[5]).addPowerUp(StatusArchive.getTaserStatus(), new Vector2f(4,0));
        ((PowerUpManager)managers[5]).addPowerUp(StatusArchive.getDoTStatus(), new Vector2f(6,0));
        
        managers[3] = new RatManager((Floor)managers[1].getSprites().get(0), managers[4].getSprites());
        managers[2] = new MainCharacterManager((Floor)managers[1].getSprites().get(0), managers[4].getSprites(), managers[3].getSprites(), managers[5].getSprites());
    }

    @Override
    //Process everything, key input, managers, sprites, hitboxes...
    protected void processInput(float delta){
        super.processInput(delta);
        processSpaceKeyInput();
        processMovementInput(delta);
        processBKeyInput();
        for(Manager manager : managers){
            manager.process(delta);
        }
    }

    //Process what happens when space is pressed
    private void processSpaceKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE)){
            ((MainCharacterManager)managers[2]).processJump();
        }
    }

    //Process what happens when A or/and D are pressed
    private void processMovementInput(float delta){
        //if both are pressed, do nothing
        if(keyboard.keyDown(KeyEvent.VK_A) && keyboard.keyDown(KeyEvent.VK_D)){
            ((MainCharacterManager)managers[2]).processIdle();
        }
        else if(keyboard.keyDown(KeyEvent.VK_A)){
            ((MainCharacterManager)managers[2]).processWalkLeft(delta);
        }
        else if(keyboard.keyDown(KeyEvent.VK_D)){
            ((MainCharacterManager)managers[2]).processWalkRight(delta);
        }
        else{
            ((MainCharacterManager)managers[2]).processIdle();
        }
    }

    //Process what happens when B is pressed
    private void processBKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_B)){
            renderHitboxes = !renderHitboxes;
        }
    }

    @Override
    //Update where everything supposed to be located in the world
    protected void updateObjects(float delta){
        for(Manager manager : managers){
            manager.update(delta, getViewportTransform());
        }
        checkCollision(delta);
    }

    //Check the collision of everything that needs collision detection - Rat's and Cats
    private void checkCollision(float delta){
        for(Manager manager : managers)
            manager.checkCollision(delta, getViewportTransform());
    }

    @Override
    //Render everything's location in the world
    protected void render(Graphics g){
        super.render(g);
        for(Manager manager : managers){
            manager.render(g);
        }
        if(renderHitboxes){
            for(Manager manager : managers){
                manager.renderHitboxes(g);
            }
        }
    }

    @Override
    protected void terminate(){
        super.terminate();
    }

    public static void main(String[] args){
        launchApp(new SteamHuntDriver());
    }
}

