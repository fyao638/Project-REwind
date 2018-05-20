package sprites.player;

import java.util.ArrayList;

import processing.core.PImage;
import sprites.projectile.Bullet;
import sprites.projectile.Grenade;
import sprites.projectile.Projectile;

/**
 * 
 * @author Aakarsh Anand
 * This class represents one of the player classes, demolitions.
 *
 */
public class Demolitions extends Player{

	public Demolitions(PImage img, int x, int y) {
		super(img, x, y, 2);
	}
	public Bullet shoot(PImage img) {
		return super.shoot(img);
	}
	public ArrayList<Projectile> shiftAbility(PImage img) {
		ArrayList<Projectile> grenade = new ArrayList<Projectile>();
		Grenade g = new Grenade(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10);
		grenade.add(g);
		
		return grenade;
	}
	public ArrayList<Projectile> secondary(PImage img) {
		ArrayList<Projectile> burst = new ArrayList<Projectile>();
		burst.add(new Bullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10));
		burst.add(new Bullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10));
		burst.add(new Bullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10));
		return burst;
	}
	public void rewind() {
		
	}
	
}
