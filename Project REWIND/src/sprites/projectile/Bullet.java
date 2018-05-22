package sprites.projectile;

import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import sprites.Sprite;
import sprites.obstacles.Obstacle;
import sprites.player.Player;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class represents the bullets that are shot from weapons in the game.
 */
public class Bullet extends Projectile{
	
	private PImage img;

	public static final double BULLET_WIDTH = 20;
	public static final double BULLET_HEIGHT = 5;
	public static final double PI = Math.PI;
	
	private double speed;
	
	
	public Bullet(PImage image, double x, double y, double dir, double speed, int type) {
		super(image, x, y, BULLET_WIDTH, BULLET_HEIGHT, dir, speed, type);
		
//		double i = Math.random();
//		if (i < 0.5)
//			turn(dir + (Math.random()/50.0));
//		else
//			turn(dir - (Math.random()/50.0));
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
	public boolean checkObstacles(ArrayList<Obstacle> obstacles) {
		for(Obstacle s : obstacles) {
			for(int i = 0; i < 4; i++) {
				if(s.intersects(this.getX() ,this.getY(), BULLET_WIDTH, BULLET_HEIGHT)) {
					return true;
						
				}
			}
			
		}
		return false;
	}
	
	public int checkPlayer(Player player) {
		for(int i = 0; i < 4; i++) {
			if(player.intersects(this.getX(),this.getY(), BULLET_WIDTH, BULLET_HEIGHT)) {
				return 1;
						
			}
		}
		return 0;
	}
		
	
	public void draw(PApplet drawer) {
		if(this.visible()) {
			drawer.pushMatrix();
			drawer.translate((float) (x + BULLET_WIDTH / 2), (float) (y + BULLET_HEIGHT / 2));
			drawer.rotate((float) getDirection());
			drawer.image(getImage(),(int) - BULLET_WIDTH / 3,(int) - BULLET_HEIGHT/ 2,(int)BULLET_WIDTH,(int)BULLET_HEIGHT);
			drawer.popMatrix();
		}
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
