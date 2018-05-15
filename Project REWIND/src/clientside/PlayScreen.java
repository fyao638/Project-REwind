package clientside;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import gui.Hud;
import maps.Map;
import network.frontend.NetworkDataObject;
import network.frontend.NetworkListener;
import network.frontend.NetworkMessenger;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import sprites.Particle;
import sprites.player.Assault;
import sprites.player.Demolitions;
import sprites.player.Player;
import sprites.player.Technician;
import sprites.projectile.Bullet;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class represents the screen where the actual playing occurs. It is drawn by DrawingSurface.
 */
public class PlayScreen implements NetworkListener{
	
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Player p1Ghost;
	private Player p1;
	private ArrayList<Particle> particles;
	
	private ArrayList<Bullet> bullets;
	
	private ArrayList<PImage> assets;
	
	private ArrayList<Point2D.Double> prevLocs;
	private ArrayList<Point2D.Double> prevMouseLocs;
	
	private NetworkMessenger nm;
	
	//PACKETS
	
	private Map map;
	private Hud hud;

	private long shotReadyTime, rewindReadyTime, secondaryReadyTime, shiftReadyTime, ghostReappearTime;
	
	private float abilWidth, abilHeight;
	
	
	public PlayScreen() {
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
		particles = new ArrayList<Particle>();
		abilWidth = 100;
		abilHeight = 100;

	}
	public void spawnNewPlayer() {
		p1 = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
	}
	
	public void spawnNewGhost() {
		p1Ghost = new Player(assets.get(1), (int)prevLocs.get(0).getX(), (int)prevLocs.get(0).getY());
	}
	
	public void setup(DrawingSurface drawer) {
		drawer.noStroke();
		assets.add(drawer.loadImage("assets/player.png"));
		assets.add(drawer.loadImage("assets/ghost.png"));
		assets.add(drawer.loadImage("assets/bullet.png"));
		assets.add(drawer.loadImage("assets/star.png"));
		assets.add(drawer.loadImage("assets/crosshair.png"));
		assets.add(drawer.loadImage("assets/time.png"));
		assets.add(drawer.loadImage("assets/starIcon.png"));
		assets.add(drawer.loadImage("assets/flash.png"));
		assets.add(drawer.loadImage("assets/wall.png"));
		assets.add(drawer.loadImage("assets/wall2.png"));
		assets.add(drawer.loadImage("assets/particle.png"));
		assets.add(drawer.loadImage("assets/bounceLogo.png"));
		assets.add(drawer.loadImage("assets/grenade.png"));
		
		map = new Map(assets.get(8), assets.get(9));
		spawnNewPlayer();
		
		Point2D.Double p = new Point2D.Double(p1.getX(), p1.getY());
		prevLocs.add(p);
		
		spawnNewGhost();
	}
	int timer = 0;
	public void draw(DrawingSurface drawer) {
		
		//NETWORKING STUFF
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

		if(drawer.isPressed(KeyEvent.VK_A)) {
			p1.walk(-1, 0, map.getObstacles());
			if(drawer.getNetM() != null){
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, 1738);
			}
		}
		if (drawer.isPressed(KeyEvent.VK_D))
			p1.walk(1, 0, map.getObstacles());
		if (drawer.isPressed(KeyEvent.VK_W))
			p1.walk(0, -1, map.getObstacles());
		if (drawer.isPressed(KeyEvent.VK_S))
			p1.walk(0, 1, map.getObstacles());
		if (drawer.isPressed(KeyEvent.VK_R)) {
			if(rewindReadyTime -drawer.millis() <= 0) {
				p1.moveToLocation(prevLocs.get(0).getX(), prevLocs.get(0).getY());
				//set cooldowns
				rewindReadyTime = drawer.millis() + 15000;
				ghostReappearTime = drawer.millis() + 2000;
				
				//rewind cooldowns
				shotReadyTime += 2000;
				secondaryReadyTime += 2000;
				shiftReadyTime += 2000;
				
			}
		}
		if (drawer.isPressed(KeyEvent.VK_SHIFT)) {
			if(shiftReadyTime - drawer.millis() <= 0) {
				
				// create particles, maybe find a cleaner way to do this later
				for (int i = 0; i < (int) (10 + Math.random() * 10); i++) {
					particles.add(new Particle(assets.get(10), p1.x + p1.getWidth() / 2, p1.y + p1.getHeight() / 2, 20, 20));
				}
				// casting this for now... But I need a better fix
				((Assault) p1).shiftAbility(map.getObstacles());
				
				shiftReadyTime = drawer.millis() + 7000;
				
			}
		}
		// 
		//
		//Testing
		//if(drawer.isPressed(KeyEvent.VK_SPACE)) {
		//	p1.changePlayerType(1);
		//}
		
		
		
		// Draw abilities
		if(drawer.mousePressed) {
			if(drawer.mouseButton == PConstants.LEFT) {
				if(shotReadyTime - drawer.millis() <= 0) {
					bullets.add(p1.shoot(assets.get(2)));
					shotReadyTime = drawer.millis() + 1000;
				}
			}
			else if(drawer.mouseButton == PConstants.RIGHT) {
				if(p1.getType() == 1) {
					if(secondaryReadyTime - drawer.millis() <= 0) {
						if(p1.getType() == 1) {
							// casting this for now... But I need a better fix
							ArrayList<Bullet> fan = ((Assault)p1).secondary(assets.get(3));
							for(Bullet b : fan) {
								bullets.add(b);
							}
							secondaryReadyTime = drawer.millis() + 7000;
						}
					}
				}
				else {
					if(secondaryReadyTime - drawer.millis() <= 0) {
						bullets.add(((Demolitions)p1).secondary(assets.get(2)).get(0));
						secondaryReadyTime = drawer.millis() + 7000;
					}
				}
			}
		}

		

		
		drawer.fill(100);
		map.draw(drawer);
		
		if (bullets.size() > 0) {
			for(int i = 0; i < bullets.size(); i++) {
					bullets.get(i).act();
					bullets.get(i).draw(drawer);
					if (bullets.get(i).checkObstacles(map.getObstacles())) {
						bullets.remove(i);
					}
			}
		}
		
		// draw the players after the bullets so the bullets don't appear above the gun
		if(ghostReappearTime - drawer.millis() < 0) {
			p1Ghost.draw(drawer);
		}
		p1.draw(drawer);
		
		if (particles.size() > 0) {
			for(int i = 0; i < particles.size(); i++) {
					particles.get(i).draw(drawer);
					if (!particles.get(i).act()) {
						particles.remove(i);
					}
			}
		}
		//assets dont change, so dont take the in draw
		hud.draw(drawer, p1, assets.get(4), assets.get(5), assets.get(6),assets.get(7), assets.get(11), shotReadyTime, rewindReadyTime, secondaryReadyTime, shiftReadyTime, drawer.millis(), abilWidth, abilHeight);
		
		
		
		timer++;
	
		if(!drawer.isOffline()) {
			
		}
	}
	
	// GET DATA METHODS
	public Player getPlayer() {
		return p1;
	}
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
		
	}
	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		// TODO Auto-generated method stub
		
	}
}

