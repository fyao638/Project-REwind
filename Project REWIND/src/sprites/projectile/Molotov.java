package sprites.projectile;

import java.util.ArrayList;

import clientside.DrawingSurface;
import gui.ScaleImage;
import processing.core.PApplet;
import processing.core.PImage;
import sprites.Particle;
import sprites.obstacles.Obstacle;
import sprites.player.Player;

public class Molotov extends Projectile{
	
	private PImage img;

	public static final int GRENADE_WIDTH = 40;
	public static final int GRENADE_HEIGHT = 40;
	public static final double PI = Math.PI;
	
	private double speed;
	
	private ArrayList<Particle> flames;
	private int drawn;
	private int flameTimer;
	private boolean isActive;
	
	private ArrayList<Particle> particles;
	
	public Molotov(ScaleImage image, double x, double y, double dir, double speed) {
		super(image, x, y, GRENADE_WIDTH, GRENADE_HEIGHT, dir, speed, 3);
		flames = new ArrayList<Particle>();
		particles = new ArrayList<Particle>();
		flameTimer = 0;
		drawn = 0;
		isActive = true;
//		double i = Math.random();
//		if (i < 0.5)
//			turn(dir + (Math.random()/50.0));
//		else
//			turn(dir - (Math.random()/50.0));
		turn(dir);
		
		this.speed = speed;
		
		
	}
	
	public void setup(DrawingSurface drawer) {
		for(int i = 0; i < 10; i++) {
			flames.add(new Particle(getImage(),(int) - GRENADE_WIDTH / 3 - 50,(int) - GRENADE_HEIGHT/ 2 - 50,(int)GRENADE_HEIGHT + 100,(int)GRENADE_HEIGHT + 100, 5));
		}
		for(int i = 10; i < 25; i++) {
			flames.add(new Particle(getImage(),(int) - GRENADE_WIDTH / 3 - 50,(int) - GRENADE_HEIGHT/ 2 - 50,(int)GRENADE_HEIGHT + 100,(int)GRENADE_HEIGHT + 100, 5));
		}
		
	}
	
	public void act() {
		moveByAmount(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));

	}
	
	// return true if it hits an obstacle, false if otherwise
	public boolean checkObstacles(ArrayList<Obstacle> obstacles) {
		double x = 0.5;
		if (speed < 0)
			speed = 0;
		if (speed > 0)
			speed -= Math.pow(x, 3);
		x += 0.001;
		return false;
	}
		
	public int checkPlayer(Player player) {
//		for(int i = 0; i < 4; i++) {
//			if(player.intersects(this.getX(),this.getY(), GRENADE_WIDTH,  GRENADE_HEIGHT) && this.speed == 0) {
//				return true;
//						
//			}
//		}
		if(player.intersects((x + GRENADE_WIDTH / 2) - (GRENADE_WIDTH / 3 + 50), (y + GRENADE_HEIGHT / 2) - (GRENADE_HEIGHT/ 2 + 50), GRENADE_HEIGHT + 100, GRENADE_HEIGHT + 100) && this.speed == 0) {
			return 1;
		}
		
		return 0;
	}
	
	public void draw(PApplet drawer) {
		if(drawn == 0) {
			for(int i = 0; i < 10; i++) {
				//flames.add(new Particle(getImage(),(int) - GRENADE_WIDTH / 3 - 50,(int) - GRENADE_HEIGHT/ 2 - 50,(int)GRENADE_HEIGHT + 100,(int)GRENADE_HEIGHT + 100, 5));
			}
			for(int i = 10; i < 25; i++) {
				//flames.add(new Particle(getImage(), (int) - GRENADE_WIDTH / 3 - 50,(int) - GRENADE_HEIGHT/ 2 - 50,(int)GRENADE_HEIGHT + 100,(int)GRENADE_HEIGHT + 100, 5));
			}
			//System.out.println(flames);
		}
		
		if (particles.size() > 0) {
			for(int i = 0; i < particles.size(); i++) {
					particles.get(i).draw(drawer);
					if (!particles.get(i).act()) {
						particles.remove(i);
					}
			}
		}
		
		if (flames.size() > 0) {
			for(int i = 0; i < flames.size(); i++) {
				flames.get(i).draw(drawer);
					if (!flames.get(i).act()) {
						flames.remove(i);
					}
			}
		}
		
		if(this.visible()) {

			particles.add(new Particle(getImage(), x + getWidth() / 2, y + getHeight() / 2, 5, 5, 4));
			drawer.pushMatrix();
			drawer.translate((float) (x + GRENADE_WIDTH / 2), (float) (y + GRENADE_HEIGHT / 2));
			drawer.rotate((float) getDirection());
			if(this.speed != 0) {
				getImage().draw(drawer, (int) - GRENADE_WIDTH / 3,(int) - GRENADE_HEIGHT/ 2,(int)GRENADE_HEIGHT,(int)GRENADE_HEIGHT);
				//drawer.image(getImage(),(int) - GRENADE_WIDTH / 3,(int) - GRENADE_HEIGHT/ 2,(int)GRENADE_HEIGHT,(int)GRENADE_HEIGHT);
			}
			else if (flameTimer < 200) {

				for (int i = 0; i < (int) (10 + Math.random() * 10); i++) {
				flames.add(new Particle(getImage(),(int) (x + GRENADE_WIDTH / 2) - GRENADE_WIDTH / 3 - 50,(int) (y + GRENADE_HEIGHT / 2) - GRENADE_HEIGHT/ 2 - 50,(int)GRENADE_HEIGHT + 100,(int)GRENADE_HEIGHT + 100, 5));
				}
				flameTimer++;
			}
			else {
				isActive = false;
				this.setVisibility(false);
			}
			drawer.popMatrix();
		}
		drawn++;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return isActive;
	}

}
