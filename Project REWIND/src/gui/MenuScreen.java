package gui;
import clientside.DrawingSurface;
import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class represents the main menu. It is drawn by DrawingSurface.
 */
public class MenuScreen {
	
	int rectX, rectY;      // Position of square button
	int rectWidth = 400;
	int rectHeight = 100;
	boolean rectOver = false;
	PImage logo;
	
	public MenuScreen() {
		rectX = 200;
		rectY = 300;
	}

	public void setup(PApplet drawer) {
		logo = drawer.loadImage("assets/logo.png");
		
	}	
	public void draw(DrawingSurface drawer) {
	
		update(drawer.mouseX, drawer.mouseY, drawer);
		drawer.background(255);
	  
		if (rectOver) {
			drawer.fill(0,255,255);
		} 
		else {
			drawer.fill(0, 0, 255);
		}
		
		drawer.image(logo, 100 , 150, 600, 100);
		
		drawer.stroke(255);
		drawer.rect(rectX, rectY, rectWidth, rectHeight, 30);
		
		drawer.textSize(100);
		drawer.fill(0);
		drawer.text("PLAY", rectX + 75, rectY + rectHeight - 10);
	
	  	if(drawer.mousePressed && overRect(rectX, rectY, rectWidth, rectHeight, drawer)) {
	  		drawer.changeState(1);
	    }
	}
	
	void update(int x, int y, PApplet drawer) {
		if ( overRect(rectX, rectY, rectWidth, rectHeight, drawer) ) {
			rectOver = true;
		} 
		else {
			rectOver = false;
		}
	}
	boolean overRect(int x, int y, int width, int height, PApplet drawer)  {
		if (drawer.mouseX >= x && drawer.mouseX <= x+width && drawer.mouseY >= y && drawer.mouseY <= y+height) {
			return true;
	    } 
		else {
			return false;
		}
	}
}
