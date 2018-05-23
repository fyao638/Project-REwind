package gui;

import processing.core.PApplet;
import processing.core.PImage;

public class ScaleImage {

	private PImage original, scaled;
	
	private static float windowRatioX = 1, windowRatioY = 1;
	
	public ScaleImage(PImage img) {
		this.original = img;
	}
	
	public static void setWindowScaling(float windowRatioX, float windowRatioY) {
		ScaleImage.windowRatioX = windowRatioX;
		ScaleImage.windowRatioY = windowRatioY;
	}
	
	public void draw(PApplet surface, float x, float y, float w, float h) {
		float expectedWidth = w*windowRatioX;
		float expectedHeight = h*windowRatioY;
		
		if (scaled == null || !(Math.abs(scaled.width - expectedWidth) <= 1 && Math.abs(scaled.height - expectedHeight) <= 1)) {
			scaled = original.copy();
			scaled.resize((int)expectedWidth, (int)expectedHeight);
		}
		
		surface.image(scaled, x*windowRatioX, y*windowRatioY);
	}
	
	
}
