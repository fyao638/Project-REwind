package sprites;

import processing.core.PApplet;
import processing.core.PImage;
//doesn't have to be a sprite
public class Particle extends Sprite {

	private double speed;
	
	private int lifeTime;
	private int initLifeTime;
	
	public Particle(PImage image, double x, double y, double w, double h) {
		super(image, x, y, w, h);
		lifeTime = (int) (50 + Math.random() * 50);
		initLifeTime = lifeTime;
		
		//speed, randomly generated between 0.2 & 2.2
		speed = 0.2 + Math.random() * 0.2;
		
		turn(Math.random() * Math.PI * 2);
	}
	
	
	//return false if lifeTime is over
	public boolean act() {
		moveByAmount(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));
		lifeTime--;
		if (lifeTime <= 0)
			return false;
		return true;
	}
	
	public void draw(PApplet drawer) {
		drawer.fill((float)255, (float)(255 - 255.0 / initLifeTime * (initLifeTime - lifeTime)));
		drawer.ellipse((float)x,(float)y,(float)getWidth(),(float)getHeight());
		drawer.fill((float)255, (float)255);
	}
	
}
