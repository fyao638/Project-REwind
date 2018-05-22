package clientside;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
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
import sprites.projectile.Grenade;
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
	private Player you, enemy;
	private ArrayList<Particle> particles;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Projectile> otherProjectiles;
	private ArrayList<PImage> assets;
	
	private int youType, enemyType;
	
	private ArrayList<Point2D.Double> prevYouLocs;
	private ArrayList<Point2D.Double> prevYouMouseLocs;
	private ArrayList<Integer> prevYouHealth;
	
	private static final String messageTypeMove = "MOVE";
	private static final String messageTypeTurn = "TURN";
	private static final String messageTypeRewind = "REWIND";
	private static final String messageTypeShoot = "SHOOT";
	private static final String messageTypeSecondary = "SECONDARY";
	private static final String messageTypeShift = "SHIFT";
	private static final String messageTypeReset = "RESET";
	//private ArrayList<Player> otherPlayers;
	
	private Map map;
	private Hud hud;
	
	
	private float abilWidth, abilHeight;
	
	private boolean isHost;
	
	private int timer;
	
	
	
	public PlayScreen() {
		assets = new ArrayList<PImage>();
		isHost = false;
		otherProjectiles = new ArrayList<Projectile>();
		projectiles = new ArrayList<Projectile>();
		hud = new Hud();
		prevYouLocs = new ArrayList<Point2D.Double>();
		prevYouMouseLocs = new ArrayList<Point2D.Double>();
		prevYouHealth = new ArrayList<Integer>();
		particles = new ArrayList<Particle>();
		abilWidth = 100;
		abilHeight = 100;
		timer = 0;
	}
	public void spawnNewHost() {
		
		if(you == null && youType != 0) {
			if(youType == 1) {
				System.out.println("a");
				you = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			}
			else if(youType == 2) {
				System.out.println("b");
				you = new Demolitions(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			}
			else {
				System.out.println("c");
				you = new Technician(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			}
			isHost = true;
			
			enemy = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			
			//players.add(hostPlayer);
			//players.add(clientPlayer);
			
			Point2D.Double p = new Point2D.Double(you.getX(), you.getY());
			//Point2D.Double p2 = new Point2D.Double(hostPlayer.getX(), hostPlayer.getY());
			prevYouLocs.add(p);
			//prevEnemyLocs.add(p2);
			
			spawnNewGhost();
		}
	}
	
	public void spawnNewClient() {
		System.out.println("Type:" + enemyType);
		if(you == null && youType != 0) {
			
			if(youType == 1) {
				System.out.println("a");
				you = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			}
			else if(youType == 2) {
				System.out.println("b");
				you = new Demolitions(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			}
			else {
				System.out.println("c");
				you = new Technician(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			}
			
			enemy = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			
			
			Point2D.Double p = new Point2D.Double(you.getX(), you.getY());
			//Point2D.Double p2 = new Point2D.Double(hostPlayer.getX(), hostPlayer.getY());
			prevYouLocs.add(p);
			//prevEnemyLocs.add(p2);
			
			spawnNewGhost();
		}
	}
	public void updatePlayerType(DrawingSurface drawer) {
		
	}
	
	public void updateEnemy(DrawingSurface drawer, int enemyType) {
		if (isHost) {
			if (enemyType == 1) {
				enemy = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			} else if (enemyType == 2) {
				enemy = new Demolitions(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			} else {
				enemy = new Technician(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
			}
		}
		else {
			if (enemyType == 1) {
				enemy = new Assault(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			} else if (enemyType == 2) {
				enemy = new Demolitions(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			} else {
				enemy = new Technician(assets.get(0), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
			}
		}
	}
	
	public void spawnNewGhost() {
		p1Ghost = new Player(assets.get(1), (int)prevYouLocs.get(0).getX(), (int)prevYouLocs.get(0).getY(), 0);
	}
	
	public void setup(DrawingSurface drawer) {
		drawer.noStroke();
		assets.add(drawer.loadImage("assets/player.png"));		//0
		assets.add(drawer.loadImage("assets/ghost.png"));			//1
		assets.add(drawer.loadImage("assets/bullet.png"));		//2
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
		assets.add(drawer.loadImage("assets/molotov.png"));      	//13
		assets.add(drawer.loadImage("assets/grenadeIcon.png"));	//14
		assets.add(drawer.loadImage("assets/molotovIcon.png"));	//15
		assets.add(drawer.loadImage("assets/shieldIcon.png"));	//16
		
		//System.out.println(players);
		
		map = new Map(assets.get(8), assets.get(9));
		
	}
	
	public void reset(DrawingSurface drawer) {
		if(you.getHealth() <= 0) {
			you.setHealth(5);
			enemy.setHealth(5);
			enemy.win();
			you.setCooldowns(0, 0);
			you.setCooldowns(1, 0);
			you.setCooldowns(2, 0);
			you.setCooldowns(3, 0);
			you.setCooldowns(4, 0);
			otherProjectiles = new ArrayList<Projectile>();
			projectiles = new ArrayList<Projectile>();
			
			if(isHost) {
				you.moveToLocation(DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
				enemy.moveToLocation(DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
				
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeReset, true);
			}
			else {
				you.moveToLocation(DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,500);
				enemy.moveToLocation(DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
				
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeReset, false);
			}
			

		}
	}
	
	public void draw(DrawingSurface drawer) {
		
		if(you.getHealth() <= 0 || enemy.getHealth() <= 0) {
			reset(drawer);
		}
		Point2D.Double p = new Point2D.Double(you.getX(), you.getY());
		prevYouLocs.add(p);
		if (prevYouLocs.size() > 120)
			prevYouLocs.remove(0);

		Point2D.Double pMouse = new Point2D.Double(drawer.mouseX, drawer.mouseY);
		prevYouMouseLocs.add(pMouse);
		if (prevYouMouseLocs.size() > 120)
			prevYouMouseLocs.remove(0);
		
		Integer pHealth = you.getHealth();
		prevYouHealth.add(pHealth);
		if (prevYouHealth.size() > 120)
			prevYouHealth.remove(0);
		
		p1Ghost.setX((int)prevYouLocs.get(0).getX());
		p1Ghost.setY((int)prevYouLocs.get(0).getY());
		
		drawer.background(128,128,128);

		float ratioX = (float)drawer.width/DRAWING_WIDTH;
		float ratioY = (float)drawer.height/DRAWING_HEIGHT;

		drawer.scale(ratioX, ratioY);
		
		you.turnToward(drawer.mouseX / ratioX, drawer.mouseY / ratioY);

		p1Ghost.turnToward((float)prevYouMouseLocs.get(0).getX() / ratioX, (float)prevYouMouseLocs.get(0).getY() / ratioY);

		if(drawer.getClientCount() == 2) {
			if(drawer.isPressed(KeyEvent.VK_A)) {
				you.walk(-1, 0, map.getObstacles());
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, -1, 0);
			}
			if (drawer.isPressed(KeyEvent.VK_D)) {
				you.walk(1, 0, map.getObstacles());
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, 1, 0);
			}
			if (drawer.isPressed(KeyEvent.VK_W)) {
				you.walk(0, -1, map.getObstacles());
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, 0, -1);
			}
			if (drawer.isPressed(KeyEvent.VK_S)) {
				you.walk(0, 1, map.getObstacles());
				drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, 0, 1);
			}
			if (drawer.isPressed(KeyEvent.VK_R)) {
				if(you.getCooldowns()[2] - drawer.millis() <= 0) {
					you.setHealth(prevYouHealth.get(0));
					you.moveToLocation(prevYouLocs.get(0).getX(), prevYouLocs.get(0).getY());
					drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeRewind, prevYouLocs.get(0).getX(), prevYouLocs.get(0).getY());
					//set cooldowns
					you.getCooldowns()[2] = drawer.millis() + 15000;
					you.getCooldowns()[4] = drawer.millis() + 2000;
					
				}
			}
			if (drawer.isPressed(KeyEvent.VK_SHIFT)) {
				if(you.getCooldowns()[3] - drawer.millis() <= 0 && you.getType() == 1) {
					if(((Assault) you).canShift(map.getObstacles())) {
						for (int i = 0; i < (int) (50 + Math.random() * 10); i++) {
							particles.add(new Particle(assets.get(10), you.x + you.getWidth() / 2, you.y + you.getHeight() / 2, 20, 20, 1));
						}
						((Assault) you).shiftAbility(map.getObstacles());
						drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeShift, 1);
	
						you.setCooldowns(3, drawer.millis() + 7000);
					}
				}
				else if(you.getCooldowns()[3] - drawer.millis() <= 0 && you.getType() == 2) {
					
					ArrayList<Projectile> fan = ((Demolitions) you).shiftAbility(assets.get(12));
					for(Projectile b : fan) {
						projectiles.add(b);
					}
					
					drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeShift, 2);
					you.setCooldowns(3, drawer.millis() + 7000);
				}
				else if(you.getCooldowns()[3] - drawer.millis() <= 0) {
					
					((Technician) you).shiftAbility();
					
					drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeShift, 3);
					
					you.setCooldowns(3, drawer.millis() + 7000);
				}
			}
			
			// Particles for when technician has shield up
			if (you.getType() == 3 && ((Technician) you).hasShield()) {
				Rectangle rect = ((Technician) you).getShield();
				particles.add(new Particle(assets.get(10), you.x + 10, you.y, rect.getWidth() - 10, rect.getHeight() - 20, 3));
				
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
					if(you.getCooldowns()[0] - drawer.millis() <= 0) {
						
						drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeShoot);
						
						projectiles.add(you.shoot(assets.get(2)));
						you.setCooldowns(0,drawer.millis() + 1000);
					}
				}
				else if(drawer.mouseButton == PConstants.RIGHT) {
					if(you.getCooldowns()[1] - drawer.millis() <= 0) {
						if(youType == 1) {
							drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeSecondary, 1);
							ArrayList<Projectile> fan = you.secondary(assets.get(3));
								for(Projectile b : fan) {
									projectiles.add(b);
								}
								you.setCooldowns(1,drawer.millis() + 5000);
						}
						else if(youType == 2) {
							drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeSecondary, 2);
							ArrayList<Projectile> fan = you.secondary(assets.get(13));
							for(Projectile b : fan) {
								projectiles.add(b);
							}
							you.setCooldowns(1,drawer.millis() + 5000);
						}
						else {
							drawer.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypeSecondary, 3);
							ArrayList<Projectile> fan = you.secondary(assets.get(2));
							for(Projectile b : fan) {
								projectiles.add(b);
							}
							you.setCooldowns(1,drawer.millis() + 5000);
						}							
					}
					
					/*
=======
					}
					else if(youType == 2) {
						ArrayList<Projectile> molotov = ((Demolitions) you).secondary(assets.get(13));
						for(Projectile b : molotov) {
							bullets.add(b);
						}
						you.setCooldowns(1,drawer.millis() + 5000);

					}
>>>>>>> branch 'master' of https://github.com/fyao638/Project-REwind.git
					else {
						if(secondaryReadyTime - drawer.millis() <= 0) {
							bullets.add(((Demolitions)clientPlayer).secondary(assets.get(2)).get(0));
							secondaryReadyTime = drawer.millis() + 7000;
						}
					}
					*/
				}
			}
		}

		
		drawer.fill(100);
		map.draw(drawer);
		
		if (projectiles.size() > 0) {
			for(int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).act();
				projectiles.get(i).draw(drawer);
				
				int damage = projectiles.get(i).checkPlayer(enemy);
				if (projectiles.get(i).checkObstacles(map.getObstacles())) {
					projectiles.remove(i);
				}
				else if (damage > 0) {
					if(projectiles.get(i).getType() == 1) {
						projectiles.remove(i);
						if(enemy.getType() == 3) {
							if(!((Technician) enemy).hasShield()) {
								enemy.changeHealth(-damage);
							}
						}
						else {
							enemy.changeHealth(-damage);
						}
					}
					else if (projectiles.get(i).getType() == 2) {
						if(enemy.getType() == 3) {
							if(!((Technician) enemy).hasShield()) {
								enemy.changeHealth(-2);
								projectiles.remove(i);
							}
						}
							else {
								enemy.changeHealth(-2);
								projectiles.remove(i);
							}
					}
					else if (projectiles.get(i).getType() == 3) {
						if(enemy.getType() == 3) {
							if(!((Technician) enemy).hasShield()) {
								if(timer % 50 == 0)
									enemy.changeHealth(-1);
								if(!projectiles.get(i).isActive())
									projectiles.remove(i);
							}
						}
							else {
								if(timer % 50 == 0)
									enemy.changeHealth(-1);
								if(!projectiles.get(i).isActive())
									projectiles.remove(i);
							}
					}
					else {
						if(enemy.getType() == 3) {
							if(!((Technician) enemy).hasShield()) {
								if(!((Grenade) projectiles.get(i)).hasAffected()) {
									((Grenade) projectiles.get(i)).changeAffected();
									enemy.changeHealth(-damage);
								}
								else {
									if (!projectiles.get(i).isActive())
										projectiles.remove(i);
								}
							}
						}
						else {
							if(projectiles.get(i).isActive()) {
								((Grenade) projectiles.get(i)).changeAffected();
								enemy.changeHealth(-damage);
							}
							else {
								projectiles.remove(i);
							}
						}
					}
				}
			}
		}
		
		if (otherProjectiles.size() > 0) {
			for(int i = 0; i < otherProjectiles.size(); i++) {
				otherProjectiles.get(i).act();
				otherProjectiles.get(i).draw(drawer);
				
				int damage = otherProjectiles.get(i).checkPlayer(you);
				if (otherProjectiles.get(i).checkObstacles(map.getObstacles())) {
					otherProjectiles.remove(i);
				}
				else if (damage > 0) {
					if(otherProjectiles.get(i).getType() == 1) {
						otherProjectiles.remove(i);
						if(you.getType() == 3) {
							if(!((Technician) you).hasShield()) {
								you.changeHealth(-damage);
							}
						}
							else {
								you.changeHealth(-damage);
							}
					}
					else if (otherProjectiles.get(i).getType() == 2) {
						if(you.getType() == 3) {
							if(!((Technician) you).hasShield()) {
								you.changeHealth(-2);
								otherProjectiles.remove(i);
//								if(projectiles.get(i).isDone()) {
//									
//								}
							}
						}
							else {
								you.changeHealth(-2);

							}
					}
					else if (otherProjectiles.get(i).getType() == 3) {
						if(you.getType() == 3) {
							if(!((Technician) you).hasShield()) {
								if(timer % 50 == 0)
									you.changeHealth(-1);
								if(!otherProjectiles.get(i).isActive())
									otherProjectiles.remove(i);
							}
						}
						else {
							if(timer % 50 == 0)
								you.changeHealth(-1);
							if(!otherProjectiles.get(i).isActive())
								otherProjectiles.remove(i);
						}
					}
					else {
						if(you.getType() == 3) {
							if(!((Technician) you).hasShield()) {
								if(!((Grenade) otherProjectiles.get(i)).hasAffected()) {
									((Grenade) otherProjectiles.get(i)).changeAffected();
									you.changeHealth(-damage);
								}
								else {
									if (!otherProjectiles.get(i).isActive())
										otherProjectiles.remove(i);
								}
							}
						}
						else {
							if(!((Grenade) otherProjectiles.get(i)).hasAffected()) {
								((Grenade) otherProjectiles.get(i)).changeAffected();
								you.changeHealth(-damage);
							}
							else {
								if (!otherProjectiles.get(i).isActive())
									otherProjectiles.remove(i);
							}
						}
					}
				}
			}
		}
		// draw the players after the bullets so the bullets don't appear above the gun
		if(you.getCooldowns()[4]- drawer.millis() < 0) {
			p1Ghost.draw(drawer);
		}
		
		you.draw(drawer);
		enemy.draw(drawer);
		
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
		hud.draw(drawer, this, you, drawer.millis(), abilWidth, abilHeight);
		
		
		timer++;
		
	}
	
	public int getTimer() {
		return timer;
	}
	

	public void setYouType(int youType) {
		this.youType = youType;
	}
	public void setEnemyType(int enemyType) {
		this.enemyType = enemyType;
	}
	// GET DATA METHODS
	public Player getYouPlayer() {
		return you;
	}
	public Player getEnemyPlayer() {
		return enemy;
	}
	public boolean getIsHost() {
		return isHost;
	}
	public ArrayList<Projectile> getOtherBullets(){
		return otherProjectiles;
	}
	public ArrayList<Obstacle> getObstacles(){
		return map.getObstacles();
	}
	public ArrayList<Projectile> getBullets() {
		return projectiles;
	}
	public ArrayList<PImage> getAssets(){
		return assets;
	}
	public ArrayList<Particle> getParticles(){
		return particles;
	}
}

