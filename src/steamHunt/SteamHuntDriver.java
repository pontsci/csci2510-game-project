package steamHunt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import managers.*;
import sound.SteamSound;
import spawning.Spawner;
import sprite.Sprite;
import sprite.character.player.MainCharacter;
import sprite.world.Door;
import util.SimpleFramework;
import managers.ScreenManager.ScreenType;


//The driver's job is to direct information between managers.
//It does not deal with individual sprites, that is footBox for the manager to do.
public class SteamHuntDriver extends SimpleFramework{
    //Used for looping
    private Manager[] managers = new Manager[14];

    //Used for easy calling
    private BackgroundManager backgroundManager = new BackgroundManager();
    private WallManager wallManager = new WallManager();
    private FloorManager floorManager = new FloorManager();
    private PlatformManager platformManager = new PlatformManager();
    private DoorManager doorManager = new DoorManager();
    private LeverManager leverManager = new LeverManager();
    private ScreenWallManager screenWallManager= new ScreenWallManager();
    private MainCharacterManager mainCharManager = new MainCharacterManager();
    private PowerUpManager powerUpManager = new PowerUpManager();
    private EnemyManager enemyManager = new EnemyManager();
    private Spawner spawner = new Spawner();
    private BulletManager bulletManager = new BulletManager();
    private HealthManager healthManager = new HealthManager();
    private ScreenManager screenManager = new ScreenManager();

    //original scale
    private float origAppWidth;
    private float origAppHeight;

    //Screen state
    private boolean paused = true;
    private boolean hasStarted = false;

    //Hitbox rendering
    private boolean renderHitboxes = false;

