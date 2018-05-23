package sprites.projectile;

import java.util.ArrayList;

import gui.ScaleImage;
import processing.core.PApplet;
import processing.core.PImage;
import sprites.Sprite;
import sprites.obstacles.Obstacle;
import sprites.player.Player;

public abstract class Projectile extends Sprite{
	
	private double width, height, speed;
	private int type;
	
	
	public Projectile(ScaleImage image, double x, double y, double width, double height, double dir, double speed, int type) {
		super(image, x, y, width, height);
		
		turn(dir);
		this.type = type;
		this.speed = speed;
	}
	//sdkfjlkgjsd
	// METHODS
	
	public String toString() {
		return "";
	}
	
	public void act() {
		moveByAmount(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));
	}
	
	// return true if it hits an obstacle, false if otherwise
	public abstract boolean checkObstacles(ArrayList<Obstacle> obstacles);
	
	// > 0 for hit, <= 0 for miss
	public abstract int checkPlayer(Player player);
	
	public abstract boolean isActive();
	
	public void draw(PApplet drawer) {
		if(this.visible()) {
			drawer.pushMatrix();
			drawer.translate((float) (x + width / 2), (float) (y + height / 2));
			drawer.rotate((float) getDirection());
			getImage().draw(drawer,(int) - width / 3,(int) - height/ 2,(int)width,(int)height);
			drawer.popMatrix();
		}
		
	}
	
	public int getType() {
		return type;
	}
}
