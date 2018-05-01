

import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Mario extends Sprite {

	public static final int MARIO_WIDTH = 40;
	public static final int MARIO_HEIGHT = 60;
	
	private double yVel;

	public Mario(PImage img, int x, int y) {
		super(img, x, y, MARIO_WIDTH, MARIO_HEIGHT);
		yVel = 0;
	}

	// METHODS
	public void walk(int dir) {
		// WALK!
	}

	public void jump() {
		// JUMP!
	}

	public void act(ArrayList<Shape> obstacles) {
		
		yVel += 0.05;
		
		// FALL (and stop when a platform is hit)
		for(Shape s : obstacles) {
			if(s.intersects(this.getX(),this.getY() , MARIO_WIDTH, MARIO_HEIGHT)) {
				yVel = 0;
			}
		}
		
		
		this.moveByAmount( 0 , yVel);
		
	}


}
