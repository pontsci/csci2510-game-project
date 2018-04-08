package managers;

import java.awt.image.BufferedImage;

import sprite.world.PowerUp;
import status.Status;
import util.Vector2f;

public class PowerUpManager extends Manager{

	private BufferedImage healthImg;
	private BufferedImage frImg;
	private BufferedImage dmgImg;
	private BufferedImage shieldImg;
	private BufferedImage taserImg;
	
	//Parse pick up images and save individual images
	public PowerUpManager() {
		BufferedImage sprSheet = super.loadFile("src/resources/world/pickups/PickupObjects.png");
		dmgImg = sprSheet.getSubimage(0, 0, 50, 50);
		frImg = sprSheet.getSubimage(50, 0, 50, 50);
		taserImg = sprSheet.getSubimage(100, 0, 50, 50);
		shieldImg = sprSheet.getSubimage(400, 0, 50, 50);
		
		//Using key as a stand in image
		healthImg = sprSheet.getSubimage(150, 0, 50, 50);
	}
	
	public void addPowerUp(Status effect, Vector2f position) {
		
		//Adds a powerup with a matching StatusArchive id.
		//Health
		if(effect.id == 0) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), healthImg, effect));
		}
		//Fire Rate
		else if(effect.id == 1) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), frImg, effect));
		}
		//Damage
		else if(effect.id == 2) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), dmgImg, effect));
		}
		//Shield
		else if(effect.id == 3) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), shieldImg, effect));
		}
		//Taser
		else if(effect.id == 4) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), taserImg, effect));
		}
		
		//Damage Over Time
		//Won't be an actual powerup, since this effect will damage the player. Using taser image as a placeholder.
		else if(effect.id == 5) {
			getSprites().add(new PowerUp(position.x, position.y, new Vector2f(1,1), taserImg, effect));
		}
	}
}
