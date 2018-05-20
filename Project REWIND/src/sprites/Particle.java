package sprites;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PImage;
//doesn't have to be a sprite
/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class represents the particles that are created when certain actions occur.
 */
public class Particle extends Sprite {

	private double speed;
	
	private int lifeTime;
	private int initLifeTime;
	private Color color;
	
	private float width;
	private float height;
	
	public Particle(PImage image, double x, double y, double w, double h, int type) {
		super(image, x, y, w, h);
		if (type == 1) {		// 1 for flash, 2 for grenade, 3 for shield
			lifeTime = (int) (50 + Math.random() * 50);
			initLifeTime = lifeTime;
			
			//speed, randomly generated between 0.2 & 2.2
			speed = 0.2 + Math.random() * 0.2;
			color = new Color(255, 255, 255);
			
			turn(Math.random() * Math.PI * 2);
			
			width = (float) w;
			height = (float) h;
		}
		else if (type == 2) {
			lifeTime = (int) (30 + Math.random() * 50);
			initLifeTime = lifeTime;
			
			//speed, randomly generated between 0.2 & 2.2
			speed = 0.2 + Math.random() * 0.2;
			color = new Color(255, 255, 255);
			
			turn(Math.random() * Math.PI * 2);
			
			width = 5;
			height = 5;
		}
		else {
			this.x = (int) (x + Math.random() * w);
			this.y = (int) (y + Math.random() * h);
			width = (float) 1;
			height = (float) 1;
			lifeTime = (int) (15 + Math.random() * 10);
			initLifeTime = lifeTime;
			
			speed = 0;
			color = new Color(0, 255, 255);
		}
	}
	
	
	//return false if lifeTime is less than or equal to 0
	public boolean act() {
		moveByAmount(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));
		lifeTime--;
		if (lifeTime <= 0)
			return false;
		return true;
	}
	
	public void draw(PApplet drawer) {
		drawer.strokeWeight(1);
		drawer.fill((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 	//color
				(float)(255 - 255.0 / initLifeTime * (initLifeTime - lifeTime)));				//opacity
		
		drawer.ellipse((float)x,(float)y,(float)width,(float)height);
		drawer.fill((float)255, (float)255);
	}
	
}
