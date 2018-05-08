package sprites;

import processing.core.PApplet;
import processing.core.PImage;

public class Bullet extends Sprite {
	
	private PImage img;

	public static final int BULLET_WIDTH = 10;
	public static final int BULLET_HEIGHT = 10;
	
	private double speed;
	
	public Bullet(PImage image, double x, double y, double dir, double speed) {
		super(image, x, y, BULLET_WIDTH, BULLET_HEIGHT);
		turn(dir);
		this.speed = speed;
	}
	
	public void act() {
		moveByAmount(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));
	}
	
	public void draw(PApplet drawer) {
		drawer.pushMatrix();
		drawer.translate((float) (x + BULLET_WIDTH / 2), (float) (y + BULLET_HEIGHT / 2));
		drawer.rotate((float) getDirection());
		drawer.image(getImage(),(int) - BULLET_WIDTH / 3,(int) - BULLET_HEIGHT/ 2,(int)width,(int)height);
		drawer.popMatrix();
	}
	
}
