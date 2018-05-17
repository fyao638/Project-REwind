package sprites.projectile;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import sprites.Sprite;
import sprites.obstacles.Obstacle;

public abstract class Projectile extends Sprite{
	
	private double width, height, speed;
	
	
	public Projectile(PImage image, double x, double y, double width, double height, double dir, double speed) {
		super(image, x, y, width, height);
		
		turn(dir);
		
		this.speed = speed;
	}
	
	// METHODS
	
	public String toString() {
		return "";
	}
	
	public void act() {
		moveByAmount(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));
	}
	
	// return true if it hits an obstacle, false if otherwise
	public abstract boolean checkObstacles(ArrayList<Obstacle> obstacles);
		
	
	public void draw(PApplet drawer) {
		if(this.visible()) {
			drawer.pushMatrix();
			drawer.translate((float) (x + width / 2), (float) (y + height / 2));
			drawer.rotate((float) getDirection());
			drawer.image(getImage(),(int) - width / 3,(int) - height/ 2,(int)width,(int)height);
			drawer.popMatrix();
		}
	}

}
