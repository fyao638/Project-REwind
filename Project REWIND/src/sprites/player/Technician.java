package sprites.player;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import sprites.projectile.BouncingBullet;
import sprites.projectile.Bullet;
import sprites.projectile.Projectile;

public class Technician extends Player {
	// could be used when using hit detection?
	private boolean hasShield;
	// timer for the shield
	private int shieldTimer;
	public Technician(PImage img, int x, int y) {
		super(img, x, y, 3);
		hasShield = false;
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
	public void draw(PApplet drawer) {
		shieldTimer++;
		super.draw(drawer);
		// 5000 very much subject to change
		if (shieldTimer > 5000) {
			hasShield = false;
		}
		else {
			// draw an ellipse that shields the player
			Rectangle shieldRect = getShield();drawer.pushMatrix();
			drawer.translate((float) (x + PLAYER_WIDTH / 2), (float) (y + PLAYER_HEIGHT / 2));
			drawer.ellipse(shieldRect.x, shieldRect.y, shieldRect.width, shieldRect.height);
			drawer.popMatrix();
		}
	}
	// shield ability
	public void shiftAbility() {
		hasShield = true;
		shieldTimer = 0;
	}
	public void rewind() {
		
	}
	
	// return a rectangle with the appropriate variables needed for a shield
	// doesn't use Ellipse because I'm not familiar with Ellipse2D and it's nice to have 
		//a sort of 'bounding' rectangle for shield
	private Rectangle getShield() {
		Rectangle rect = new Rectangle((int) x, (int) y - PLAYER_WIDTH / 5, PLAYER_WIDTH, PLAYER_WIDTH);
		return rect;
	}
	
//	public int getType() {
//		return 3;
//	}

}
