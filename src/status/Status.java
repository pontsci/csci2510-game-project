package status;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import sprite.world.StatusIcon;
import util.Vector2f;

public class Status {
	public int id;
	public boolean active;
	public float duration;
	public String name;
	public StatusIcon icon;
	
	//Fills in new Status object's information
	public Status(int id, String name, float timer, Vector2f scale, BufferedImage newIc) {
		this.id = id;
		active =  false;
		duration = timer;
		this.name = name;
		icon = new StatusIcon(scale, newIc);
	}
	
	//Render given sprite
	public void render(Graphics g) {
		if(active) {
			icon.render(g);
		}
	}
}
