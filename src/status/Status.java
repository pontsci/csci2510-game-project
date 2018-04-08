package status;

import java.awt.Graphics;

import sprite.Sprite;

public class Status {
	public int id;
	public boolean active;
	public float duration;
	public String name;
	public Sprite FX;
	
	//Fills in new Status object's information
	public Status(int id, String name, float timer, Sprite newFX) {
		this.id = id;
		active =  false;
		duration = timer;
		this.name = name;
		FX = newFX;
	}
	
	//Render given sprite
	public void render(Graphics g) {
		if(FX != null) {
			FX.render(g);
		}
	}
}
