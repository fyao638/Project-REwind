package maps;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import sprites.obstacles.Obstacle;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class represents the game map and controls what obstacles are present.
 */
public class Map { //extend sprite? NO
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;
	
	private ArrayList<Obstacle> obstacles;
	public Map(PImage wallImage, PImage wallImage2) {
		obstacles = new ArrayList<Obstacle>();
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
		obstacles.add(new Obstacle(wallImage,0,0,DRAWING_WIDTH,1));
		obstacles.add(new Obstacle(wallImage,0,0,1,DRAWING_HEIGHT));
		obstacles.add(new Obstacle(wallImage,0,DRAWING_HEIGHT,DRAWING_WIDTH,1));
		obstacles.add(new Obstacle(wallImage,DRAWING_WIDTH,0,1,DRAWING_HEIGHT));
	}
	public void draw(PApplet d) {
		for(Obstacle s : obstacles) {
			s.draw(d);
		}
	}
	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}
	
}
