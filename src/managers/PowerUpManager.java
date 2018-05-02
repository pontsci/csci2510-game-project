package managers;

import spawning.Spawner;
import sprite.world.PowerUp;
import status.Status;
import status.StatusArchive;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.image.BufferedImage;

public class PowerUpManager extends Manager{
	private BufferedImage powerupSpriteSheet = loadFile("src/resources/world/pickups/PickupObjects.png");
	private BufferedImage healImg = loadFile("src/resources/world/pickups/health.png");
	BufferedImage lightnShield = loadFile("src/resources/world/pickups/LightningShield.png");
	private StatusArchive archive = new StatusArchive();

	/**
	 * Add a power up according to passed parameters
	 * @param effect the effect type
	 * @param position the start position
	 */
	private void addPowerUp(Status effect, Vector2f position) {
		
		//Adds a powerup with a matching StatusArchive id.
		//Health
		if(effect.id == 0) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), healImg, effect));
		}
		//Fire Rate
		else if(effect.id == 1) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), powerupSpriteSheet.getSubimage(50, 0, 50, 50), effect));
		}
		//Damage
		else if(effect.id == 2) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), powerupSpriteSheet.getSubimage(0, 0, 50, 50), effect));
		}
		//Shield
		else if(effect.id == 3) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), powerupSpriteSheet.getSubimage(400, 0, 50, 50), effect));
		}
		//Taser
		else if(effect.id == 4) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), powerupSpriteSheet.getSubimage(100, 0, 50, 50), effect));
		}
		
		//Damage Over Time
		//Won't be an actual powerup, since this effect will damage the player. Using taser image as a placeholder.
		else if(effect.id == 5) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(.21f,.14f), lightnShield.getSubimage(0, 0, 237, 356), effect));
		}
	}

	@Override
	public void checkCollision(float delta, Matrix3x3f viewport)
	{
		//not needed
	}

	/**
	 * Depending on the level, change what power ups will spawn
	 * @param level the current level
	 * @param spawner the spawn system
	 * @param viewport the viewport
	 */
	@Override
	public void switchLevel(int level, Spawner spawner, Matrix3x3f viewport){
		getSprites().clear();
		switch(level){
			case 1:
				addPowerUp(archive.getDmgStatus(), spawner.getSpawnPoint());
				addPowerUp(archive.getFireRateStatus(), spawner.getSpawnPoint());
				break;
			case 2:
				addPowerUp(archive.getHealthStatus(), spawner.getSpawnPoint());
				addPowerUp(archive.getShieldStatus(), spawner.getSpawnPoint());
				break;
			case 3:
				addPowerUp(archive.getTaserStatus(), spawner.getSpawnPoint());
				addPowerUp(archive.getHealthStatus(), spawner.getSpawnPoint());
				break;
			case 4:
				addPowerUp(archive.getHealthStatus(), spawner.getSpawnPoint());
				addPowerUp(archive.getTaserStatus(), spawner.getSpawnPoint());
				break;
			case 5:
				addPowerUp(archive.getFireRateStatus(), spawner.getSpawnPoint());
				addPowerUp(archive.getShieldStatus(), spawner.getSpawnPoint());
				break;
			case 6:
				addPowerUp(archive.getTaserStatus(), spawner.getSpawnPoint());
				addPowerUp(archive.getHealthStatus(), spawner.getSpawnPoint());
				break;
			case 7:
				addPowerUp(archive.getDmgStatus(), new Vector2f(-6.0f, -4.1f));
		}
	}
}
