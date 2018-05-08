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
	
	private boolean canMove;

	private PImage img;
	private double dir;
	
	public Player(PImage img, int x, int y) {
		super(img, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		canMove = true;
		dir = 0;
		this.img = img;
	}

	// METHODS
	public void walk(int xDir, int yDir) {
		if(canMove) {
			this.moveByAmount(xDir * 5, yDir * 5);
		}
	}
	// Dunno what to do with this
	public Bullet shoot(int mouseX, int mouseY, DrawingSurface d) {
		double newXVel =  - (getX() - mouseX) ;
		double newYVel =  - (getY() - mouseY) ;
		
		double angle = Math.atan(newYVel / newXVel);
		
		if(newXVel < 0) {
			angle += Math.PI;
		}
		
		return new Bullet(d.getAssets().get(2), getX(), getY(), 10 * Math.cos(angle), 10 * Math.sin(angle));
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
		//drawer.rotate((float) dir);
	}
	
	public void turnToMouse(int mouseX, int mouseY) {
		this.turnToward(mouseX, mouseY);
	}
	
	public void rewind(Point2D.Double p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public void act(ArrayList<Shape> obstacles) {
		// FALL (and stop when a platform is hit)
		for(Shape s: obstacles) {
			if(s.getBounds().intersects(this.getX(), this.getY(), PLAYER_WIDTH, PLAYER_HEIGHT)) {
				
				canMove = false;
				
				Rectangle obsBounds = s.getBounds();
				double obsWidth = obsBounds.getWidth();
				double obsHeight = obsBounds.getHeight();
				double obsX = obsBounds.getX();
				double obsY = obsBounds.getY();
				
				boolean isXBetween = (obsX + obsWidth > getX() && obsX < getX() ) || (getX() + PLAYER_WIDTH > obsX && getX() + PLAYER_WIDTH < obsX + obsWidth);
				boolean isYBetween = (obsY + obsHeight > getY() && obsY < getY() ) || (getY() + PLAYER_HEIGHT > obsY && getY() + PLAYER_HEIGHT < obsY + obsHeight);
				
				//BROKEN
				
				if(isXBetween) {
					if( obsY > getY()) {
						moveToLocation(getX(), obsY - PLAYER_HEIGHT);
					}
					else{
						moveToLocation(getX(), obsY + obsHeight);
					}
				}
				else {
					if( obsX > getX()) {
						moveToLocation(obsX - PLAYER_WIDTH, getY());
					}
					else{
						moveToLocation(obsX + obsWidth, getY());
					}
				}
				
				
			}
			else {
				canMove = true;
			}
		}
	}
}
