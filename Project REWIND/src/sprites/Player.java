package sprites;

import java.awt.*;
import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Sprite {

	public static final int PLAYER_WIDTH = 90;
	public static final int PLAYER_HEIGHT = 60;
	
	private boolean canMove;
	
	private int xMov;
	private int yMov;

	private PImage img;
	private double dir;
	
	public Player(PImage img, int x, int y) {
		super(img, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		canMove = true;
		xMov = 0;
		yMov = 0;
		dir = 0;
		this.img = img;
	}

	// METHODS
	public void walk(int xDir, int yDir) {
		if(canMove) {
			this.moveByAmount(xDir * 5, yDir * 5);
			xMov = xDir;
			yMov = yDir;
		}
	}
	public void turnToward(int x, int y) {
		if ((this.x + width / 2) - x == 0) {
			if ((this.y + height / 2) - y > 0)
				dir = 3 * Math.PI / 2;
			else
				dir = Math.PI / 2;
		}
		else
			dir = Math.atan(((double)(this.y + height / 2) - y)/((this.x + width / 2) - x));
		
		if ((this.x + width / 2) > x)
			dir += Math.PI;
		
		if (dir >= 2 * Math.PI)
			dir -= 2 * Math.PI;
	}
	public void draw(PApplet drawer) {
		drawer.image(img,(int)x,(int)y,(int)width,(int)height);
		drawer.rotate((float) dir);
	}
	
	public void turnToMouse(int mouseX, int mouseY) {
		this.turnToward(mouseX, mouseY);
	}

	public void act(ArrayList<Shape> obstacles) {
		// FALL (and stop when a platform is hit)
		for(Shape s: obstacles) {
			if(s.getBounds().intersects(this.getX(), this.getY(), PLAYER_WIDTH, PLAYER_HEIGHT)) {
				canMove = false;
				moveToLocation(this.getX() - PLAYER_WIDTH * xMov, s.getBounds().getY() - PLAYER_HEIGHT * yMov);
			}
			else {
				canMove = true;
			}
		}
	}
}
