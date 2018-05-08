package sprites;

import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Bullet extends Sprite {
	
	private PImage img;

	public static final int BULLET_WIDTH = 20;
	public static final int BULLET_HEIGHT = 20;
	
	public Bullet(PImage image, double x, double y, double xVel, double yVel) {
		super(image, x, y, BULLET_WIDTH, BULLET_HEIGHT);
		setXVel(xVel);
		setYVel(yVel);
		
	}
	public void act(ArrayList<Shape> obstacles) {
		
		for(Shape s : obstacles) {
			if(s.getBounds().intersects(this.getCenterX(),this.getCenterY(), BULLET_WIDTH, BULLET_HEIGHT)) {
				this.setVisibility(false);
			}
		}
		
		moveByAmount(getXVel(), getYVel());
	}
	
}
