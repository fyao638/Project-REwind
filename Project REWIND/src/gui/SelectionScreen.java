package gui;

import clientside.DrawingSurface;
import network.frontend.NetworkDataObject;
import network.frontend.NetworkMessenger;
import processing.core.PApplet;
import processing.core.PImage;

public class SelectionScreen {
	
	int rectX1, rectY1;   // Position of square button
	int rectX2, rectY2;
	int rectX3, rectY3; 
	int rectWidth = 100;
	int rectHeight = 100;
	boolean rectOver1, rectOver2, rectOver3;
	private int type = 1;
	PImage logo;
	
	public SelectionScreen() {
		rectOver1 = false;
		rectOver2 = false;
		rectOver3 = false;
		rectX1 = 100;
		rectY1= 300;
		rectX2 = 300;
		rectY2= 300;
		rectX3 = 500;
		rectY3= 300;
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
		drawer.text("Assault", rectX1 + 45, rectY1 + rectHeight - 20);
		
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
		drawer.text("Demolitions", rectX2 + 35, rectY2 + rectHeight - 20);
		
		drawer.image(logo, 100 , 50, 600, 100);
		
		if (rectOver3) {
			drawer.fill(0,255,255);
		} 
		else {
			drawer.fill(0, 0, 255);
		}
		
		drawer.stroke(0);
		drawer.rect(rectX3, rectY3, rectWidth, rectHeight, 30);
		drawer.textSize(80);
		drawer.fill(0);
		drawer.text("Technician", rectX3 + 45, rectY3 + rectHeight - 20);
		
	
	  	if(drawer.mousePressed && overRect(rectX1, rectY1, rectWidth, rectHeight, drawer)) {
	  		type = 1;
	  		drawer.changeState(2);
	  		drawer.openNetworkingPanel();
	    }
	  	if(drawer.mousePressed && overRect(rectX2, rectY2, rectWidth, rectHeight, drawer)) {
	  		type = 2;
	  		drawer.changeState(2);
	  		drawer.openNetworkingPanel();
	  	}
	  	if(drawer.mousePressed && overRect(rectX3, rectY3, rectWidth, rectHeight, drawer)) {
	  		type = 3;
	  		drawer.changeState(2);
	  		drawer.openNetworkingPanel();
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
		else if(overRect(rectX3, rectY3, rectWidth, rectHeight, drawer)){
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
	
	public int getType() {
		return type;
	}
}
