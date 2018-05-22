package sprites.player;

import java.util.ArrayList;

import clientside.DrawingSurface;
import clientside.PlayScreen;
import processing.core.PImage;
import sprites.projectile.Bullet;
import sprites.projectile.Grenade;
import sprites.projectile.Molotov;
import sprites.projectile.Projectile;

/**
 * 
 * @author Aakarsh Anand
 * This class represents one of the player classes, demolitions.
 *
 */
public class Demolitions extends Player{

	
	private int timesShot;
	
	public Demolitions(PImage img, int x, int y) {
		super(img, x, y, 2);
	}
	public Bullet shoot(PImage img) {
		return super.shoot(img);
	}
	public ArrayList<Projectile> shiftAbility(PImage img) {
		ArrayList<Projectile> grenade = new ArrayList<Projectile>();
		Grenade g = new Grenade(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10, 2);
		grenade.add(g);
		return grenade;
	}
	public ArrayList<Projectile> secondary(PImage img) {
		ArrayList<Projectile> molotov = new ArrayList<Projectile>();
		Molotov g = new Molotov(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10, 3);
		molotov.add(g);
		return molotov;
	}
	
	public int timesShot() {
		return timesShot;
	}
	public void rewind() {
		
	}
	
}
