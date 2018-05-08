package sprites;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Sprite {

	public static final int PLAYER_WIDTH = 90;
	public static final int PLAYER_HEIGHT = 60;
	private static final double BUL_ANGLE = Math.atan((PLAYER_HEIGHT - 20) / (2 * PLAYER_WIDTH) / 3);
	private static final double BUL_DISTANCE = PLAYER_WIDTH * 1.225 / 2;
	
	private boolean canMove;
	
	private Point2D.Double bulletPoint;
	
	private int xMov;
	private int yMov;
	
	public Player(PImage img, int x, int y) {
		super(img, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		canMove = true;
		xMov = 0;
		yMov = 0;
		bulletPoint = new Point2D.Double(x + PLAYER_WIDTH + 5, y + PLAYER_HEIGHT - 25);
	}

	// METHODS
	public void walk(int xDir, int yDir) {
		if(canMove) {
			this.moveByAmount(xDir * 5, yDir * 5);
			xMov = xDir;
			yMov = yDir;
		}
	}

	public void shoot(Bullet b) {
		
	}
	
	public void draw(PApplet drawer) {
		// change where the bullet hole is
		double angle = getDirection();
		bulletPoint.setLocation(x - 10 + BUL_DISTANCE + Math.cos(angle + BUL_ANGLE) * (BUL_DISTANCE - 5), 
				y - 26 + BUL_DISTANCE + Math.sin(angle + BUL_ANGLE) * (BUL_DISTANCE - 5));
		
		// draw the player
		drawer.pushMatrix();
		drawer.translate((float) (x + PLAYER_WIDTH / 2), (float) (y + PLAYER_HEIGHT / 2));
		drawer.rotate((float) angle);
		drawer.image(getImage(),(int) - PLAYER_WIDTH / 3,(int) - PLAYER_HEIGHT/ 2 - 16,(int)width,(int)height);
		drawer.popMatrix();
		//the point you see kind of inside the gun (also visible on the ghost is where the bullet spawns
			//the bullet doesn't always seem to spawn there but I don't know the issue to that.
		drawer.point((float) (bulletPoint.getX()), (float) bulletPoint.getY());
		
	}
	
	public void turnToMouse(int mouseX, int mouseY) {
		this.turnToward(mouseX, mouseY);
	}
	
	public void rewind(Point2D.Double p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public void checkObstacleCollision(ArrayList<Shape> obstacles) {
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
	
	public Point2D.Double getBulletPoint() {
		return bulletPoint;
	}
	
	
	
	public Point2D.Double getCenterPoint() {
		return new Point2D.Double(x - PLAYER_WIDTH / 3,x - PLAYER_HEIGHT/ 2 - 16);
	}
}
