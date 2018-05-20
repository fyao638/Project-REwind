package clientside;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import gui.Hud;
import maps.Map;
import network.frontend.NetworkDataObject;
import processing.core.PConstants;
import processing.core.PImage;
import sprites.Particle;
import sprites.obstacles.Obstacle;
import sprites.player.Assault;
import sprites.player.Demolitions;
import sprites.player.Player;
import sprites.player.Technician;
import sprites.projectile.Projectile;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class represents the screen where the actual playing occurs. It is drawn by DrawingSurface.
 */
public class PlayScreen{
	
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Player p1Ghost;
	private Player hostPlayer, clientPlayer;
	private ArrayList<Particle> particles;
	private ArrayList<Projectile> bullets;
	private ArrayList<Projectile> otherBullets;
	private ArrayList<Player> players;
	private ArrayList<PImage> assets;
	
	private int hostType, clientType;
	private boolean hostSpawned, clientSpawned;
	
	private ArrayList<Point2D.Double> prevClientLocs;
	private ArrayList<Point2D.Double> prevClientMouseLocs;
	private ArrayList<Point2D.Double> prevEnemyLocs;
	
	private static final String messageTypeMove = "MOVE";
	private static final String messageTypeTurn = "TURN";
	private static final String messageTypeRewind = "REWIND";
	private static final String messageTypeShoot = "SHOOT";
	private static final String messageTypeSecondary = "SECONDARY";
	private static final String messageTypeFlash = "FLASH";
	private static final String messageTypeReset = "RESET";
	//private ArrayList<Player> otherPlayers;
	
	private Map map;
	private Hud hud;

	private long secondaryReadyTime;
	
	private float abilWidth, abilHeight;
	
	private boolean isHost;
	
	private int timer;
	
	
	
