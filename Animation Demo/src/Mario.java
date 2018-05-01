

import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Mario extends Sprite {
	
	private double xVel = 0;
	private double yVel = 0;
	
	public static final int MARIO_WIDTH = 40;
	public static final int MARIO_HEIGHT = 60;

	public Mario(PImage img, int x, int y) {
		super(img, x, y, MARIO_WIDTH, MARIO_HEIGHT);
	}

	// METHODS
	public void walk(int dir) {
		this.moveByAmount(dir * 3, 0);
	}

	public void jump() {
		yVel = -10;
	}

	public void act(ArrayList<Shape> obstacles) {
		yVel += 0.05;
		// FALL (and stop when a platform is hit)
		this.moveByAmount(xVel, yVel);
	}


}
