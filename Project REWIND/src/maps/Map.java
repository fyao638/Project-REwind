package maps;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import gui.ScaleImage;
import processing.core.PApplet;
import sprites.obstacles.Obstacle;

/**
 * 
 * @author Aakarsh Anand
 * This class represents the game map and controls what obstacles are present.
 */
public class Map { //extend sprite? NO
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;
	
	private ArrayList<Obstacle> obstacles, sides;
	public Map(ScaleImage wallImage, ScaleImage wallImage2) {
		obstacles = new ArrayList<Obstacle>();
		sides = new ArrayList<Obstacle>();
		obstacles.add(new Obstacle(wallImage2,60,60,50,200));
		obstacles.add(new Obstacle(wallImage,60,60,200,50));
		obstacles.add(new Obstacle(wallImage2,690,340,50,200));
		obstacles.add(new Obstacle(wallImage,540,490,200,50));
		obstacles.add(new Obstacle(wallImage2,690,60,50,200));
		obstacles.add(new Obstacle(wallImage,540,60,200,50));
		obstacles.add(new Obstacle(wallImage2,60,340,50,200));
		obstacles.add(new Obstacle(wallImage,60,490,200,50));
		obstacles.add(new Obstacle(wallImage2,250,200,30,200));
		obstacles.add(new Obstacle(wallImage2,520,200,30,200));
		obstacles.add(new Obstacle(wallImage,350,280,100,30));
		obstacles.add(new Obstacle(wallImage,0,-100,DRAWING_WIDTH,100));
		obstacles.add(new Obstacle(wallImage,-100,0,100,DRAWING_HEIGHT));
		obstacles.add(new Obstacle(wallImage,0,DRAWING_HEIGHT,DRAWING_WIDTH,100));
		obstacles.add(new Obstacle(wallImage,DRAWING_WIDTH,0,100,DRAWING_HEIGHT));
		
		sides.add(new Obstacle(wallImage,0,-100,DRAWING_WIDTH,100));
		sides.add(new Obstacle(wallImage,-100,0,100,DRAWING_HEIGHT));
		sides.add(new Obstacle(wallImage,0,DRAWING_HEIGHT,DRAWING_WIDTH,100));
		sides.add(new Obstacle(wallImage,DRAWING_WIDTH,0,100,DRAWING_HEIGHT));	
	}
	public void draw(PApplet d) {
		for(Obstacle s : obstacles) {
			s.draw(d);
		}
	}
	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}
	
	public ArrayList<Obstacle> getSides() {
		return sides;
	}
	
}
