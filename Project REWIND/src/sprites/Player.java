package sprites;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;
import testers.DrawingSurface;

public class Player extends Sprite {

	public static final int PLAYER_WIDTH = 60;
	public static final int PLAYER_HEIGHT = 40;
	private static final double BUL_ANGLE = Math.atan((PLAYER_HEIGHT - 20) / (2 * PLAYER_WIDTH) / 3);
	private static final double BUL_DISTANCE = PLAYER_WIDTH * 1.225 / 2;
	
	private Rectangle boundingRect;
	
	private Point2D.Double bulletPoint;
	
	public Player(PImage img, int x, int y) {
		super(img, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		bulletPoint = new Point2D.Double(x + PLAYER_WIDTH + 5, y + PLAYER_HEIGHT - 25);
		boundingRect = new Rectangle(getBoundRect());
	}

	// METHODS
	
	public void draw(PApplet drawer) {
		// change where the bullet hole is
		double angle = getDirection();
		bulletPoint.setLocation(x - PLAYER_WIDTH / 10 + BUL_DISTANCE + Math.cos(angle + BUL_ANGLE) * (BUL_DISTANCE - 5), 
				y - PLAYER_HEIGHT / 2.35 + BUL_DISTANCE + Math.sin(angle + BUL_ANGLE) * (BUL_DISTANCE - 5));

		// draw the player
		drawer.pushMatrix();
		drawer.translate((float) (x + PLAYER_WIDTH / 2), (float) (y + PLAYER_HEIGHT / 2));
		drawer.rotate((float) angle);
		drawer.image(getImage(), (float) (- PLAYER_WIDTH / 3), (float) (- PLAYER_HEIGHT / (1.4)),(float)width,(float)height);
		drawer.popMatrix();
		//the point you see kind of inside the gun (also visible on the ghost is where the bullet spawns
			//the bullet doesn't always seem to spawn there but I don't know the issue to that.
		drawer.point((float) (bulletPoint.getX()), (float) bulletPoint.getY());
		
		
		// Drawing the bounding rectangle
		/*
		drawer.noFill();
		drawer.rectMode(PApplet.CORNER);
		boundingRect = getBoundRect();
		drawer.rect((float)boundingRect.getX(), (float)boundingRect.getY(), (float)boundingRect.getWidth(), (float)boundingRect.getHeight());
		*/
	}
	
	public void walk(int xDir, int yDir, ArrayList<Shape> obstacles) {
		if(canMove(xDir * 5, yDir * 5, obstacles)) {
			this.moveByAmount(xDir * 5, yDir * 5);
		}
	}
	
	private boolean canMove(int xChange, int yChange, ArrayList<Shape> obstacles) {
		double pX = getX();
		double pY = getY();
		for(Shape s: obstacles) {
			if (xChange != 0) {
				if (xChange > 0) {
					for (int i = 1; i <= xChange; i++) {
						
						moveToLocation(pX + i, pY);
						if(s.getBounds().intersects(boundingRect.getX(), boundingRect.getY(), boundingRect.width, boundingRect.height)) {
							moveToLocation(pX, pY);
							return false;
						}
					}
				}
				else {
					for (int i = -1; i >= xChange; i--) {
						moveToLocation(pX + i, pY);
						if(s.getBounds().intersects(boundingRect.getX(), boundingRect.getY(), boundingRect.width, boundingRect.height)) {
							moveToLocation(pX, pY);
							return false;
						}
					}
				}
			
			}
			if (yChange != 0) {
				if (yChange > 0) {
					for (int i = 1; i <= yChange; i++) {
						moveToLocation(pX, pY + i);
						if(s.getBounds().intersects(boundingRect.getX(), boundingRect.getY(), boundingRect.width, boundingRect.height)) {
							moveToLocation(pX, pY);
							return false;
						}
					}
				}
				else {
					for (int i = -1; i >= yChange; i--) {
						moveToLocation(pX, pY + i);
						if(s.getBounds().intersects(boundingRect.getX(), boundingRect.getY(), boundingRect.width, boundingRect.height)) {
							moveToLocation(pX, pY);
							return false;	
						}
					}
				}
			}
		}
		moveToLocation(pX, pY);
		return true;
	}
	
	public Point2D.Double getBulletPoint() {
		return bulletPoint;
	}
	
	public void moveToLocation(double x, double y) {
		super.moveToLocation(x, y);
		boundingRect = getBoundRect();
	}	
	public Point2D.Double getCenterPoint() {
		return new Point2D.Double(x - PLAYER_WIDTH / 3,x - PLAYER_HEIGHT/ 2 - 16);
	}
	
	//method that gets the bounding rectangle of just the guy, not including the gun
	private Rectangle getBoundRect() {
		Rectangle rect = new Rectangle((int) x + PLAYER_WIDTH / 12, (int) y - PLAYER_WIDTH / 12, PLAYER_WIDTH * 5/6, PLAYER_WIDTH * 5/6);
		return rect;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
