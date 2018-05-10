package testers;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import sprites.Bullet;
import sprites.Player;

public class PlayScreen{
	
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Player p1, p1Ghost;
	
	private ArrayList<Bullet> bullets;
	
	private ArrayList<PImage> assets;
	
	private ArrayList<Point2D.Double> prevLocs;
	private ArrayList<Point2D.Double> prevMouseLocs;
	
	private Map map;
	private Hud hud;

	private long shotReadyTime, rewindReadyTime, secondaryReadyTime, shiftReadyTime, ghostReappearTime;
	
	private float abilWidth, abilHeight;
	
	
	public PlayScreen() {
		super();
		assets = new ArrayList<PImage>();
		bullets = new ArrayList<Bullet>();
		hud = new Hud();
		prevLocs = new ArrayList<Point2D.Double>();
		shotReadyTime = 0;
		rewindReadyTime = 0;
		secondaryReadyTime = 0;
		shiftReadyTime = 0;
		ghostReappearTime = 0;
		prevMouseLocs = new ArrayList<Point2D.Double>();
		abilWidth = 100;
		abilHeight = 100;
	}
	public void spawnNewPlayer() {
		p1 = new Player(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
	}
	
	public void spawnNewGhost() {
		p1Ghost = new Player(assets.get(1), (int)prevLocs.get(0).getX(), (int)prevLocs.get(0).getY());
	}
	
	public void setup(DrawingSurface drawer) {
		drawer.noStroke();
		assets.add(drawer.loadImage("player.png"));
		assets.add(drawer.loadImage("ghost.png"));
		assets.add(drawer.loadImage("bullet.png"));
		assets.add(drawer.loadImage("star.png"));
		assets.add(drawer.loadImage("crosshair.png"));
		assets.add(drawer.loadImage("time.png"));
		assets.add(drawer.loadImage("starIcon.png"));
		assets.add(drawer.loadImage("flash.png"));
		assets.add(drawer.loadImage("wall.png"));
		assets.add(drawer.loadImage("wall2.png"));
		
		map = new Map(assets.get(8), assets.get(9));
		spawnNewPlayer();
		
		Point2D.Double p = new Point2D.Double(p1.getX(), p1.getY());
		prevLocs.add(p);
		
		spawnNewGhost();
	}
	int timer = 0;
	public void draw(DrawingSurface drawer) {

		Point2D.Double p = new Point2D.Double(p1.getX(), p1.getY());
		prevLocs.add(p);
		if (prevLocs.size() > 120)
			prevLocs.remove(0);
		

		Point2D.Double pMouse = new Point2D.Double(drawer.mouseX, drawer.mouseY);
		prevMouseLocs.add(pMouse);
		if (prevMouseLocs.size() > 120)
			prevMouseLocs.remove(0);
		
		p1Ghost.setX((int)prevLocs.get(0).getX());
		p1Ghost.setY((int)prevLocs.get(0).getY());
		
		drawer.background(128,128,128);  

		float ratioX = (float)drawer.width/DRAWING_WIDTH;
		float ratioY = (float)drawer.height/DRAWING_HEIGHT;

		drawer.scale(ratioX, ratioY);
		
		p1.turnToward(drawer.mouseX / ratioX, drawer.mouseY / ratioY);
		p1Ghost.turnToward((float)prevMouseLocs.get(0).getX() / ratioX, (float)prevMouseLocs.get(0).getY() / ratioY);

		if(drawer.isPressed(KeyEvent.VK_A))
			p1.walk(-1, 0, map.getObstacles());
		if (drawer.isPressed(KeyEvent.VK_D))
			p1.walk(1, 0, map.getObstacles());
		if (drawer.isPressed(KeyEvent.VK_W))
			p1.walk(0, -1, map.getObstacles());
		if (drawer.isPressed(KeyEvent.VK_S))
			p1.walk(0, 1, map.getObstacles());
		if (drawer.isPressed(KeyEvent.VK_R)) {
			if(rewindReadyTime -drawer.millis() <= 0) {
				p1.moveToLocation(prevLocs.get(0).getX(), prevLocs.get(0).getY());
				
				rewindReadyTime = drawer.millis() + 15000;
				ghostReappearTime = drawer.millis() + 2000;
			}
		}
		if (drawer.isPressed(KeyEvent.VK_SHIFT)) {
			if(shiftReadyTime - drawer.millis() <= 0) {
				
				p1.shiftAbility(map.getObstacles());
				
				shiftReadyTime = drawer.millis() + 7000;
				
			}
		}
			
		//TESTING HEALTH
		if(drawer.isPressed(KeyEvent.VK_SPACE)) { 
			p1.changeHealth(-1);
			System.out.println(p1.getHealth());
		}

		// Draw abilities
		
		if(drawer.mousePressed) {
			if(drawer.mouseButton == PConstants.LEFT) {
				if(shotReadyTime - drawer.millis() <= 0) {
					bullets.add(p1.shoot(assets.get(2)));
					shotReadyTime = drawer.millis() + 1000;
				}
			}
			else if(drawer.mouseButton == PConstants.RIGHT) {
				if(secondaryReadyTime - drawer.millis() <= 0) {
					if(p1.getType() == 1) {
						ArrayList<Bullet> fan = p1.secondaryShoot(assets.get(3));
						for(Bullet b : fan) {
							bullets.add(b);
						}
						secondaryReadyTime = drawer.millis() + 7000;
					}
				}
			}
			else if(drawer.mouseButton == PConstants.CENTER) {
				if(secondaryReadyTime - drawer.millis() <= 0) {
					bullets.add(p1.secondaryShoot2(assets.get(2)));
					secondaryReadyTime = drawer.millis() + 1000;
				}
			}
		}

		if (bullets.size() > 0) {
			for(int i = 0; i < bullets.size(); i++) {
					bullets.get(i).act();
					bullets.get(i).draw(drawer);
					if (bullets.get(i).checkObstacles(map.getObstacles())) {
						bullets.remove(i);
					}
			}
		}

		drawer.fill(100);
		map.draw(drawer);
		
		// draw the players after the bullets so the bullets don't appear above the gun
		if(ghostReappearTime - drawer.millis() < 0) {
			p1Ghost.draw(drawer);
		}
		p1.draw(drawer);

		hud.draw(drawer, p1.getHealth(), assets.get(4), assets.get(5), assets.get(6),assets.get(7), shotReadyTime, rewindReadyTime, secondaryReadyTime, shiftReadyTime, drawer.millis(), abilWidth, abilHeight);
		
		timer++;
	
	}
}

