package managers;

import spawning.Spawner;
import sprite.screen.*;
import util.Vector2f;

import java.awt.*;

import util.Matrix3x3f;

//Could be used to handle which background to play on each level of the game
public class ScreenManager extends Manager{
    /**
     * An enum for screen types
     */
    public enum ScreenType {
        NONE(-1), PAUSE(0), START(1), DIE (2), END(3);
        
        private int i;
        ScreenType(int i) {
            this.i = i;
        }
        public int getIndex() {
            return i;
        }
    }
    
    private ScreenType currentScreen = ScreenType.NONE;

    /**
     * Get the background's sprite sheet and make a background.
     */
    public void initialize(){
        getSprites().add(new DisplayScreen(0, .2f, new Vector2f(1,.87f), loadFile("src/resources/world/background/PauseScreen.png")));//Pause
        getSprites().add(new DisplayScreen(0, 0, new Vector2f(1,.87f), loadFile("src/resources/world/background/StartScreen.png")));//Start
        getSprites().add(new DisplayScreen(0, 0, new Vector2f(1,.87f), loadFile("src/resources/world/background/DeathScreen.png")));//Die
        getSprites().add(new DisplayScreen(0, 0, new Vector2f(1,.87f), loadFile("src/resources/world/background/EndingScreen.png")));//Ending
        currentScreen = ScreenType.START;
    }

    /**
     * Allow setting the screenType externally
     * @param screenType the screen type
     */
    public void SetScreen(ScreenType screenType) {
        currentScreen = screenType;
    }

    /**
     * render the current screen
     * @param g graphics
     */
    @Override
    public void render(Graphics g){
        if (currentScreen != ScreenType.NONE)
            getSprites().get(currentScreen.getIndex()).render(g);
    }

    @Override
    public void checkCollision(float delta, Matrix3x3f viewport)
    {
        //no collision needed
    }

    @Override
    public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
        //Not needed
    }

}
