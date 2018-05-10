package sprites;

import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Bullet extends Sprite {
	
	private PImage img;

	public static final int BULLET_WIDTH = 20;
	public static final int BULLET_HEIGHT = 20;
	public static final double PI = Math.PI;
	
	private double speed;
	
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
		this.speed = speed;
	}
	
	public void act() {
		moveByAmount(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));
	}
	
	// return true if it hits an obstacle, false if otherwise
	public boolean checkObstacles(ArrayList<Obstacle> obstacles) {
		double direction = getDirection();
		for(Obstacle s : obstacles) {
			for(int i = 0; i < 4; i++) {
				if(s.getLineComposition()[i].intersects(this.getCenterX(),this.getCenterY(), BULLET_WIDTH, BULLET_HEIGHT)) {
					if(!isBouncing)
						return true;
					else {
						double incidence = (direction - s.getLineCompositionNormals()[i]);
						if (s.getLineCompositionNormals()[i] == 0) {
							turn(Math.PI - incidence);
						}
						else {
							turn((Math.PI/2) - incidence);
						}

						timesBounced++;
//						if(timesBounced > 3)
//							isBouncing = false;
					}
						
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
