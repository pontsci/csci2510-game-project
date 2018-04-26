package managers;

import spawning.Spawner;
import util.Vector2f;

import java.awt.*;
import sprite.screens.Pause;
import sprite.screens.Start;
import util.Matrix3x3f;

//Could be used to handle which background to play on each level of the game
public class ScreenManager extends Manager{
    public enum ScreenType {
        NONE(-1), PAUSE(0), START(1);
        
        private int i;
        ScreenType(int i) {
            this.i = i;
        }
        public int getIndex() {
            return i;
        }
    }
    
    private ScreenType currentScreen = ScreenType.NONE; 
    
    //Get the background's sprite sheet and make a background.
    public void initialize(){
        getSprites().add(new Pause(0, .2f, new Vector2f(1,.87f)));
        getSprites().add(new Start(0, 0, new Vector2f(1,.87f)));
        currentScreen = ScreenType.START;
    }
    
    // Allow setting the screenType externally
    public void SetScreen(ScreenType screenType) {
        currentScreen = screenType;
    }
    
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

    }
}
