package sprites;

import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Bullet extends Sprite {
	
	private PImage img;

	public static final int BULLET_WIDTH = 20;
	public static final int BULLET_HEIGHT = 20;
	
	private double speed, dir;
	
	private int timesBounced;
	
	private boolean isBouncing;
	
	public Bullet(PImage image, double x, double y, double dir, double speed, boolean isBouncing) {
		super(image, x, y, BULLET_WIDTH, BULLET_HEIGHT);
		
//		double i = Math.random();
//		if (i < 0.5)
//			turn(dir + (Math.random()/50.0));
//		else
//			turn(dir - (Math.random()/50.0));
		turn(dir);
		
		this.isBouncing = isBouncing;
		this.dir = dir;
		this.speed = speed;
	}
	
	public void act() {
		moveByAmount(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));
	}
	
	// return true if it hits an obstacle, false if otherwise
	public boolean checkObstacles(ArrayList<Shape> obstacles) {
		
		for(Shape s : obstacles) {
			if(s.getBounds().intersects(this.getCenterX(),this.getCenterY(), BULLET_WIDTH, BULLET_HEIGHT)) {
				if(!isBouncing)
					return true;
				else {
					if(timesBounced > 3) {
						isBouncing = false;
						break;
					}
					//System.out.println(dir);
					if(dir > 3 && dir < 3.5) {
						double angle = Math.atan2(Math.sin(dir), Math.cos(dir));
						System.out.println(angle);
						dir = -(3.14 + (dir - 3.14));
						//dir += angle;
					}
//					else
//						dir -= 1;
//					if(dir > 4.7)
//						dir = -1.55 + (dir - 4.7);
					turn(dir);
					timesBounced++;
				}
					
			}
		}
		return false;
	}
		
	
	public void draw(PApplet drawer) {
		if(this.visible()) {
			drawer.pushMatrix();
			drawer.translate((float) (x + BULLET_WIDTH / 2), (float) (y + BULLET_HEIGHT / 2));
			drawer.rotate((float) getDirection());
			drawer.image(getImage(),(int) - BULLET_WIDTH / 3,(int) - BULLET_HEIGHT/ 2,(int)width,(int)height);
			drawer.popMatrix();
		}
	}
	
}