    //Level
    private int level = 1;


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
        origAppHeight = appHeight;
        origAppWidth = appWidth;
    }

    private enum ManagerType{
        BACKGROUND(0), WALL(1), FLOOR(2), PLATFORM(3), DOOR(4), LEVER(5), SCREENWALL(6), MAINCHAR(7), POWERUP(8), ENEMY(9), SPAWNER(10), BULLET(11), HEALTH(12), SCREEN(13);
        private int i;
        ManagerType(int i){
            this.i = i;
        }
    }

    //easier enum usage declarations
    private final ManagerType BACKGROUND = ManagerType.BACKGROUND;
    private final ManagerType WALL = ManagerType.WALL;
    private final ManagerType FLOOR = ManagerType.FLOOR;
    private final ManagerType PLATFORM = ManagerType.PLATFORM;
    private final ManagerType DOOR = ManagerType.DOOR;
    private final ManagerType LEVER = ManagerType.LEVER;
    private final ManagerType SCREENWALL = ManagerType.SCREENWALL;
    private final ManagerType MAINCHAR = ManagerType.MAINCHAR;
    private final ManagerType POWERUP = ManagerType.POWERUP;
    private final ManagerType ENEMY = ManagerType.ENEMY;
    private final ManagerType SPAWNER = ManagerType.SPAWNER;
    private final ManagerType BULLET = ManagerType.BULLET;
    private final ManagerType HEALTH = ManagerType.HEALTH;
    private final ManagerType SCREEN = ManagerType.SCREEN;

    @Override
    //Initialize the sprites each manager starts with
    protected void initialize(){
        super.initialize();
        SteamSound.initialize();
        //Sync the array list and individual managers together
        managers[BACKGROUND.i] = backgroundManager;
        managers[WALL.i] = wallManager;
        managers[FLOOR.i] = floorManager;
        managers[PLATFORM.i] = platformManager;
        managers[DOOR.i] = doorManager;
        managers[LEVER.i] = leverManager;
        managers[SCREENWALL.i] = screenWallManager;
        managers[MAINCHAR.i] = mainCharManager;
        managers[POWERUP.i] = powerUpManager;
        managers[ENEMY.i] = enemyManager;
        managers[SPAWNER.i] = spawner;
        managers[BULLET.i] = bulletManager;
        managers[HEALTH.i] = healthManager;
        managers[SCREEN.i] = screenManager;

        //Be careful with the enemy and bullet manager, they still need to be initialized AFTER main character.
        //Backgrounds don't use the initalize function;
        //Walls don't use the initalize function;
        //Floors don't use the initalize function;
        //Platforms don't use the initalize function;
        doorManager.initialize(enemyManager.getSprites());
        leverManager.initialize(enemyManager.getSprites(), mainCharManager.getSprites());
        screenWallManager.initialize();
        mainCharManager.initialize(floorManager.getSprites(),screenWallManager.getSprites(), powerUpManager.getSprites(), platformManager.getSprites(), wallManager.getSprites(), bulletManager, doorManager.getSprites(), leverManager.getSprites());
        //Power ups don't initialize
        enemyManager.initialize(floorManager.getSprites(), screenWallManager.getSprites(), platformManager.getSprites(),(MainCharacter)mainCharManager.getSprites().get(0), wallManager.getSprites(), bulletManager);
        spawner.initialize();
        bulletManager.initialize((MainCharacter)mainCharManager.getSprites().get(0), enemyManager.getSprites(), wallManager.getSprites());
        healthManager.initialize((MainCharacter)mainCharManager.getSprites().get(0));
        screenManager.initialize();

        //load level and spawner
        loadNewLevel();
        SteamSound.musicLoopFire();
    }
    
     private void restart() {
         //give the player the "You have died screen"
         paused = true;
         hasStarted = false;
         screenManager.SetScreen(ScreenType.DIE);
         //re initialize character and set level = 1
         mainCharManager.reset();
         level = 1;
    }


    private void loadNewLevel(){
        //create a new level.
        for(Manager manager : managers){
            manager.switchLevel(level, spawner, getViewportTransform());
        }
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
            processKKeyInput();
            processRKeyInput();//Temporary for damaging the Main Character.
            processTKeyInput();//Temp
            processEKeyInput();
            processEndInput();
            processTestLevelChange();
        }
        for(Manager manager : managers){
            processScale(manager);
            manager.process(delta);
        }

    }

    private void processScale(Manager m){
        float scaleX;
        float scaleY;
        scaleX = getWidth()/origAppWidth;
        scaleY = getHeight()/origAppHeight;
        for(Sprite s:m.getSprites()){
            s.setGlobalScale(scaleX, scaleY);
        }
    }

    //End screen... currently can't restart the game.
    private void processEndInput(){
        if(((MainCharacter)mainCharManager.getSprites().get(0)).isEnd()){
            paused = !paused;
            screenManager.SetScreen(ScreenType.END);
        }
    }

    private void processKKeyInput(){
          if(keyboard.keyDownOnce(KeyEvent.VK_K)){
            mainCharManager.die();
        }
    }

    // Process a P key as the pause
    private void processPKeyInput() {
        if(keyboard.keyDownOnce(KeyEvent.VK_P) || keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)){
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

    private void processRKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_R)){
            ((MainCharacter)(managers[MAINCHAR.i]).getSprites().get(0)).setHp(((MainCharacter)(managers[MAINCHAR.i]).getSprites().get(0)).getHp() - 1);
        }
    }

    private void processTKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_T)){
            ((MainCharacter)(managers[MAINCHAR.i]).getSprites().get(0)).setHp(((MainCharacter)(managers[MAINCHAR.i]).getSprites().get(0)).getHp() + 1);
        }
    }

    //Process what happens when E is pressed - go through door to next level.
    private void processEKeyInput(){
        if(keyboard.keyDown(KeyEvent.VK_E)){
            mainCharManager.canGoThroughDoor();
        }
    }

    //Process what happens when S is pressed
    private void processSpaceKeyInput(){
        if(keyboard.keyDown(KeyEvent.VK_SPACE)){
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
        if(((MainCharacter)(managers[MAINCHAR.i]).getSprites().get(0)).isChangingLevel()){
            level++;
            if(level > 4){
                level = 1;
            }
            loadNewLevel();
        }
        else if(mainCharManager.isDead(appWorldWidth/2, appWorldHeight)){
            restart();
            loadNewLevel();
        }
        else if(keyboard.keyDownOnce(KeyEvent.VK_Z)){
            level--;
            if(level < 1){
                level = 4;
            }
            loadNewLevel();
        }
        else if(keyboard.keyDownOnce(KeyEvent.VK_C)){
            level++;
            if(level > 4){
                level = 1;
            }
            loadNewLevel();
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

