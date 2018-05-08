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

	public DrawingSurface() {
		super();
		assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();
		bullets = new ArrayList<Bullet>();
		screenRect = new Rectangle(0,0,DRAWING_WIDTH,DRAWING_HEIGHT);
		obstacles = new ArrayList<Shape>();
		obstacles.add(new Rectangle(375,100,50,400));
		obstacles.add(new Rectangle(200,250,400,50));
		prevLocs = new ArrayList<Point2D.Double>();
		prevMouseLocs = new ArrayList<Point2D.Double>();
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
		if (isPressed(KeyEvent.VK_R))
			p1.moveToLocation(prevLocs.get(0).getX(), prevLocs.get(0).getY());
		
		if(mousePressed) {
			bullets.add(new Bullet(assets.get(2), p1.getBulletPoint().getX(), p1.getBulletPoint().getY(), p1.getDirection(), 10));
		}
		
		for(Bullet b : bullets) {
			b.act();
			b.checkObstacles(obstacles);
			b.draw(this);
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

