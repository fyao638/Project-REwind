

import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Mario extends Sprite {
	
	private double xVel;
	private double yVel;
	
	public static final int MARIO_WIDTH = 40;
	public static final int MARIO_HEIGHT = 60;
	

	public Mario(PImage img, int x, int y) {
		super(img, x, y, MARIO_WIDTH, MARIO_HEIGHT);
		yVel = 0;
		xVel = 0;
	}

	// METHODS
	public void walk(int dir) {
		moveByAmount(3 * dir, 0);
	}

	public void jump() {
		yVel = -10;
	}

	public void act(ArrayList<Shape> obstacles) {
		
		yVel += 0.05;
		
		// FALL (and stop when a platform is hit)
		for(Shape s : obstacles) {
			if(s.intersects(this.getX(),this.getY() , MARIO_WIDTH, MARIO_HEIGHT)) {
				yVel = 0;
			}
		}
		
		
		this.moveByAmount(xVel, yVel);
		
	}


}
