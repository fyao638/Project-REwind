package sprites.obstacles;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import gui.ScaleImage;
import processing.core.PApplet;
import sprites.Sprite;

/**
 * 
 * @author  Michael Kim
 * @version 1.0
 * This class represents an obstacle on the map that tracks collisions with other players, bullets, and abilities.
 */
public class Obstacle extends Sprite {
	
	private ScaleImage img;
	private Line2D.Double[] lineComposition;
	private double[] lineCompositionNormals;
	private Line2D.Double l1, l2, l3, l4;

	public Obstacle(ScaleImage image, double x, double y, double w, double h) {
		super(image, x, y, w, h);
		l1 = new Line2D.Double(x, y, x + w, y);
		l2 = new Line2D.Double(x + w, y, x + w, y + h);
		l3 = new Line2D.Double(x + w, y + h, x, y + h);
		l4 = new Line2D.Double(x, y + h, x, y);
		
		lineComposition = new Line2D.Double[4];
		lineCompositionNormals = new double[4];
		
		lineComposition[0] = l1;
		lineComposition[1] = l2;
		lineComposition[2] = l3;
		lineComposition[3] = l4;
		
		lineCompositionNormals[0] = calcNormal(l1);
		lineCompositionNormals[1] = calcNormal(l2);
		lineCompositionNormals[2] = calcNormal(l3);
		lineCompositionNormals[3] = calcNormal(l4);
		
	}
	
	public void draw(PApplet drawer) {
		getImage().draw(drawer, (float)x,(float)y,(float)width,(float)height);
	}
	
	private double calcNormal(Line2D.Double l) {
		double slope = (l.y2 - l.y1)/(l.x2 - l.x1);
		if (slope == 0)
			return (3*Math.PI)/2;
		else if (slope == (3*Math.PI)/2)
			return 0;
		else
			return 0;
	}
	
	public Line2D.Double[] getLineComposition() {
		return lineComposition;
	}
	
	public double[] getLineCompositionNormals() {
		return lineCompositionNormals;
	}
}
