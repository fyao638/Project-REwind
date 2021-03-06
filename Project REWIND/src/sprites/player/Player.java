package sprites.player;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

import clientside.DrawingSurface;
import clientside.PlayScreen;
import gui.ScaleImage;
import processing.core.PApplet;
import processing.core.PImage;
import sprites.Sprite;
import sprites.obstacles.Obstacle;
import sprites.projectile.Bullet;
import sprites.projectile.Projectile;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class represents a player in the game.
 */
public class Player extends Sprite {

	public static final int PLAYER_WIDTH = 60;
	public static final int PLAYER_HEIGHT = 40;
	private static final double BUL_ANGLE = Math.atan((PLAYER_HEIGHT - 20) / (2 * PLAYER_WIDTH) / 3);
	private static final double BUL_DISTANCE = PLAYER_WIDTH * 1.0 / 2;
	private int[] cooldowns;
	
	private int score;
	
	//This will be used to determine the abilities and secondary fire of the player as there be different ones
	// EX:
	// num = name = color scheme = secondary fire / shift ability
	// 1 = "thicc and slick" / assault = blue and black = shotgun / flash
	// 2 = "loud and proud" / demolition = red and black = burst(may be changed) / grenade
	// 3 = "400+ IQ plays" / technician = green and grey = bounce / shield
	//Will also determine the look?
	private int playerType, health, maxHealth;
	
	private Rectangle boundingRect;
	
	private Point2D.Double bulletPoint;
	
