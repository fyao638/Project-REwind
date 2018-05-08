package sprites;

import java.awt.Image;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PImage;

public class Sprite extends Rectangle2D.Double {
	private double xVel;
	private double yVel;
	private double dir;
	private boolean isVisible;
	
	private PImage img;
	
	//Constructors
	
	public Sprite(PImage image, double x, double y, double w, double h) {
		super(x,y,w,h);
		this.img = image;
		dir = 0;
		xVel = 0;
		yVel = 0;
		isVisible = true;
	}
	
	// METHODS
		public void moveToLocation(double x, double y) {
			super.x = x;
			super.y = y;
		}
		
		public void moveByAmount(double x, double y) {
			super.x += x;
			super.y += y;
		}
		
		public void applyWindowLimits(int windowWidth, int windowHeight) {
			x = Math.min(x,windowWidth-width);
			y = Math.min(y,windowHeight-height);
			x = Math.max(0,x);
			y = Math.max(0,y);
		}
		
		
		public void draw(PApplet drawer) {
			if (isVisible) {
				drawer.image(img,(int)x,(int)y,(int)width,(int)height);
			}
		}
		
		public void turn(double dir) {
			this.dir = dir;
		}

		public void turnToward(float x, float y) {
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
		
		public double getDirection() {
			return dir;
		}
		
		public PImage getImage() {
			return img;
		}
		
		public boolean isPointInImage(double mouseX, double mouseY) {
			if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height)
				return true;
			return false;
		}
		
		public void setVisibility(boolean visibility) {
			isVisible = visibility;
		}
		
		public boolean visible() {
			return isVisible;
		}
		public double getXVel() {
			return xVel;
		}
		public void setXVel(double xVel) {
			this.xVel = xVel;
		}
		public double getYVel() {
			return yVel;
		}
		public void setYVel(double yVel) {
			this.yVel = yVel;
		}
}
