package testers;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

public class Map { //extend sprite? NO
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;
	
	private ArrayList<Shape> obstacles;
	public Map() {
		obstacles = new ArrayList<Shape>();
		obstacles.add(new Rectangle(375,100,50,400));
		obstacles.add(new Rectangle(200,250,400,50));
		obstacles.add(new Rectangle(0,0,DRAWING_WIDTH,1));
		obstacles.add(new Rectangle(0,0,1,DRAWING_HEIGHT));
		obstacles.add(new Rectangle(0,DRAWING_HEIGHT,DRAWING_WIDTH,1));
		obstacles.add(new Rectangle(DRAWING_WIDTH,0,1,DRAWING_HEIGHT));
	}
	public void draw(DrawingSurface d) {
		for(Shape s : obstacles) {
			if (s instanceof Rectangle) {
				Rectangle r = (Rectangle)s;
				d.rect(r.x,r.y,r.width,r.height);
			}
		}
	}
	public ArrayList<Shape> getObstacles() {
		return obstacles;
	}
	
}
