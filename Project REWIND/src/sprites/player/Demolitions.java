package sprites.player;

import java.util.ArrayList;

import processing.core.PImage;
import sprites.projectile.Bullet;
import sprites.projectile.Grenade;

/**
 * 
 * @author Aakarsh Anand
 * This class represents one of the player classes, demolitions.
 *
 */
public class Demolitions extends Player{
	public Demolitions(PImage img, int x, int y) {
		super(img, x, y);
	}
	public Bullet shoot(PImage img) {
		return super.shoot(img);
	}
	public ArrayList<Bullet> secondary(PImage img) {
		ArrayList<Bullet> grenade = new ArrayList<Bullet>();
		Grenade g = new Grenade(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10);
		grenade.add(g);
		return grenade;
	}
	public void shiftAbility() {
		
	}
	public void rewind() {
		
	}
}
