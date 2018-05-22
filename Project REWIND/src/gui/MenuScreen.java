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
	
	float rectX, rectY;   // Position of square button
	float rectWidth;
	float rectHeight;
	boolean rectOver1;
	PImage logo;
	
	private NetworkMessenger nm;
	
	public MenuScreen() {
		rectOver1 = false;
		rectX = 200;
		rectY = 280;
		rectWidth = 290;
		rectHeight = 100;
	}

	public void setup(PApplet drawer) {
		logo = drawer.loadImage("assets/logo.png");
		
		
	}	
	public void draw(DrawingSurface drawer) {
	
		update(drawer.mouseX, drawer.mouseY, drawer);
		drawer.background(127);
	  
		if (rectOver1) {
			drawer.fill(0,255,255);
		} 
		else {
			drawer.fill(0, 0, 255);
		}
		
		rectX = (float) (drawer.width / 2.0 - rectWidth / 2.0);
		
		
		drawer.stroke(0);
		drawer.strokeWeight(10);
		drawer.rect(rectX, rectY, rectWidth, rectHeight, 30);
		drawer.textSize(80);
		drawer.fill(0);
		drawer.text("PLAY", rectX + 45, rectY + rectHeight - 20);
		
		drawer.image(logo, (float) (drawer.width / 2.0 - 600 / 2.0), rectY - 130, 600, 100);
		
		
	  	if(drawer.mousePressed && overRect(rectX, rectY, rectWidth, rectHeight, drawer)) {
	  		drawer.changeState(1);
	    }
	}
	
	void update(int x, int y, PApplet drawer) {
		if ( overRect(rectX, rectY, rectWidth, rectHeight, drawer) ) {
			rectOver1 = true;
		} 
		else {
			rectOver1 = false;
		}
	}
	boolean overRect(float x, float y, float width, float height, PApplet drawer)  {
		if (drawer.mouseX >= x && drawer.mouseX <= x+width && drawer.mouseY >= y && drawer.mouseY <= y+height) {
			return true;
	    } 
		else {
			return false;
		}
	}
}
