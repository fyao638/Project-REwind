package sprites.projectile;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import sprites.Sprite;
import sprites.obstacles.Obstacle;
import sprites.player.Player;

/**
 * 
 * @author Aakarsh Anand
 * This class represents the grenade shot by Demolitions.
 *
 */
public class Grenade extends Projectile{
	
	private PImage img;

	public static final int GRENADE_WIDTH = 40;
	public static final int GRENADE_HEIGHT = 40;
	public static final double PI = Math.PI;
	
	private double speed;
	
	
	public Grenade(PImage image, double x, double y, double dir, double speed) {
		super(image, x, y, GRENADE_WIDTH, GRENADE_HEIGHT, dir, speed);
		
//		double i = Math.random();
//		if (i < 0.5)
//			turn(dir + (Math.random()/50.0));
//		else
//			turn(dir - (Math.random()/50.0));
		turn(dir);
		
		this.speed = speed;
	}
	
	public void act() {
		moveByAmount(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));
	}
	
	// return true if it hits an obstacle, false if otherwise
	public boolean checkObstacles(ArrayList<Obstacle> obstacles) {
		double x = 0.5;
		if (speed < 0)
			speed = 0;
		if (speed > 0)
			speed -= Math.pow(x, 2);
		x += 0.001;
		return false;
	}
		
	public boolean checkPlayer(Player player) {
		return false;
	}
	
	public void draw(PApplet drawer) {
		if(this.visible()) {
			drawer.pushMatrix();
			drawer.translate((float) (x + GRENADE_WIDTH / 2), (float) (y + GRENADE_HEIGHT / 2));
			drawer.rotate((float) getDirection());
			drawer.image(getImage(),(int) - GRENADE_WIDTH / 3,(int) - GRENADE_HEIGHT/ 2,(int)GRENADE_HEIGHT,(int)GRENADE_HEIGHT);
			if(this.speed == 0)
				this.setVisibility(false);
			drawer.popMatrix();
		}
	}
}
