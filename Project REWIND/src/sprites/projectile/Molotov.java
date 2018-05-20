package sprites.projectile;

import java.util.ArrayList;

import clientside.DrawingSurface;
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
	
	private ArrayList<PImage> flames;
	private int explosionFrame, times, cycles, drawn;
	
	private ArrayList<Particle> particles;
	
	public Molotov(PImage image, double x, double y, double dir, double speed) {
		super(image, x, y, GRENADE_WIDTH, GRENADE_HEIGHT, dir, speed);
		flames = new ArrayList<PImage>();
		particles = new ArrayList<Particle>();
		explosionFrame = 0;
		times = 0;
		cycles = 0;
		drawn = 0;
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
			flames.add(drawer.loadImage("molotovGif/frame_0" + i + "_delay-0.01s.gif"));
		}
		for(int i = 10; i < 25; i++) {
			flames.add(drawer.loadImage("molotovGif/frame_" + i + "_delay-0.01s.gif"));
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
		
	public boolean checkPlayer(Player player) {
		for(int i = 0; i < 4; i++) {
			if(player.intersects(this.getX(),this.getY(), GRENADE_WIDTH,  GRENADE_HEIGHT) && this.speed == 0) {
				return true;
						
			}
		}
		return false;
	}
	
	public void draw(PApplet drawer) {
		if(drawn == 0) {
			for(int i = 0; i < 10; i++) {
				flames.add(drawer.loadImage("molotovGif/frame_0" + i + "_delay-0.01s.gif"));
			}
			for(int i = 10; i < 25; i++) {
				flames.add(drawer.loadImage("molotovGif/frame_" + i + "_delay-0.01s.gif"));
			}
			System.out.println(flames);
		}
		
		if (particles.size() > 0) {
			for(int i = 0; i < particles.size(); i++) {
					particles.get(i).draw(drawer);
					if (!particles.get(i).act()) {
						particles.remove(i);
					}
			}
		}
		
		if(this.visible()) {

			particles.add(new Particle(getImage(), x + getWidth() / 2, y + getHeight() / 2, 5, 5, 2));
			drawer.pushMatrix();
			drawer.translate((float) (x + GRENADE_WIDTH / 2), (float) (y + GRENADE_HEIGHT / 2));
			drawer.rotate((float) getDirection());
			if(this.speed != 0) {
				drawer.image(getImage(),(int) - GRENADE_WIDTH / 3,(int) - GRENADE_HEIGHT/ 2,(int)GRENADE_HEIGHT,(int)GRENADE_HEIGHT);
			}
			else if (cycles != 25) {
				drawer.image(flames.get(explosionFrame),(int) - GRENADE_WIDTH / 3 - 50,(int) - GRENADE_HEIGHT/ 2 - 50,(int)GRENADE_HEIGHT + 100,(int)GRENADE_HEIGHT + 100);
				explosionFrame++;
				times++;
				if(times == 24) {
					times = 19;
					explosionFrame = 19;
					cycles++;
				}
					
			}
			else
				this.setVisibility(false);
			drawer.popMatrix();
		}
		drawn++;
	}

}
