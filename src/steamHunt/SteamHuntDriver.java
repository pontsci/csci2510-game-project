package steamHunt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import managers.*;
import spawning.SpawnRange;
import spawning.Spawner;
import sprite.character.player.MainCharacter;
import sprite.world.Floor;
import status.StatusArchive;
import util.SimpleFramework;
import util.Vector2f;

//The driver's job is to direct information between managers.
//It does not deal with individual sprites, that is footBox for the manager to do.
public class SteamHuntDriver extends SimpleFramework{
    private Manager[] managers = new Manager[10];
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
        //The index of these is display ordering: For example
        //In the very back is the background, ontop of that is the floor and platforms
        //After that is the the walls... and so on so forth.
        managers[0] = new BackgroundManager();
        managers[1] = new FloorManager();
        managers[2] = new PlatformManager();
        managers[5] = new WallManager();
        managers[6] = new PowerUpManager();
        managers[8] = new Spawner();

        //Add six power up items
        ((PowerUpManager)managers[6]).addPowerUp(StatusArchive.getHealthStatus(), new Vector2f(-6,0));
        ((PowerUpManager)managers[6]).addPowerUp(StatusArchive.getFireRateStatus(), new Vector2f(-4,0));
        ((PowerUpManager)managers[6]).addPowerUp(StatusArchive.getDmgStatus(), new Vector2f(-2,0));
        ((PowerUpManager)managers[6]).addPowerUp(StatusArchive.getShieldStatus(), new Vector2f(2,0));
        ((PowerUpManager)managers[6]).addPowerUp(StatusArchive.getTaserStatus(), new Vector2f(4,0));
        ((PowerUpManager)managers[6]).addPowerUp(StatusArchive.getDoTStatus(), new Vector2f(6,0));

        //temporary testing of the spawn range
        SpawnRange sp = new SpawnRange(-4, -2, 0, getViewportTransform());
        ArrayList<SpawnRange> spawnRanges = new ArrayList<>();
        spawnRanges.add(sp);
        ((Spawner)managers[8]).setSpawnRanges(spawnRanges);

        managers[4] = new RatManager((Floor)managers[1].getSprites().get(0), managers[5].getSprites(), managers[2].getSprites());
        managers[3] = new MainCharacterManager((Floor)managers[1].getSprites().get(0), managers[5].getSprites(), managers[4].getSprites(), managers[6].getSprites(), managers[2].getSprites());
        managers[7] = new EnemyManager((Floor)managers[1].getSprites().get(0), managers[5].getSprites(), managers[2].getSprites(), (MainCharacter) managers[3].getSprites().get(0));
        managers[9] = new BulletManager((MainCharacter)managers[3].getSprites().get(0), managers[7].getSprites());
        ((MainCharacterManager)managers[3]).setBulletManager((BulletManager)managers[9]);
    }

    @Override
    //Process everything, key input, managers, sprites, hitboxes...
    protected void processInput(float delta){
        super.processInput(delta);
        processSpaceKeyInput();
        processMovementInput(delta);
        processBKeyInput();
        processSKeyInput();
        processJKeyInput();
        for(Manager manager : managers){
            manager.process(delta);
        }
    }

    //Process what happens when space is pressed
    private void processSpaceKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE)){
            ((MainCharacterManager)managers[3]).processJump();
        }
    }

    //Process what happens when A or/and D are pressed
    private void processMovementInput(float delta){
        //if both are pressed, do nothing
        if(keyboard.keyDown(KeyEvent.VK_A) && keyboard.keyDown(KeyEvent.VK_D)){
            ((MainCharacterManager)managers[3]).processIdle();
        }
        else if(keyboard.keyDown(KeyEvent.VK_A)){
            ((MainCharacterManager)managers[3]).processWalkLeft(delta);
        }
        else if(keyboard.keyDown(KeyEvent.VK_D)){
            ((MainCharacterManager)managers[3]).processWalkRight(delta);
        }
        else{
            ((MainCharacterManager)managers[3]).processIdle();
        }
    }

    //Process what happens when B is pressed
    private void processBKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_B)){
            renderHitboxes = !renderHitboxes;
        }
    }

    //Process what happens when S is pressed
    private void processSKeyInput(){
        if(keyboard.keyDown(KeyEvent.VK_S)){
            ((MainCharacterManager)managers[3]).processIgnorePlatformCollision();
        }
        else
            ((MainCharacterManager)managers[3]).processAllowPlatformCollision();
    }

    //Process what happens when S is pressed
    private void processJKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_J)){
            ((MainCharacterManager)managers[3]).processShoot();
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

    //Check sprite collision
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

