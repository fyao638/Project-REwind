package sprites.player;

import java.util.ArrayList;

import processing.core.PImage;
import sprites.projectile.BouncingBullet;
import sprites.projectile.Bullet;

public class Technician extends Player{

	public Technician(PImage img, int x, int y) {
		super(img, x, y);
	}
	public Bullet shoot(PImage img) {
		return super.shoot(img);
	}
	public ArrayList<Bullet> secondary(PImage img) {
		BouncingBullet b = new BouncingBullet(img, this.getBulletPoint().getX(), this.getBulletPoint().getY(), this.getDirection(), 10);
		ArrayList<Bullet> bounce = new ArrayList<Bullet>();
		bounce.add(b);
		return bounce;
	}
	public void shiftAbility() {
		
	}
	public void rewind() {
		
	}

}
