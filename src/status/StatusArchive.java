package status;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.Vector2f;

public class StatusArchive {

	private ArrayList<Status> conditions = new ArrayList<Status>();
	
	private BufferedImage healImg;
	private BufferedImage fireRateImg;
	private BufferedImage dmgUpImg;
	private BufferedImage shieldImg;
	private BufferedImage taserImg;
	private BufferedImage DoTImg;
	
	
	public StatusArchive(){
		BufferedImage icons = loadFile("src/resources/world/pickups/PickupObjects.png");
		BufferedImage lightnShield = loadFile("src/resources/world/pickups/LightningShield.png");
		healImg = loadFile("src/resources/world/pickups/health.png");
		fireRateImg = icons.getSubimage(50, 0, 50, 50);
		dmgUpImg = icons.getSubimage(0, 0, 50, 50);
		shieldImg = icons.getSubimage(400, 0, 50, 50);
		taserImg = icons.getSubimage(100, 0, 50, 50);
		DoTImg = lightnShield.getSubimage(0, 0, 237, 356);
		
		conditions.add(getHealthStatus());
		conditions.add(getFireRateStatus());
		conditions.add(getDmgStatus());
		conditions.add(getShieldStatus());
		conditions.add(getTaserStatus());
		conditions.add(getDoTStatus());
	}	
	
	public ArrayList<Status> returnStatuses() {
		return conditions;
	}
	
	//Health Status effect
	public Status getHealthStatus() {
        return new Status(0, "HealthUp", 1.5f, new Vector2f(1,1), healImg);
	}
	
	//Fire Rate Up Status effect
	public Status getFireRateStatus() {
        return new Status(1, "FireRateUp", 5.0f, new Vector2f(1,1), fireRateImg);
	}
	
	//Damage Up Status effect
	public Status getDmgStatus() {
        return new Status(2, "DmgUp", 5.0f, new Vector2f(1,1), dmgUpImg);
	}
	
	//Shield Effect
	public Status getShieldStatus() {
        return new Status(3, "ShieldUp", 5.0f, new Vector2f(1,1), shieldImg);
	}
	
	//Taser Effect
	public Status getTaserStatus() {
        return new Status(4, "TaserUp", 5.0f, new Vector2f(1,1), taserImg);
	}
	
	//DoT Effect
	public Status getDoTStatus() {
        return new Status(5, "DoT", 4.0f, new Vector2f(.21f,.14f), DoTImg);
	}
	
	protected BufferedImage loadFile(String fileName) {
        BufferedImage spriteSheet;
        try {
            spriteSheet = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            spriteSheet = null;
        }
        return spriteSheet;
    }
}
