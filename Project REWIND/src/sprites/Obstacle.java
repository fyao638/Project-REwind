package sprites;

import processing.core.PApplet;
import processing.core.PImage;

public class Obstacle extends Sprite {
	
	private PImage img;

	public Obstacle(PImage image, double x, double y, double w, double h) {
		super(image, x, y, w, h);
	}
	
	public void draw(PApplet drawer) {
		drawer.image(getImage(), (float)x,(float)y,(float)width,(float)height);
	}
}
