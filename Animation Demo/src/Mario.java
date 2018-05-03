

import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Mario extends Sprite {
	
	private double xVel;
	private double yVel;
	
	private boolean canJump;
	
	public static final int MARIO_WIDTH = 40;
	public static final int MARIO_HEIGHT = 60;
	

	public Mario(PImage img, int x, int y) {
		super(img, x, y, MARIO_WIDTH, MARIO_HEIGHT);
		yVel = 0;
		xVel = 0;
		canJump = false;
	}

	// METHODS
	public void walk(int dir, ArrayList<Shape> obstacles) {
		boolean isValidWalk = true;
		for(Shape s : obstacles) {
			Rectangle boundRect = s.getBounds();
			if(this.getY() + MARIO_HEIGHT > boundRect.getY()
				&& this.getX() - 4 > boundRect.getX() + boundRect.getWidth() && this.getX() + MARIO_WIDTH + 4 < boundRect.getX()) {
				isValidWalk = false;
				break;
			}
		}
		if (isValidWalk)
			moveByAmount(3 * dir, 0);
	}

	public void jump() {
		if (canJump) {
			yVel = -7.5;
			moveByAmount(0, -5);
		}
	}

	public void act(ArrayList<Shape> obstacles) {
		
		yVel += 0.25;
		//System.out.println(canJump);
		// FALL (and stop when a platform is hit)
		for(Shape s : obstacles) {
			Rectangle boundRect = s.getBounds();
			if (/*this.getY() + MARIO_HEIGHT + yVel < boundRect.getY() //checks if mario is high enough
					&& */boundRect.intersects(this.getX(),this.getY(), MARIO_WIDTH, MARIO_HEIGHT) //checks if they intersect
					&& this.getX() + MARIO_WIDTH > boundRect.getX() && this.getX() < boundRect.getX() + boundRect.getWidth()) { // checks x coordinates
				canJump = true;
				moveToLocation(this.getX(), boundRect.getY() - MARIO_HEIGHT);
				yVel = 0;
				break;
			}
			else {
				canJump = false;
			}
		}

		System.out.println(canJump);
		
		this.moveByAmount(xVel, yVel);
		
	}


}
