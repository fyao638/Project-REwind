package testers;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import sprites.Bullet;
import sprites.Player;

public class DrawingSurface extends PApplet {

	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Rectangle screenRect;

	private Player p1, p1Ghost;
	private ArrayList<Shape> obstacles;

	private ArrayList<Integer> keys;
	
	private ArrayList<Bullet> bullets;
	
	private ArrayList<PImage> assets;
	
	private ArrayList<Point2D.Double> prevLocs;
	private ArrayList<Point2D.Double> prevMouseLocs;

	private long shotReadyTime, rewindReadyTime;
	
	private float abilWidth, abilHeight;
	
	public DrawingSurface() {
		super();
		assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();
		bullets = new ArrayList<Bullet>();
		screenRect = new Rectangle(0,0,DRAWING_WIDTH,DRAWING_HEIGHT);
		obstacles = new ArrayList<Shape>();
		obstacles.add(new Rectangle(375,100,50,400));
		obstacles.add(new Rectangle(200,250,400,50));
		obstacles.add(new Rectangle(0,0,DRAWING_WIDTH,1));
		obstacles.add(new Rectangle(0,0,1,DRAWING_HEIGHT));
		obstacles.add(new Rectangle(0,DRAWING_HEIGHT,DRAWING_WIDTH,1));
		obstacles.add(new Rectangle(DRAWING_WIDTH,0,1,DRAWING_HEIGHT));
		prevLocs = new ArrayList<Point2D.Double>();
		shotReadyTime = 0;
		rewindReadyTime = 0;
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
		//size(0,0,PApplet.P3D);
		assets.add(loadImage("player.png"));
		assets.add(loadImage("ghost.png"));
		assets.add(loadImage("bullet.png"));
		
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

		fill(100);
		for (Shape s : obstacles) {
			if (s instanceof Rectangle) {
				Rectangle r = (Rectangle)s;
				rect(r.x,r.y,r.width,r.height);
			}
		}
		
		p1.turnToward(mouseX / ratioX, mouseY / ratioY);
		p1Ghost.turnToward((float)prevMouseLocs.get(0).getX() / ratioX, (float)prevMouseLocs.get(0).getY() / ratioY);

		if (isPressed(KeyEvent.VK_A))
			p1.walk(-1, 0, obstacles);
		if (isPressed(KeyEvent.VK_D))
			p1.walk(1, 0, obstacles);
		if (isPressed(KeyEvent.VK_W))
			p1.walk(0, -1, obstacles);
		if (isPressed(KeyEvent.VK_S))
			p1.walk(0, 1, obstacles);
		if (isPressed(KeyEvent.VK_R)) {
			if(rewindReadyTime - millis() <= 0) {
				p1.moveToLocation(prevLocs.get(0).getX(), prevLocs.get(0).getY());
				
				rewindReadyTime = millis() + 15000;
			}
		}
		
		

		// Draw abilities
		
		noFill();
		
		stroke(0, 102, 153);
		strokeWeight(10); 
		
		
		rect(20, 480, abilWidth, abilHeight, 20);
		rect(140, 480, abilWidth, abilHeight, 20);
		
		fill(0, 102, 153, 128);
		
		
		if(shotReadyTime - millis() > 0) {
			rectMode(CORNERS);
			rect(20, 580, abilWidth + 20, 580 - 100 * (shotReadyTime - millis()) / 1000, 20);
			rectMode(CORNER);
		}
		if(rewindReadyTime - millis() > 0) {
			rectMode(CORNERS);
			rect(140, 580, 140 + abilWidth, 580 - 100 * (rewindReadyTime - millis()) / 15000, 20);
			rectMode(CORNER);
		}
		
		noStroke();
		strokeWeight(1);
		
		this.textSize(26); 
		fill(0, 102, 153);
		
		if(shotReadyTime - millis() <= 0) {
			this.text("SHOT", 37, 540);
		}
		else {
			fill(255, 255, 255);
			this.text("0." + Math.round((double)(shotReadyTime - millis()) * 10) / 1000 + "sec", 30, 540);
			fill(0, 102, 153);
		}
		
		if(rewindReadyTime - millis() <= 0) {
			textSize(20);
			this.text("REwind", 155, 539);
		}
		else {
			fill(255, 255, 255);
			this.text(Math.round((double)(rewindReadyTime - millis()) * 10) / 10000 + "sec", 150, 540);
			fill(0, 102, 153);
		}
		
		if(mousePressed) {
			if(mouseButton == LEFT) {
				if(shotReadyTime - millis() <= 0) {
					bullets.add(new Bullet(assets.get(2), p1.getBulletPoint().getX(), p1.getBulletPoint().getY(), p1.getDirection(), 10));
					
					shotReadyTime = millis() + 1000;
				}
			}
		}

		if (bullets.size() > 0) {
			for(int i = 0; i < bullets.size(); i++) {
					bullets.get(i).act();
					bullets.get(i).draw(this);
					if (bullets.get(i).checkObstacles(obstacles)) {
						bullets.remove(i);
					}
			}
		}

		// draw the players after the bullets so the bullets don't appear above the gun
		p1Ghost.draw(this);
		p1.draw(this);

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