	public PlayScreen() {	
		assets = new ArrayList<PImage>();
		isHost = false;
		otherBullets = new ArrayList<Projectile>();
		bullets = new ArrayList<Projectile>();
		players = new ArrayList<Player>();
		hud = new Hud();
		prevClientLocs = new ArrayList<Point2D.Double>();
		prevClientMouseLocs = new ArrayList<Point2D.Double>();
		prevEnemyLocs = new ArrayList<Point2D.Double>();
		prevEnemyLocs = new ArrayList<Point2D.Double>();
		particles = new ArrayList<Particle>();
		abilWidth = 100;
		abilHeight = 100;
		hostSpawned = false;
		clientSpawned = false;
		timer = 0;
	}
	public void spawnNewHost() {
		System.out.println("Type:" + hostType);
		System.out.println("Type:" + clientType);
		
		if(hostPlayer == null && hostType != 0) {
			if(hostType == 1) {
				System.out.println("a");
				hostPlayer = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			}
			else if(hostType == 2) {
				System.out.println("b");
				hostPlayer = new Demolitions(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			}
			else {
				System.out.println("c");
				hostPlayer = new Technician(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			}
			hostSpawned = true;
			
			clientPlayer = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			
			//players.add(hostPlayer);
			//players.add(clientPlayer);
			
			Point2D.Double p = new Point2D.Double(hostPlayer.getX(), hostPlayer.getY());
			//Point2D.Double p2 = new Point2D.Double(hostPlayer.getX(), hostPlayer.getY());
			prevClientLocs.add(p);
			//prevEnemyLocs.add(p2);
			
			spawnNewGhost();
			}
	}
	
	public void spawnNewClient() {
		System.out.println("Type:" + clientType);
		if(clientPlayer == null && clientType != 0) {
			
			if(clientType == 1) {
				System.out.println("a");
				clientPlayer = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			}
			else if(clientType == 2) {
				System.out.println("b");
				clientPlayer = new Demolitions(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			}
			else {
				System.out.println("c");
				clientPlayer = new Technician(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			}
			
			clientSpawned = true;
			
			hostPlayer = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			//clientPlayer = new Demolitions(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
//			if(hostPlayer == null && hostType != 0) {
//				if(hostType == 1) {
//					System.out.println("a");
//					hostPlayer = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
//				}
//				else if(hostType == 2) {
//					System.out.println("b");
//					hostPlayer = new Demolitions(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
//				}
//				else {
//					System.out.println("c");
//					hostPlayer = new Technician(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
//				}
			
			//players.add(hostPlayer);
			//players.add(clientPlayer);
			
			Point2D.Double p = new Point2D.Double(hostPlayer.getX(), hostPlayer.getY());
			//Point2D.Double p2 = new Point2D.Double(hostPlayer.getX(), hostPlayer.getY());
			prevClientLocs.add(p);
			//prevEnemyLocs.add(p2);
			
			spawnNewGhost();
		}
	}
	
	public void spawnNewGhost() {
		p1Ghost = new Player(assets.get(1), (int)prevClientLocs.get(0).getX(), (int)prevClientLocs.get(0).getY(), 0);
	}
	
	public void setup(DrawingSurface drawer) {
		drawer.noStroke();
		assets.add(drawer.loadImage("assets/player.png"));			//0
		assets.add(drawer.loadImage("assets/ghost.png"));			//1
		assets.add(drawer.loadImage("assets/bullet.png"));			//2
		assets.add(drawer.loadImage("assets/star.png"));			//3
		assets.add(drawer.loadImage("assets/crosshair.png"));		//4
		assets.add(drawer.loadImage("assets/time.png"));			//5
		assets.add(drawer.loadImage("assets/starIcon.png"));		//6
		assets.add(drawer.loadImage("assets/flash.png"));			//7
		assets.add(drawer.loadImage("assets/wall.png"));			//8
		assets.add(drawer.loadImage("assets/wall2.png"));			//9
		assets.add(drawer.loadImage("assets/particle.png"));     	//10
		assets.add(drawer.loadImage("assets/bounceLogo.png"));   	//11
		assets.add(drawer.loadImage("assets/grenade.png"));      	//12
		
		
		//System.out.println(players);
		
		map = new Map(assets.get(8), assets.get(9));
		
	}
	
	public void reset(DrawingSurface drawer) {
		if(hostPlayer.getHealth() == 0) {
			hostPlayer.changeHealth(5);
			clientPlayer.win();
			hostPlayer.setCooldowns(0, 0);
			hostPlayer.setCooldowns(1, 0);
			hostPlayer.setCooldowns(2, 0);
			hostPlayer.setCooldowns(3, 0);
			hostPlayer.setCooldowns(4, 0);
			if(isHost) {
				hostPlayer.moveToLocation(DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
				clientPlayer.moveToLocation(DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
				
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeReset, true);
			}
			else {
				hostPlayer.moveToLocation(DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
				clientPlayer.moveToLocation(DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
				
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeReset, false);
			}

		}
	}
	
	public void draw(DrawingSurface drawer) {
		
		if(hostPlayer.getHealth() == 0 || clientPlayer.getHealth() == 0) {
			reset(drawer);
		}
		Point2D.Double p = new Point2D.Double(hostPlayer.getX(), hostPlayer.getY());
		prevClientLocs.add(p);
		if (prevClientLocs.size() > 120)
			prevClientLocs.remove(0);
		
		Point2D.Double p2 = new Point2D.Double(clientPlayer.getX(), clientPlayer.getY());
		prevEnemyLocs.add(p2);
		if (prevEnemyLocs.size() > 120)
			prevEnemyLocs.remove(0);
		

		Point2D.Double pMouse = new Point2D.Double(drawer.mouseX, drawer.mouseY);
		prevClientMouseLocs.add(pMouse);
		if (prevClientMouseLocs.size() > 120)
			prevClientMouseLocs.remove(0);
		
		p1Ghost.setX((int)prevClientLocs.get(0).getX());
		p1Ghost.setY((int)prevClientLocs.get(0).getY());
		
		drawer.background(128,128,128);

		float ratioX = (float)drawer.width/DRAWING_WIDTH;
		float ratioY = (float)drawer.height/DRAWING_HEIGHT;

		drawer.scale(ratioX, ratioY);
		
		hostPlayer.turnToward(drawer.mouseX / ratioX, drawer.mouseY / ratioY);

		p1Ghost.turnToward((float)prevClientMouseLocs.get(0).getX() / ratioX, (float)prevClientMouseLocs.get(0).getY() / ratioY);

		if(drawer.isPressed(KeyEvent.VK_A)) {
			hostPlayer.walk(-1, 0, map.getObstacles());
			drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, -1, 0);
		}
		if (drawer.isPressed(KeyEvent.VK_D)) {
			hostPlayer.walk(1, 0, map.getObstacles());
			drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, 1, 0);
		}
		if (drawer.isPressed(KeyEvent.VK_W)) {
			hostPlayer.walk(0, -1, map.getObstacles());
			drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, 0, -1);
		}
		if (drawer.isPressed(KeyEvent.VK_S)) {
			hostPlayer.walk(0, 1, map.getObstacles());
			drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, 0, 1);
		}
		if (drawer.isPressed(KeyEvent.VK_R)) {
			if(hostPlayer.getCooldowns()[2] - drawer.millis() <= 0) {
				hostPlayer.moveToLocation(prevClientLocs.get(0).getX(), prevClientLocs.get(0).getY());
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeRewind, prevEnemyLocs.get(0).getX(), prevEnemyLocs.get(0).getY());
				//set cooldowns
				hostPlayer.getCooldowns()[2] = drawer.millis() + 15000;
				hostPlayer.getCooldowns()[4] = drawer.millis() + 2000;
				
			}
		}
		if (drawer.isPressed(KeyEvent.VK_SHIFT)) {
			if(hostPlayer.getCooldowns()[3] - drawer.millis() <= 0 && hostPlayer.getType() == 1) {
				if(((Assault) hostPlayer).canShift(map.getObstacles())) {
					for (int i = 0; i < (int) (50 + Math.random() * 10); i++) {
						particles.add(new Particle(assets.get(10), hostPlayer.x + hostPlayer.getWidth() / 2, hostPlayer.y + hostPlayer.getHeight() / 2, 20, 20, 1));
					}
					((Assault) hostPlayer).shiftAbility(map.getObstacles());
					drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeFlash);
					
				}
			}
			else if(hostPlayer.getCooldowns()[3] - drawer.millis() <= 0 && hostPlayer.getType() == 2) {
				
				ArrayList<Projectile> fan = hostPlayer.secondary(assets.get(12));
				for(Projectile b : fan) {
					bullets.add(b);
				}
				hostPlayer.setCooldowns(3, drawer.millis() + 7000);
			}
			else if(hostPlayer.getCooldowns()[3] - drawer.millis() <= 0) {
				
				((Technician) hostPlayer).shiftAbility();
				
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeFlash);
				
				hostPlayer.setCooldowns(3, drawer.millis() + 7000);
			}
		}
		
