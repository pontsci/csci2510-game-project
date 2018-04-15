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
    private Manager[] managers = new Manager[9];
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

    private enum ManagerType{
        BACKGROUND(0), FLOOR(1), PLATFORM(2), MAINCHAR(3), RAT(4), WALL(5), POWERUP(6), ENEMY(7), SPAWNER(8);

        private int i;
        ManagerType(int i){
            this.i = i;
        }
    }

    private final ManagerType BACKGROUND = ManagerType.BACKGROUND;
    private final ManagerType FLOOR = ManagerType.FLOOR;
    private final ManagerType PLATFORM = ManagerType.PLATFORM;
    private final ManagerType MAINCHAR = ManagerType.MAINCHAR;
    private final ManagerType RAT = ManagerType.RAT;
    private final ManagerType WALL = ManagerType.WALL;
    private final ManagerType POWERUP = ManagerType.POWERUP;
    private final ManagerType ENEMY = ManagerType.ENEMY;
    private final ManagerType SPAWNER = ManagerType.SPAWNER;

    @Override
    //Initialize the sprites each manager starts with
    protected void initialize(){
        super.initialize();
        //The i of these is display ordering: For example
        //In the very back is the background, ontop of that is the floor and platforms
        //After that is the the walls... and so on so forth.
        managers[BACKGROUND.i] = new BackgroundManager();
        managers[FLOOR.i] = new FloorManager();
        managers[PLATFORM.i] = new PlatformManager();
        managers[WALL.i] = new WallManager();
        managers[POWERUP.i] = new PowerUpManager();
        managers[SPAWNER.i] = new Spawner();

        //Add six power up items
        ((PowerUpManager)managers[POWERUP.i]).addPowerUp(StatusArchive.getHealthStatus(), new Vector2f(-6,0));
        ((PowerUpManager)managers[POWERUP.i]).addPowerUp(StatusArchive.getFireRateStatus(), new Vector2f(-4,0));
        ((PowerUpManager)managers[POWERUP.i]).addPowerUp(StatusArchive.getDmgStatus(), new Vector2f(-2,0));
        ((PowerUpManager)managers[POWERUP.i]).addPowerUp(StatusArchive.getShieldStatus(), new Vector2f(2,0));
        ((PowerUpManager)managers[POWERUP.i]).addPowerUp(StatusArchive.getTaserStatus(), new Vector2f(4,0));
        ((PowerUpManager)managers[POWERUP.i]).addPowerUp(StatusArchive.getDoTStatus(), new Vector2f(6,0));

        //temporary testing of the spawn range
        SpawnRange sp = new SpawnRange(-4, -2, 0, getViewportTransform());
        ArrayList<SpawnRange> spawnRanges = new ArrayList<>();
        spawnRanges.add(sp);
        ((Spawner)managers[SPAWNER.i]).setSpawnRanges(spawnRanges);

        Floor floor = (Floor)managers[FLOOR.i].getSprites().get(0);
        managers[RAT.i] = new RatManager(floor, managers[WALL.i].getSprites(), managers[PLATFORM.i].getSprites());
        managers[MAINCHAR.i] = new MainCharacterManager(floor, managers[WALL.i].getSprites(), managers[RAT.i].getSprites(), managers[POWERUP.i].getSprites(), managers[PLATFORM.i].getSprites());
        managers[ENEMY.i] = new EnemyManager(floor, managers[WALL.i].getSprites(), managers[PLATFORM.i].getSprites(), (MainCharacter) managers[MAINCHAR.i].getSprites().get(0));
    }

    @Override
    //Process everything, key input, managers, sprites, hitboxes...
    protected void processInput(float delta){
        super.processInput(delta);
        processSpaceKeyInput();
        processMovementInput(delta);
        processBKeyInput();
        processSKeyInput();
        for(Manager manager : managers){
            manager.process(delta);
        }
    }

    //Process what happens when space is pressed
    private void processSpaceKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE)){
            ((MainCharacterManager)managers[MAINCHAR.i]).processJump();
        }
    }

    //Process what happens when A or/and D are pressed
    private void processMovementInput(float delta){
        //if both are pressed, do nothing
        if(keyboard.keyDown(KeyEvent.VK_A) && keyboard.keyDown(KeyEvent.VK_D)){
            ((MainCharacterManager)managers[MAINCHAR.i]).processIdle();
        }
        else if(keyboard.keyDown(KeyEvent.VK_A)){
            ((MainCharacterManager)managers[MAINCHAR.i]).processWalkLeft(delta);
        }
        else if(keyboard.keyDown(KeyEvent.VK_D)){
            ((MainCharacterManager)managers[MAINCHAR.i]).processWalkRight(delta);
        }
        else{
            ((MainCharacterManager)managers[MAINCHAR.i]).processIdle();
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
            ((MainCharacterManager)managers[MAINCHAR.i]).processIgnorePlatformCollision();
        }
        else
            ((MainCharacterManager)managers[MAINCHAR.i]).processAllowPlatformCollision();
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

