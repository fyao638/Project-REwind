package sprites.player;

import java.util.ArrayList;

import processing.core.PImage;
import sprites.projectile.BouncingBullet;
import sprites.projectile.Bullet;
import sprites.projectile.Projectile;

public class Technician extends Player{

	public Technician(PImage img, int x, int y) {
		super(img, x, y);
	}
	public Bullet shoot(PImage img) {
		return super.shoot(img);
	}
	public ArrayList<Projectile> secondary(PImage img) {
		BouncingBullet b = new BouncingBullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 9.25);
		ArrayList<Projectile> bounce = new ArrayList<Projectile>();
		bounce.add(b);
		return bounce;
	}
	public void shiftAbility() {
		
	}
	public void rewind() {
		
	}
	
//	public int getType() {
//		return 3;
//	}

}
