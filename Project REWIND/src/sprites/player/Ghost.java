package sprites.player;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import sprites.Sprite;
import sprites.obstacles.Obstacle;
import sprites.projectile.Bullet;
import sprites.projectile.Projectile;

public class Ghost extends Sprite{
	
	public static final int PLAYER_WIDTH = 60;
	public static final int PLAYER_HEIGHT = 40;
	private static final double BUL_ANGLE = Math.atan((PLAYER_HEIGHT - 20) / (2 * PLAYER_WIDTH) / 3);
	private static final double BUL_DISTANCE = PLAYER_WIDTH * 1.0 / 2;
	
	//This will be used to determine the abilities and secondary fire of the player as there be different ones
	// EX:
	// num = name = color scheme = secondary fire / shift ability
	// 1 = "thicc and slick" / assault = blue and black = shotgun / flash
	// 2 = "loud and proud" / demolition = red and black = burst(may be changed) / grenade
	// 3 = "400+ IQ plays" / technician = green and grey = bounce / shield
	//Will also determine the look?

	
	private Rectangle boundingRect;
	
	private Point2D.Double bulletPoint;
	
	private ArrayList<Point2D.Double> prevClientLocs;
	private ArrayList<Point2D.Double> prevClientMouseLocs;
	
	public Ghost(PImage img, double x, double y) {
		super(img, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		bulletPoint = new Point2D.Double(x + PLAYER_WIDTH + 5, y + PLAYER_HEIGHT - 25);
		boundingRect = new Rectangle(getBoundRect());
		prevClientLocs = new ArrayList<Point2D.Double>();
		prevClientMouseLocs = new ArrayList<Point2D.Double>();
	}

	// METHODS
	public String toString() {
		//TODO make it into a string
		return "";
	}
	
	
	public void draw(PApplet drawer) {
		// change where the bullet hole is
		double angle = getDirection();


		// draw the player
		drawer.pushMatrix();
		drawer.translate((float) (x + PLAYER_WIDTH / 2), (float) (y + PLAYER_HEIGHT / 2));
		drawer.rotate((float) angle);
		drawer.image(getImage(), (float) (- PLAYER_WIDTH / 3), (float) (- PLAYER_HEIGHT / (1.4)),(float)width,(float)height);
		drawer.popMatrix();

		
		
		// Drawing the bounding rectangle
		/*
		drawer.noFill();
		drawer.rectMode(PApplet.CORNER);
		boundingRect = getBoundRect();
		drawer.rect((float)boundingRect.getX(), (float)boundingRect.getY(), (float)boundingRect.getWidth(), (float)boundingRect.getHeight());
		*/
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
		Rectangle rect = new Rectangle((int) x + PLAYER_WIDTH / 12, (int) y - PLAYER_WIDTH / 12, PLAYER_WIDTH * 5/7, PLAYER_WIDTH * 5/7);
		return rect;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

}
