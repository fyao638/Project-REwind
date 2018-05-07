package sprites;

import processing.core.PApplet;
import processing.core.PImage;

public class Bullet extends Sprite {
	
	private PImage img;

	public static final int PLAYER_WIDTH = 50;
	public static final int PLAYER_HEIGHT = 50;
	
	public Bullet(PImage image, double x, double y, double xVel, double yVel) {
		super(image, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		setXVel(xVel);
		setYVel(yVel);
		
	}
	public void act() {
		moveByAmount(getXVel(), getYVel());
	}
	
}
