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
    BackgroundManager backgroundManager;
    FloorManager floorManager;
    PlatformManager platformManager;
    WallManager wallManager;
    PowerUpManager powerUpManager;
    RatManager ratManager;
    MainCharacterManager mainCharManager;
    Spawner spawner;
    BulletManager bulletManager;
    EnemyManager enemyManager;
    private boolean renderHitboxes = false;
    private int level = 2;


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
        BACKGROUND(0), FLOOR(1), PLATFORM(2), MAINCHAR(3), RAT(4), WALL(5), POWERUP(6), ENEMY(7), SPAWNER(8), BULLET(9);

        private int i;
        ManagerType(int i){
            this.i = i;
        }
    }

    //easier enum usage declarations
    private final ManagerType BACKGROUND = ManagerType.BACKGROUND;
    private final ManagerType FLOOR = ManagerType.FLOOR;
    private final ManagerType PLATFORM = ManagerType.PLATFORM;
    private final ManagerType MAINCHAR = ManagerType.MAINCHAR;
    private final ManagerType RAT = ManagerType.RAT;
    private final ManagerType WALL = ManagerType.WALL;
    private final ManagerType POWERUP = ManagerType.POWERUP;
    private final ManagerType ENEMY = ManagerType.ENEMY;
    private final ManagerType SPAWNER = ManagerType.SPAWNER;
    private final ManagerType BULLET = ManagerType.BULLET;

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

        /*
         * The following declarations are for ease of use whilst
         * maintaining the ability to loop through managers
        */

        //world managers
        backgroundManager = (BackgroundManager) managers[BACKGROUND.i];
        floorManager = (FloorManager)managers[FLOOR.i];
        platformManager = (PlatformManager)managers[PLATFORM.i];
        wallManager = (WallManager)managers[WALL.i];

        //spawning/item managers
        powerUpManager = (PowerUpManager)managers[POWERUP.i];
        spawner = (Spawner) managers[SPAWNER.i];

        //shorten calls
        Floor floor = (Floor)floorManager.getSprites().get(0);

        //rat
        managers[RAT.i] = new RatManager(floor, wallManager.getSprites(), platformManager.getSprites());
        ratManager = (RatManager) managers[RAT.i];

        //main character
        managers[MAINCHAR.i] = new MainCharacterManager(floor, wallManager.getSprites(), ratManager.getSprites(), powerUpManager.getSprites(), platformManager.getSprites());
        mainCharManager = (MainCharacterManager)managers[MAINCHAR.i];
        MainCharacter player = (MainCharacter) mainCharManager.getSprites().get(0);

        //enemy
        managers[ENEMY.i] = new EnemyManager(floor, wallManager.getSprites(), platformManager.getSprites(), player);
        enemyManager = (EnemyManager) managers[ENEMY.i];

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
        processSpaceKeyInput();
        processMovementInput(delta);
        processBKeyInput();
        processSKeyInput();
        processJKeyInput();
        processTestLevelChange();
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

    //Process what happens when S is pressed
    private void processJKeyInput(){
        if(keyboard.keyDownOnce(KeyEvent.VK_J)){
            ((MainCharacterManager)managers[3]).processShoot();
        }
    }

    private void processTestLevelChange(){
        if(keyboard.keyDownOnce(KeyEvent.VK_Z)){
            level--;
            loadNewLevel();
        }
        else if(keyboard.keyDownOnce(KeyEvent.VK_C)){
            level++;
            loadNewLevel();
        }
    }

    private void loadNewLevel(){
        //clear level
        platformManager.getSprites().clear();
        enemyManager.getSprites().clear();
        powerUpManager.getSprites().clear();
        //Load platforms and spawnranges
        platformManager.getSprites().clear();
        platformManager.getSprites().addAll(Levels.getLevel(level));
        spawner.setSpawnRanges(platformManager.getPlatFormSpawns(getViewportTransform()));
        //Spawn enemies
        for (int i = 0; i < 3; i++){
            enemyManager.addTriBot(spawner.getSpawnPoint());
        }
        //Spawn powerups.
        powerUpManager.addPowerUp(StatusArchive.getHealthStatus(), spawner.getSpawnPoint());
        powerUpManager.addPowerUp(StatusArchive.getFireRateStatus(), spawner.getSpawnPoint());
        powerUpManager.addPowerUp(StatusArchive.getDmgStatus(), spawner.getSpawnPoint());
        powerUpManager.addPowerUp(StatusArchive.getShieldStatus(), spawner.getSpawnPoint());
        powerUpManager.addPowerUp(StatusArchive.getTaserStatus(), spawner.getSpawnPoint());
        powerUpManager.addPowerUp(StatusArchive.getDoTStatus(), spawner.getSpawnPoint());
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

