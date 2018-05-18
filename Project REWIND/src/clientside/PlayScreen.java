package clientside;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
	private Player clientPlayer, enemyPlayer;
	private ArrayList<Particle> particles;

	private ArrayList<Projectile> bullets;
	private ArrayList<Projectile> otherBullets;
	private ArrayList<Player> players;
	private ArrayList<PImage> assets;
	
	private ArrayList<Point2D.Double> prevClientLocs;
	private ArrayList<Point2D.Double> prevClientMouseLocs;
	private ArrayList<Point2D.Double> prevEnemyLocs;
	private ArrayList<Point2D.Double> prevEnemyMouseLocs;
	
	private static final String messageTypeMove = "MOVE";
	private static final String messageTypeTurn = "TURN";
	private static final String messageTypeRewind = "REWIND";
	private static final String messageTypeShoot = "SHOOT";
	private static final String messageTypeSecondary = "SECONDARY";
	private static final String messageTypeFlash = "FLASH";
	//private ArrayList<Player> otherPlayers;
	
	//PACKETS
	
	private Map map;
	private Hud hud;

	private long shotReadyTime, rewindReadyTime, secondaryReadyTime, shiftReadyTime, ghostReappearTime;
	
	private float abilWidth, abilHeight;
	
	public PlayScreen() {
		assets = new ArrayList<PImage>();
		otherBullets = new ArrayList<Projectile>();
		bullets = new ArrayList<Projectile>();
		players = new ArrayList<Player>();
		hud = new Hud();
		shotReadyTime = 0;
		rewindReadyTime = 0;
		secondaryReadyTime = 0;
		shiftReadyTime = 0;
		ghostReappearTime = 0;
		prevClientLocs = new ArrayList<Point2D.Double>();
		prevClientMouseLocs = new ArrayList<Point2D.Double>();
		prevEnemyLocs = new ArrayList<Point2D.Double>();
		prevEnemyLocs = new ArrayList<Point2D.Double>();
		particles = new ArrayList<Particle>();
		abilWidth = 100;
		abilHeight = 100;
	}
	public void spawnNewHost() {
		clientPlayer = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
		enemyPlayer = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
	}
	
	public void spawnNewClient() {
		clientPlayer = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
		enemyPlayer = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
	}
	
	public void spawnNewGhost() {
		p1Ghost = new Player(assets.get(1), (int)prevClientLocs.get(0).getX(), (int)prevClientLocs.get(0).getY());
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
		if(drawer.getClientCount() == 0) {
			spawnNewHost();
		}
		else {
			spawnNewClient();
		}
		players.add(clientPlayer);
		players.add(enemyPlayer);
		
		Point2D.Double p = new Point2D.Double(clientPlayer.getX(), clientPlayer.getY());
		Point2D.Double p2 = new Point2D.Double(clientPlayer.getX(), clientPlayer.getY());
		prevClientLocs.add(p);
		prevEnemyLocs.add(p2);
		
		spawnNewGhost();
	}
	int timer = 0;
	public void draw(DrawingSurface drawer) {
		
		
		Point2D.Double p = new Point2D.Double(clientPlayer.getX(), clientPlayer.getY());
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
		
		clientPlayer.turnToward(drawer.mouseX / ratioX, drawer.mouseY / ratioY);

		p1Ghost.turnToward((float)prevClientMouseLocs.get(0).getX() / ratioX, (float)prevClientMouseLocs.get(0).getY() / ratioY);

		if(drawer.isPressed(KeyEvent.VK_A)) {
			clientPlayer.walk(-1, 0, map.getObstacles());
			drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, -1, 0);
		}
		if (drawer.isPressed(KeyEvent.VK_D)) {
			clientPlayer.walk(1, 0, map.getObstacles());
			drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, 1, 0);
		}
		if (drawer.isPressed(KeyEvent.VK_W)) {
			clientPlayer.walk(0, -1, map.getObstacles());
			drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, 0, -1);
		}
		if (drawer.isPressed(KeyEvent.VK_S)) {
			clientPlayer.walk(0, 1, map.getObstacles());
			drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, 0, 1);
		}
		if (drawer.isPressed(KeyEvent.VK_R)) {
			if(rewindReadyTime - drawer.millis() <= 0) {
				clientPlayer.moveToLocation(prevClientLocs.get(0).getX(), prevClientLocs.get(0).getY());
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeRewind, prevEnemyLocs.get(0).getX(), prevEnemyLocs.get(0).getY());
				//set cooldowns
				rewindReadyTime = drawer.millis() + 15000;
				ghostReappearTime = drawer.millis() + 2000;
				
			}
		}
		if (drawer.isPressed(KeyEvent.VK_SHIFT)) {
			if(shiftReadyTime - drawer.millis() <= 0) {
				for (int i = 0; i < (int) (10 + Math.random() * 10); i++) {
					particles.add(new Particle(assets.get(10), clientPlayer.x + clientPlayer.getWidth() / 2, clientPlayer.y + clientPlayer.getHeight() / 2, 20, 20));
				}
				if(((Assault) clientPlayer).shiftAbility(map.getObstacles())) {
					drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeFlash);
					
					shiftReadyTime = drawer.millis() + 7000;
				}
			}
		}
		if(drawer.isPressed(KeyEvent.VK_F)) {
			drawer.getSoundM().laugh();
		}
		// Test this plz
		//
		//Testing
		//if(drawer.isPressed(KeyEvent.VK_SPACE)) {
		//	p1.changePlayerType(1);
		//}
		
		
		
		// Draw abilities
		if(drawer.mousePressed) {
			if(drawer.mouseButton == PConstants.LEFT) {
				if(shotReadyTime - drawer.millis() <= 0) {
					
					drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeShoot);
					
					bullets.add(clientPlayer.shoot(assets.get(2)));
					shotReadyTime = drawer.millis() + 1000;
				}
			}
			else if(drawer.mouseButton == PConstants.RIGHT) {
				if(clientPlayer.getType() == 1) {
					if(secondaryReadyTime - drawer.millis() <= 0) {
						if(clientPlayer.getType() == 1) {
							
							drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeSecondary);
							
							// casting this for now... But I need a better fix
							ArrayList<Projectile> fan = (clientPlayer).secondary(assets.get(3));

							for(Projectile b : fan) {
								bullets.add(b);
							}
							secondaryReadyTime = drawer.millis() + 7000;
						}
//						else if(clientPlayer.getType() == 2) {
//							// casting this for now... But I need a better fix
//							System.out.println("help");
//							ArrayList<Projectile> fan = (clientPlayer).secondary(assets.get(12));
//
//							for(Projectile b : fan) {
//								bullets.add(b);
//							}
//							secondaryReadyTime = drawer.millis() + 7000;
//							
//						}
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
					else if (bullets.get(i).checkPlayer(enemyPlayer)) {
						bullets.remove(i);
						enemyPlayer.changeHealth(-1);
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
					else if (otherBullets.get(i).checkPlayer(clientPlayer)) {
						otherBullets.remove(i);
						clientPlayer.changeHealth(-1);
					}
			}
		}
		// draw the players after the bullets so the bullets don't appear above the gun
		if(ghostReappearTime - drawer.millis() < 0) {
			p1Ghost.draw(drawer);
		}
		
		clientPlayer.draw(drawer);
		enemyPlayer.draw(drawer);
		
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
		hud.draw(drawer, clientPlayer, assets.get(4), assets.get(5), assets.get(6),assets.get(7), assets.get(11), shotReadyTime, rewindReadyTime, secondaryReadyTime, shiftReadyTime, drawer.millis(), abilWidth, abilHeight);
		
		
		timer++;
		
	}
	
	// GET DATA METHODS
	public Player getClientPlayer() {
		return (Assault)clientPlayer;
	}
	public Player getEnemyPlayer() {
		return (Assault)enemyPlayer;
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