		// Particles for when technician has shield up
		if (hostPlayer.getType() == 3 && ((Technician) hostPlayer).hasShield()) {
			Rectangle rect = ((Technician) hostPlayer).getShield();
			particles.add(new Particle(assets.get(10), hostPlayer.x + 10, hostPlayer.y, rect.getWidth() - 10, rect.getHeight() - 20, 3));
			
		}
		
		// Particles for when grenade active
		
		
		// Test this plz
		//
		//Testing
		//if(drawer.isPressed(KeyEvent.VK_SPACE)) {
		//	p1.changePlayerType(1);
		//}
		
		
		
		// Draw abilities
		if(drawer.mousePressed) {
			if(drawer.mouseButton == PConstants.LEFT) {
				if(hostPlayer.getCooldowns()[0] - drawer.millis() <= 0) {
					
					drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeShoot);
					
					bullets.add(hostPlayer.shoot(assets.get(2)));
					hostPlayer.setCooldowns(0,drawer.millis() + 1000);
				}
			}
			else if(drawer.mouseButton == PConstants.RIGHT) {
				if(secondaryReadyTime - drawer.millis() <= 0) {
					drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeSecondary);
					if(hostType == 1) {
						ArrayList<Projectile> fan = hostPlayer.secondary(assets.get(3));
							for(Projectile b : fan) {
								bullets.add(b);
							}
							secondaryReadyTime = drawer.millis() + 7000;
					}
					else if(hostType == 2) {
						// TODO add new ability
					}
					else {
						ArrayList<Projectile> fan = hostPlayer.secondary(assets.get(2));
						for(Projectile b : fan) {
							bullets.add(b);
						}
						secondaryReadyTime = drawer.millis() + 7000;
					}							
				}
				
				/*
				else {
					if(secondaryReadyTime - drawer.millis() <= 0) {
						bullets.add(((Demolitions)clientPlayer).secondary(assets.get(2)).get(0));
						secondaryReadyTime = drawer.millis() + 7000;
					}
				}
				*/
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
					else if (bullets.get(i).checkPlayer(clientPlayer)) {
						if(bullets.remove(i).getType() == 1)
							clientPlayer.changeHealth(-1);
						else
							clientPlayer.changeHealth(-2);
					}
			}
		}
		
		if (otherBullets.size() > 0) {
			for(int i = 0; i < otherBullets.size(); i++) {
					otherBullets.get(i).act();
					otherBullets.get(i).draw(drawer);
					if (otherBullets.get(i).checkObstacles(map.getObstacles())) {
						otherBullets.remove(i);
					}
					else if (otherBullets.get(i).checkPlayer(hostPlayer)) {
						otherBullets.remove(i);
						hostPlayer.changeHealth(-1);
					}
			}
		}
		// draw the players after the bullets so the bullets don't appear above the gun
		if(hostPlayer.getCooldowns()[4]- drawer.millis() < 0) {
			p1Ghost.draw(drawer);
		}
		
		hostPlayer.draw(drawer);
		clientPlayer.draw(drawer);
		
		if(drawer.getNetM() != null) {
			drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeTurn, drawer.mouseX, drawer.mouseY);
		}
		
		if (particles.size() > 0) {
			for(int i = 0; i < particles.size(); i++) {
					particles.get(i).draw(drawer);
					if (!particles.get(i).act()) {
						particles.remove(i);
					}
			}
		}
		//assets dont change, so dont take the in draw
		hud.draw(drawer, this, hostPlayer, assets.get(4), assets.get(5), assets.get(6),assets.get(7), assets.get(11), drawer.millis(), abilWidth, abilHeight);
		
		
		timer++;
		
	}
	
	public void setHostType(int hostType) {
		this.hostType = hostType;
		//System.out.println(this.hostType);
	}
	public void setClientType(int clientType) {
		this.clientType = clientType;
	}
	// GET DATA METHODS
	public Player getHostPlayer() {
		return hostPlayer;
	}
	public Player getClientPlayer() {
		return clientPlayer;
	}
	public boolean getIsHost() {
		return isHost;
	}
	public ArrayList<Projectile> getOtherBullets(){
		return otherBullets;
	}
	public ArrayList<Obstacle> getObstacles(){
		return map.getObstacles();
	}
	public ArrayList<Projectile> getBullets() {
		return bullets;
	}
	public ArrayList<PImage> getAssets(){
		return assets;
	}
	public ArrayList<Particle> getParticles(){
		return particles;
	}
}

