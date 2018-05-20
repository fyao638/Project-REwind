package gui;
import clientside.DrawingSurface;
import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;
import network.frontend.NetworkDataObject;
import network.frontend.NetworkListener;
import network.frontend.NetworkMessenger;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class represents the main menu. It is drawn by DrawingSurface.
 */
public class MenuScreen{
	
	int rectX1, rectY1;   // Position of square button
	int rectWidth = 400;
	int rectHeight = 100;
	boolean rectOver1;
	PImage logo;
	
	private NetworkMessenger nm;
	
	public MenuScreen() {
		rectOver1 = false;
		rectX1 = 200;
		rectY1= 180;
	}

	public void setup(PApplet drawer) {
		logo = drawer.loadImage("assets/logo.png");
		
	}	
	public void draw(DrawingSurface drawer) {
	
		update(drawer.mouseX, drawer.mouseY, drawer);
		drawer.background(255);
	  
		if (rectOver1) {
			drawer.fill(0,255,255);
		} 
		else {
			drawer.fill(0, 0, 255);
		}
		
		drawer.stroke(0);
		drawer.rect(rectX1, rectY1, rectWidth, rectHeight, 30);
		drawer.textSize(80);
		drawer.fill(0);
		drawer.text("PLAY", rectX1 + 45, rectY1 + rectHeight - 20);
		
		drawer.image(logo, 100 , 50, 600, 100);
		
		
		
	
	  	if(drawer.mousePressed && overRect(rectX1, rectY1, rectWidth, rectHeight, drawer)) {
	  		drawer.changeState(1);
	    }
	}
	
	void update(int x, int y, PApplet drawer) {
		if ( overRect(rectX1, rectY1, rectWidth, rectHeight, drawer) ) {
			rectOver1 = true;
		} 
		else {
			rectOver1 = false;
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
