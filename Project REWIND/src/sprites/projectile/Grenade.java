package sprites.projectile;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import clientside.DrawingSurface;
import processing.core.PApplet;
import processing.core.PImage;
import sprites.Particle;
import sprites.Sprite;
import sprites.obstacles.Obstacle;
import sprites.player.Player;

/**
 * 
 * @author Aakarsh Anand
 * This class represents the grenade shot by Demolitions.
 *
 */
public class Grenade extends Projectile{
	
	private PImage img;

	public static final int GRENADE_WIDTH = 40;
	public static final int GRENADE_HEIGHT = 40;
	public static final double PI = Math.PI;
	
	private double speed;
	
	private ArrayList<PImage> explosions;
	private int explosionFrame, times, cycles, drawn;
	private boolean isDone;
	private ArrayList<Particle> particles;

	private boolean isActive;
	
	private boolean affected;
	
	public Grenade(PImage image, double x, double y, double dir, double speed) {
		super(image, x, y, GRENADE_WIDTH, GRENADE_HEIGHT, dir, speed, 4);
		explosions = new ArrayList<PImage>();
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
		isActive = true;
		this.speed = speed;
		
		affected = false;
	}
	
	public void setup(DrawingSurface drawer) {
		System.out.println("i");
		explosions.add(drawer.loadImage("explosionGif/frame_0_delay-s.gif"));
		explosions.add(drawer.loadImage("explosionGif/frame_1_delay-s.gif"));
		explosions.add(drawer.loadImage("explosionGif/frame_2_delay-s.gif"));
		explosions.add(drawer.loadImage("explosionGif/frame_3_delay-s.gif"));
		explosions.add(drawer.loadImage("explosionGif/frame_4_delay-s.gif"));
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
			speed -= Math.pow(x, 2);
		x += 0.001;
		return false;
	}
		
	public int checkPlayer(Player player) {
		if (speed == 0 && cycles == 1) {
			for(int i = 0; i < 4; i++) {
				if(player.intersects((x + GRENADE_WIDTH / 2) - (GRENADE_WIDTH / 3 + 50), (y + GRENADE_HEIGHT / 2) - (GRENADE_HEIGHT/ 2 + 50), GRENADE_HEIGHT + 100, GRENADE_HEIGHT + 100) && this.speed == 0) {
					
					double pX = player.getCenterX();
					double pY = player.getCenterY();
					
					Point2D.Double playerCoord = new Point2D.Double(pX, pY);
					
					double grenX = (x + GRENADE_WIDTH / 2) - (GRENADE_WIDTH / 3 + 50) + (GRENADE_HEIGHT + 100) / 2;
					double grenY = (y + GRENADE_HEIGHT / 2) - (GRENADE_HEIGHT/ 2 + 50) + (GRENADE_HEIGHT + 100) / 2;
					
					Point2D.Double grenCoord = new Point2D.Double(grenX, grenY);
					
					double dist = grenCoord.distance(playerCoord);
					if (dist < 25)
						return 3;
					if (dist < 80)
						return 2;
					return 1;
				}
			}
		}
		return 0;
	}
	
	public void draw(PApplet drawer) {
		if(drawn == 0) {
			explosions.add(drawer.loadImage("explosionGif/frame_0_delay-s.gif"));
			explosions.add(drawer.loadImage("explosionGif/frame_1_delay-s.gif"));
			explosions.add(drawer.loadImage("explosionGif/frame_2_delay-s.gif"));
			explosions.add(drawer.loadImage("explosionGif/frame_3_delay-s.gif"));
			explosions.add(drawer.loadImage("explosionGif/frame_4_delay-s.gif"));
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
			else if (cycles != 5) {
				drawer.image(explosions.get(explosionFrame),(int) - GRENADE_WIDTH / 3 - 50,(int) - GRENADE_HEIGHT/ 2 - 50,(int)GRENADE_HEIGHT + 100,(int)GRENADE_HEIGHT + 100);
				times++;
				if(times == 3) {
					times = 0;
					cycles++;
					explosionFrame++;
				}
				if(explosionFrame == 5)
					explosionFrame = 0;
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
	
	public boolean hasAffected() {
		return affected;
	}
	
	public void changeAffected() {
		affected = true;
	}
}
