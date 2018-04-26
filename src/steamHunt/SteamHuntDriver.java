package steamHunt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import managers.*;
import spawning.Spawner;
import sprite.character.player.MainCharacter;
import util.SimpleFramework;
import managers.ScreenManager.ScreenType;


//The driver's job is to direct information between managers.
//It does not deal with individual sprites, that is footBox for the manager to do.
public class SteamHuntDriver extends SimpleFramework{
    private Manager[] managers = new Manager[12];
    private BackgroundManager backgroundManager;
    private FloorManager floorManager;
    private PlatformManager platformManager;
    private ScreenWallManager screenWallManager;
    private WallManager wallManager;
    private PowerUpManager powerUpManager;
    private MainCharacterManager mainCharManager;
    private Spawner spawner;
    private BulletManager bulletManager;
    private EnemyManager enemyManager;
    private ScreenManager screenManager;
    private DoorManager doorManager;
    private boolean paused = true;
    private boolean hasStarted = false;
    private boolean renderHitboxes = false;
    private int level = 2;


    private SteamHuntDriver(){
        appBackground = Color.BLACK;
        appBorder = Color.BLACK;
        appWidth = 1920;
        appHeight = 1080;
        appMaintainRatio = true;
        appSleep = 3L;
        appTitle = "SteamHunt";
        appWorldWidth = 16f;
        appWorldHeight = 9f;
    }

    private enum ManagerType{
        BACKGROUND(0), FLOOR(1), WALL(2), PLATFORM(3), DOOR(4), SCREENWALL(5), MAINCHAR(6), POWERUP(7), ENEMY(8), SPAWNER(9), BULLET(10), SCREEN(11);
        private int i;
        ManagerType(int i){
            this.i = i;
        }
    }

    //easier enum usage declarations
    private final ManagerType BACKGROUND = ManagerType.BACKGROUND;
    private final ManagerType FLOOR = ManagerType.FLOOR;
    private final ManagerType DOOR = ManagerType.DOOR;
    private final ManagerType PLATFORM = ManagerType.PLATFORM;
    private final ManagerType SCREENWALL = ManagerType.SCREENWALL;
    private final ManagerType WALL = ManagerType.WALL;
    private final ManagerType MAINCHAR = ManagerType.MAINCHAR;
    private final ManagerType POWERUP = ManagerType.POWERUP;
    private final ManagerType ENEMY = ManagerType.ENEMY;
    private final ManagerType SPAWNER = ManagerType.SPAWNER;
    private final ManagerType BULLET = ManagerType.BULLET;
    private final ManagerType SCREEN = ManagerType.SCREEN;

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
        managers[SCREENWALL.i] = new ScreenWallManager();
        managers[POWERUP.i] = new PowerUpManager();
        managers[SPAWNER.i] = new Spawner();
        managers[SCREEN.i] = new ScreenManager();
        managers[WALL.i] = new WallManager();

        /*
         * The following declarations are for ease of use whilst
         * maintaining the ability to loop through managers
        */

        //world managers
        screenManager = (ScreenManager) managers[SCREEN.i];
        backgroundManager = (BackgroundManager) managers[BACKGROUND.i];
        floorManager = (FloorManager)managers[FLOOR.i];
        platformManager = (PlatformManager)managers[PLATFORM.i];
        screenWallManager = (ScreenWallManager)managers[SCREENWALL.i];
        mainCharManager = (MainCharacterManager)managers[MAINCHAR.i];

        //spawning/item managers
        powerUpManager = (PowerUpManager)managers[POWERUP.i];
        spawner = (Spawner) managers[SPAWNER.i];

        //main character
        managers[MAINCHAR.i] = new MainCharacterManager(floorManager.getSprites(), screenWallManager.getSprites(), powerUpManager.getSprites(), platformManager.getSprites());
        mainCharManager = (MainCharacterManager)managers[MAINCHAR.i];
        MainCharacter player = (MainCharacter) mainCharManager.getSprites().get(0);

        //enemy
        managers[ENEMY.i] = new EnemyManager(floorManager.getSprites(), screenWallManager.getSprites(), platformManager.getSprites(), player);
        enemyManager = (EnemyManager) managers[ENEMY.i];

        //door
        managers[DOOR.i] = new DoorManager(enemyManager.getSprites(), (MainCharacter)mainCharManager.getSprites().get(0));
        doorManager = (DoorManager)managers[DOOR.i];

        //bullet
        managers[BULLET.i] = new BulletManager(player, enemyManager.getSprites());
        bulletManager = (BulletManager) managers[BULLET.i];
        mainCharManager.setBulletManager(bulletManager);

        //load level and spawner
        loadNewLevel();
    }

    @Override
    //Process everything, key input, managers, sprites, hitboxes...
    protected void processInput(float delta){
        super.processInput(delta);
        // Allow Pauses to happen through the P key
        processPKeyInput();
        
         //if we havent started we want to read the space input
        if(!hasStarted){
            processSpaceKeyInput();
        }
        // If we are paused we do not want to allow processing of normal "Play" buttons.
        else if (!isPaused()) { 
            processWKeyInput();
            processMovementInput(delta);
            processBKeyInput();
            processSKeyInput();
            processSpaceKeyInput();
            processTestLevelChange();

        }
        for(Manager manager : managers){
            manager.process(delta);
        }
    }

    // Process a P key as the pause
    private void processPKeyInput() {
        if(keyboard.keyDownOnce(KeyEvent.VK_P)){
            
            paused = !paused;
            
            if (paused) {
                screenManager.SetScreen(ScreenType.PAUSE);
            } else {
                if(!hasStarted){
                    screenManager.SetScreen(ScreenType.START);
                }else{
                    screenManager.SetScreen(ScreenType.NONE);
                }
            }
        }
    }
    
    //Process what happens when w is pressed
    private void processWKeyInput(){
        if(keyboard.keyDown(KeyEvent.VK_W)){
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

    //Process what happens when S is pressed
    private void processSpaceKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_SPACE)){
            if(!hasStarted){
                hasStarted = true;
                screenManager.SetScreen(ScreenType.NONE);
                paused = false;
            }else{
                ((MainCharacterManager)managers[MAINCHAR.i]).processShoot();
            }
        }
    }

    private void processTestLevelChange(){
        if(keyboard.keyDownOnce(KeyEvent.VK_Z)){
            level--;
            if(level < 1){
                level = 3;
            }
            loadNewLevel();
        }
        else if(keyboard.keyDownOnce(KeyEvent.VK_C)){
            level++;
            if(level > 3){
                level = 1;
            }
            loadNewLevel();
        }
    }

    private void loadNewLevel(){
        //create a new level.
        for(Manager manager : managers){
            manager.switchLevel(level, spawner, getViewportTransform());
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
        bulletManager.checkCollision(delta, getViewportTransform());
        mainCharManager.checkCollision(delta, getViewportTransform());
        enemyManager.checkCollision(delta,getViewportTransform());
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

    // Override the isPaused method in SimpleFramework
    @Override
    protected boolean isPaused(){
        return paused;
    }
            
    public static void main(String[] args){
        launchApp(new SteamHuntDriver());
    }
}

