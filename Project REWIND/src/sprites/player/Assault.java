package sprites.player;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PImage;
import sprites.obstacles.Obstacle;
import sprites.projectile.Bullet;
import sprites.projectile.Projectile;

/**
 * 
 * @author Frank Yao
 * This class represents one of the player classes, assault.
 *
 */
public class Assault extends Player{

	public Assault(PImage img, int x, int y) {
		super(img, x, y);
	}
	public Bullet shoot(PImage img) {
		return super.shoot(img);
	}
	public ArrayList<Projectile> secondary(PImage img) {
		ArrayList<Projectile> fan = new ArrayList<Projectile>();
		fan.add(new Bullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10));
		fan.add(new Bullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection() + 0.25, 10));
		fan.add(new Bullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection() - 0.25 , 10));
		fan.add(new Bullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection() - 0.125 , 10));
		fan.add(new Bullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection() + 0.125 , 10));
		return fan;
	}
	public void shiftAbility(ArrayList<Obstacle> obstacles) {
		double amountX = 110 * Math.cos(super.getDirection());
		double amountY = 110 * Math.sin(super.getDirection());
		
		boolean canFlash = true;
		
		for(Shape s : obstacles) {
			if(s.getBounds().intersects(new Rectangle((int)(getX() + amountX),(int)(getY() + amountY), PLAYER_WIDTH, PLAYER_HEIGHT))) {
				canFlash = false;
			}
		}
		if(canFlash) {
			super.moveToLocation(x + amountX, y + amountY);	
		}
	}
	
//	public int getType() {
//		return 1;
//	}
}
