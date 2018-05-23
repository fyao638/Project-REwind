package sprites.player;

import java.util.ArrayList;

import clientside.DrawingSurface;
import clientside.PlayScreen;
import gui.ScaleImage;
import sprites.projectile.Bullet;
import sprites.projectile.Grenade;
import sprites.projectile.Molotov;
import sprites.projectile.Projectile;

/**
 * 
 * @author Aakarsh Anand
 * @version 1.0
 * This class represents one of the player classes, demolitions.
 * - Secondary: molotov
 * - Shift: Grenade
 */
public class Demolitions extends Player{

	
	private int timesShot;
	
	public Demolitions(ScaleImage img, int x, int y) {
		super(img, x, y, 2);
	}
	public Bullet shoot(ScaleImage img) {
		return super.shoot(img);
	}
	public ArrayList<Projectile> shiftAbility(ScaleImage img) {
		ArrayList<Projectile> grenade = new ArrayList<Projectile>();
		Grenade g = new Grenade(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10);
		grenade.add(g);
		return grenade;
	}
	public ArrayList<Projectile> secondary(ScaleImage img) {
		ArrayList<Projectile> molotov = new ArrayList<Projectile>();
		Molotov g = new Molotov(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 8.25);
		molotov.add(g);
		return molotov;
	}
	
	public int timesShot() {
		return timesShot;
	}
	public void rewind() {
		
	}
	
}
