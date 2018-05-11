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
	
	int rectX1, rectY1;   // Position of square button
	int rectX2, rectY2; 
	int rectX3, rectY3; 
	int rectWidth = 400;
	int rectHeight = 100;
	boolean rectOver1, rectOver2, rectOver3;
	PImage logo;
	
	public MenuScreen() {
		rectOver1 = false;
		rectOver2 = false;
		rectOver3 = false;
		rectX1 = 200;
		rectY1= 180;
		rectX2 = 200;
		rectY2= 300;
		rectX3 = 200;
		rectY3= 420;
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
		drawer.text("OFFLINE", rectX1 + 45, rectY1 + rectHeight - 20);
		
		if (rectOver2) {
			drawer.fill(0,255,255);
		} 
		else {
			drawer.fill(0, 0, 255);
		}
		
		drawer.stroke(0);
		drawer.rect(rectX2, rectY2, rectWidth, rectHeight, 30);
		drawer.textSize(70);
		drawer.fill(0);
		drawer.text("CONNECT", rectX2 + 35, rectY2 + rectHeight - 20);
		
		if (rectOver3) {
			drawer.fill(0,255,255);
		} 
		else {
			drawer.fill(0, 0, 255);
		}
		
		drawer.stroke(0);
		drawer.rect(rectX3, rectY3, rectWidth, rectHeight, 30);
		drawer.textSize(100);
		drawer.fill(0);
		drawer.text("HOST", rectX3 + 75, rectY3 + rectHeight - 10);
		
		drawer.image(logo, 100 , 50, 600, 100);
		
		
		
	
	  	if(drawer.mousePressed && overRect(rectX1, rectY1, rectWidth, rectHeight, drawer)) {
	  		drawer.changeState(1);
	    }
	  	if(drawer.mousePressed && overRect(rectX2, rectY2, rectWidth, rectHeight, drawer)) {
	  		drawer.startClient();
	  	}
	  	if(drawer.mousePressed && overRect(rectX3, rectY3, rectWidth, rectHeight, drawer)) {
	  		drawer.startServer();
	  	}
	}
	
	void update(int x, int y, PApplet drawer) {
		if ( overRect(rectX1, rectY1, rectWidth, rectHeight, drawer) ) {
			rectOver1 = true;
			rectOver2 = false;
			rectOver3 = false;
		} 
		else if(overRect(rectX2, rectY2, rectWidth, rectHeight, drawer)){
			rectOver1 = false;
			rectOver2 = true;
			rectOver3 = false;
		}
		else if(overRect(rectX3, rectY3, rectWidth, rectHeight, drawer)) {
			rectOver1 = false;
			rectOver2 = false;
			rectOver3 = true;
		}
		else {
			rectOver1 = false;
			rectOver2 = false;
			rectOver3 = false;
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
