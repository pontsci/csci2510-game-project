package managers;

import sprite.world.PowerUp;
import status.Status;
import util.Matrix3x3f;
import util.Vector2f;

public class PowerUpManager extends Manager{

	private final static int HEALTH_IMG = 0;
	private final static int FR_IMG = 1;
	private final static int DMG_IMG = 2;
	private final static int SHIELD_IMG = 3;
	private final static int TASER_IMG = 4;

	public void addPowerUp(Status effect, Vector2f position) {
		
		//Adds a powerup with a matching StatusArchive id.
		//Health
		if(effect.id == 0) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), HEALTH_IMG, effect));
		}
		//Fire Rate
		else if(effect.id == 1) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), FR_IMG, effect));
		}
		//Damage
		else if(effect.id == 2) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), DMG_IMG, effect));
		}
		//Shield
		else if(effect.id == 3) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), SHIELD_IMG, effect));
		}
		//Taser
		else if(effect.id == 4) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), TASER_IMG, effect));
		}
		
		//Damage Over Time
		//Won't be an actual powerup, since this effect will damage the player. Using taser image as a placeholder.
		else if(effect.id == 5) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), TASER_IMG, effect));
		}
	}

	@Override
	public void checkCollision(float delta, Matrix3x3f viewport)
	{
		//not needed
	}

	@Override
	public void switchLevel(int level){

	}
}
