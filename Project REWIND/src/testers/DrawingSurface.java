package testers;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import sprites.Bullet;
import sprites.Player;

public class DrawingSurface extends PApplet {
/* Ghost class ?
 * Use the obstacle class (style)
 * move the code to playScreen
 * make a main menu
 * player should be the one shooting and rewinding (style)
 * 
 * 
 */
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Player p1, p1Ghost;

	private ArrayList<Integer> keys;
	
	private ArrayList<Bullet> bullets;
	
	private ArrayList<PImage> assets;
	
	private ArrayList<Point2D.Double> prevLocs;
	private ArrayList<Point2D.Double> prevMouseLocs;
	
	private Map map;
	private Hud hud;

	private long shotReadyTime, rewindReadyTime, secondaryReadyTime, shiftReadyTime, ghostReappearTime;
	
	private float abilWidth, abilHeight;
	
	public DrawingSurface() {
		super();
		assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();
		bullets = new ArrayList<Bullet>();
		map = new Map();
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
	
	public void runMe() {
		runSketch();
	}

	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		noStroke();
		assets.add(loadImage("player.png"));
		assets.add(loadImage("ghost.png"));
		assets.add(loadImage("bullet.png"));
		assets.add(loadImage("star.png"));
		assets.add(loadImage("crosshair.png"));
		assets.add(loadImage("time.png"));
		assets.add(loadImage("starIcon.png"));
		assets.add(loadImage("flash.png"));
		
		spawnNewPlayer();
		
		Point2D.Double p = new Point2D.Double(p1.getX(), p1.getY());
		prevLocs.add(p);
		
		spawnNewGhost();
	}

	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	int timer = 0;
	public void draw() {

		Point2D.Double p = new Point2D.Double(p1.getX(), p1.getY());
		prevLocs.add(p);
		if (prevLocs.size() > 120)
			prevLocs.remove(0);
		

		Point2D.Double pMouse = new Point2D.Double(mouseX, mouseY);
		prevMouseLocs.add(pMouse);
		if (prevMouseLocs.size() > 120)
			prevMouseLocs.remove(0);
		
		p1Ghost.setX((int)prevLocs.get(0).getX());
		p1Ghost.setY((int)prevLocs.get(0).getY());
		
		background(128,128,128);  

		float ratioX = (float)width/DRAWING_WIDTH;
		float ratioY = (float)height/DRAWING_HEIGHT;

		scale(ratioX, ratioY);
		
		p1.turnToward(mouseX / ratioX, mouseY / ratioY);
		p1Ghost.turnToward((float)prevMouseLocs.get(0).getX() / ratioX, (float)prevMouseLocs.get(0).getY() / ratioY);

		if (isPressed(KeyEvent.VK_A))
			p1.walk(-1, 0, map.getObstacles());
		if (isPressed(KeyEvent.VK_D))
			p1.walk(1, 0, map.getObstacles());
		if (isPressed(KeyEvent.VK_W))
			p1.walk(0, -1, map.getObstacles());
		if (isPressed(KeyEvent.VK_S))
			p1.walk(0, 1, map.getObstacles());
		if (isPressed(KeyEvent.VK_R)) {
			if(rewindReadyTime - millis() <= 0) {
				p1.moveToLocation(prevLocs.get(0).getX(), prevLocs.get(0).getY());
				
				rewindReadyTime = millis() + 15000;
				ghostReappearTime = millis() + 2000;
			}
		}
		if (isPressed(KeyEvent.VK_SHIFT)) {
			if(shiftReadyTime - millis() <= 0) {
				
				p1.shiftAbility(map.getObstacles());
				
				shiftReadyTime = millis() + 7000;
				
			}
		}
			
		//TESTING HEALTH
		if(isPressed(KeyEvent.VK_SPACE)) { 
			p1.changeHealth(-1);
			System.out.println(p1.getHealth());
		}

		// Draw abilities
		
		if(mousePressed) {
			if(mouseButton == LEFT) {
				if(shotReadyTime - millis() <= 0) {
					bullets.add(p1.shoot(assets.get(2)));
					shotReadyTime = millis() + 1000;
				}
			}
			else if(mouseButton == RIGHT) {
				if(secondaryReadyTime - millis() <= 0) {
					if(p1.getType() == 1) {
						ArrayList<Bullet> fan = p1.secondaryShoot(assets.get(3));
						for(Bullet b : fan) {
							bullets.add(b);
						}
						secondaryReadyTime = millis() + 7000;
					}
				}
			}
		}

		if (bullets.size() > 0) {
			for(int i = 0; i < bullets.size(); i++) {
					bullets.get(i).act();
					bullets.get(i).draw(this);
					if (bullets.get(i).checkObstacles(map.getObstacles())) {
						bullets.remove(i);
					}
			}
		}

		fill(100);
		map.draw(this);
		
		// draw the players after the bullets so the bullets don't appear above the gun
		if(ghostReappearTime - millis() < 0) {
			p1Ghost.draw(this);
		}
		p1.draw(this);

		hud.draw(this, p1.getHealth(), assets.get(4), assets.get(5), assets.get(6),assets.get(7), shotReadyTime, rewindReadyTime, secondaryReadyTime, shiftReadyTime, millis(), abilWidth, abilHeight);
		
		timer++;
	}

	public void keyPressed() {
		keys.add(keyCode);
	}

	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
}