	public Player(ScaleImage img, double x, double y, int type) {
		super(img, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		/*0 - primary fire
		1 - secondary fire
		2 - rewind
		3 - shift
		4 - ghost respawn time*/
		cooldowns = new int[]{0,0,0,0,0};
		score = 0;
		bulletPoint = new Point2D.Double(x + PLAYER_WIDTH + 5, y + PLAYER_HEIGHT - 25);
		boundingRect = new Rectangle(getBoundRect());
		playerType = type;
		if (playerType == 1) {
			health = 4;
			maxHealth = health;
		}
		else if (playerType == 2) {
			health = 8;
			maxHealth = health;
		}
		else {
			health = 6;
			maxHealth = health;
		}
	}
	public void setCooldowns(int index, int newVal) {
		cooldowns[index] = newVal;
	}
	public int[] getCooldowns() { 
		return cooldowns;
	}

	// METHODS
	public String toString() {
		//TODO make it into a string
		return "";
	}
	
	public Bullet shoot(ScaleImage img) {
		return new Bullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10, 1);
	}
	public void win() {
		score++;
	}
	public int getScore() {
		return score;
	}
	public ArrayList<Projectile> secondary(ScaleImage img) {
		return null;
	}
	public int getType() {
		return playerType;
	}
	public void changePlayerType(int newType) {
		playerType = newType;
	}
	public int getHealth() {
		return health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public void changeHealth(int amount) {
	
		health += amount;
	}
	public void resetHealth() {
		if (playerType == 1) {
			health = 4;
		}
		else if (playerType == 2) {
			health = 8;
		}
		else {
			health = 6;
		}
	}
	
	public void setHealth(int amount) {
		health = amount;
	}
	
	public void draw(PApplet drawer) {
		// change where the bullet hole is
		double angle = getDirection();
		bulletPoint.setLocation(x - PLAYER_WIDTH / 10 + BUL_DISTANCE + Math.cos(angle + BUL_ANGLE) * (BUL_DISTANCE - 5), 
				y - PLAYER_HEIGHT / 2.35 + BUL_DISTANCE + Math.sin(angle + BUL_ANGLE) * (BUL_DISTANCE - 5));

		// draw the player
		drawer.pushMatrix();
		drawer.translate((float) (x + PLAYER_WIDTH / 2), (float) (y + PLAYER_HEIGHT / 2));
		drawer.rotate((float) angle);
		getImage().draw(drawer,(float) (- PLAYER_WIDTH / 3), (float) (- PLAYER_HEIGHT / (1.4)),(float)width,(float)height);
		//drawer.image(getImage(), (float) (- PLAYER_WIDTH / 3), (float) (- PLAYER_HEIGHT / (1.4)),(float)width,(float)height);
		/* Described below in Draw the hitbox section
		drawer.noFill();
		drawer.stroke(0);
		drawer.rectMode(PApplet.CORNER);
		hitBox = getHitBox(drawer);
		drawer.rect((float)(hitBox.getX() - (x + PLAYER_WIDTH / 2)), (float)(hitBox.getY() - (y + PLAYER_HEIGHT / 2)), (float)hitBox.getWidth(), (float)hitBox.getHeight());
		*/
		drawer.popMatrix();
		//the point you see kind of inside the gun (also visible on the ghost is where the bullet spawns
			//the bullet doesn't always seem to spawn there but I don't know the issue to that.
		// drawer.point((float) (bulletPoint.getX()), (float) bulletPoint.getY());
		
		
		// Drawing the bounding rectangle
		/*
		drawer.noFill();
		drawer.stroke(0);
		drawer.rectMode(PApplet.CORNER);
		boundingRect = getBoundRect();
		drawer.rect((float)boundingRect.getX(), (float)boundingRect.getY(), (float)boundingRect.getWidth(), (float)boundingRect.getHeight());
		*/
		// Draw the hitbox
			// if moved into the draw the  player section, works properly, but the hitBox 
			// variable probably contains a non-rotated rectangle (currently commented out)
		/*
		drawer.noFill();
		drawer.stroke(0);
		drawer.rectMode(PApplet.CORNER);
		hitBox = getHitBox(drawer);
		drawer.rect((float)(hitBox.getX()), (float)(hitBox.getY()), (float)hitBox.getWidth(), (float)hitBox.getHeight());
		*/
		

	}
	
	public void walk(int xDir, int yDir, ArrayList<Obstacle> obstacles) {
		if (playerType == 1) {
			if(canMove(xDir * 6, yDir * 6, obstacles)) {
				this.moveByAmount(xDir * 6, yDir * 6);
			}
		}
		else if (playerType == 2) {
			if(canMove(xDir * 4, yDir * 4, obstacles)) {
				this.moveByAmount(xDir * 4, yDir * 4);
			}
		}
		else {
			if(canMove(xDir * 6.5, yDir * 6.5, obstacles)) {
				this.moveByAmount(xDir * 6.5, yDir * 6.5);
			}
		}
	}
	
	private boolean canMove(double d, double e, ArrayList<Obstacle> obstacles) {
		double pX = getX();
		double pY = getY();
		for(Shape s: obstacles) {
			if (d != 0) {
				if (d > 0) {
					for (int i = 1; i <= d; i++) {
						
						moveToLocation(pX + i, pY);
						if(s.getBounds().intersects(boundingRect.getX(), boundingRect.getY(), boundingRect.width, boundingRect.height)) {
							moveToLocation(pX, pY);
							return false;
						}
					}
				}
				else {
					for (int i = -1; i >= d; i--) {
						moveToLocation(pX + i, pY);
						if(s.getBounds().intersects(boundingRect.getX(), boundingRect.getY(), boundingRect.width, boundingRect.height)) {
							moveToLocation(pX, pY);
							return false;
						}
					}
				}
			
			}
			if (e != 0) {
				if (e > 0) {
					for (int i = 1; i <= e; i++) {
						moveToLocation(pX, pY + i);
						if(s.getBounds().intersects(boundingRect.getX(), boundingRect.getY(), boundingRect.width, boundingRect.height)) {
							moveToLocation(pX, pY);
							return false;
						}
					}
				}
				else {
					for (int i = -1; i >= e; i--) {
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
		Rectangle rect = new Rectangle((int) x + PLAYER_WIDTH / 6, (int) y - PLAYER_WIDTH / 15, PLAYER_WIDTH * 5/7, PLAYER_WIDTH * 5/7);
		return rect;
	}
	
	// does not rotate properly, not sure why
	private Rectangle getHitBox(PApplet drawer) {
		drawer.pushMatrix();
		drawer.translate((float) (x + PLAYER_WIDTH / 2), (float) (y + PLAYER_HEIGHT / 2));
		drawer.rotate((float) getDirection());//doesn't work
		Rectangle rect = new Rectangle((int) x + PLAYER_WIDTH / 4, (int) y, PLAYER_WIDTH * 5/7, PLAYER_HEIGHT * 5/7);
		drawer.popMatrix();
		return rect;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
