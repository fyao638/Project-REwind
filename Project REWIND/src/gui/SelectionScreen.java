package gui;

import java.util.ArrayList;

import clientside.DrawingSurface;
import network.frontend.NetworkDataObject;
import network.frontend.NetworkMessenger;
import processing.core.PApplet;
/**
 * 
 * @author Aakarsh Anaad 
 * @version 1.0
 * This class displays the different classes and allows a user to pick one of them.
 *
 */
public class SelectionScreen {
	// Positions of square button\
	ArrayList<ScaleImage> assets;
	float rectX;
	float rectX1, rectY1;   // Assaults
	float rectX2, rectY2;	// Demolitions
	float rectX3, rectY3;	// Technician 
	float rectWidth;
	float rectHeight;
	boolean rectOver1, rectOver2, rectOver3;
	private int type = 1;
	ScaleImage logo;
	private int timer;
	private boolean netPanelOpened;
	
	public SelectionScreen() {
		timer = 0;
		rectOver1 = false;
		rectOver2 = false;
		rectOver3 = false;
		rectX1 = 100;
		rectY1= 150;
		rectX2 = 300;
		rectY2= 300;
		rectX3 = 500;
		rectY3= 450;
		rectWidth = 400;
		rectHeight = 100;
		rectX = 0;
		netPanelOpened = false;
	}

	public void setup(DrawingSurface drawer) {
		logo = new ScaleImage(drawer.loadImage("assets/logo.png"));
	}
	
	public void draw(DrawingSurface drawer) {
		timer++;
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
		drawer.rect(rectX, rectY1, rectWidth, rectHeight, 30);
		drawer.textSize(80);
		drawer.fill(0);
		drawer.text("Assault", rectX + 55, rectY1 + rectHeight - 20);
		
		if (rectOver2) {
			drawer.fill(0,255,255);
		} 
		else {
			drawer.fill(0, 0, 255);
		}
		drawer.stroke(0);
		drawer.rect(rectX, rectY2, rectWidth, rectHeight, 30);
		drawer.textSize(60);
		drawer.fill(0);
		drawer.text("Demolitions", rectX + 25, rectY2 + rectHeight - 25);
		
		logo.draw(drawer, (float) (drawer.width / 2.0 - 600 / 2.0), rectY1 - 130, 600, 100);
		
		if (rectOver3) {
			drawer.fill(0,255,255);
		} 
		else {
			drawer.fill(0, 0, 255);
		}
		
		drawer.stroke(0);
		drawer.rect(rectX, rectY3, rectWidth, rectHeight, 30);
		drawer.textSize(65);
		drawer.fill(0);
		drawer.text("Technician", rectX + 30, rectY3 + rectHeight - 25);
		
		if (timer > 10 && !netPanelOpened) {
		  	if(drawer.mousePressed && overRect(rectX, rectY1, rectWidth, rectHeight, drawer)) {
		  		type = 1;
		  		//drawer.changeState(2);
		  		drawer.openNetworkingPanel();
		  		netPanelOpened = true;
		    }
		  	if(drawer.mousePressed && overRect(rectX, rectY2, rectWidth, rectHeight, drawer)) {
		  		type = 2;
		  		//drawer.changeState(2);
		  		drawer.openNetworkingPanel();
		  		netPanelOpened = true;
		  	}
		  	if(drawer.mousePressed && overRect(rectX, rectY3, rectWidth, rectHeight, drawer)) {
		  		type = 3;
		  		//drawer.changeState(2);
		  		drawer.openNetworkingPanel();
		  		netPanelOpened = true;
		  		
		  	}
		}
	}
	
	void update(int x, int y, PApplet drawer) {
		if ( overRect(rectX, rectY1, rectWidth, rectHeight, drawer) ) {
			rectOver1 = true;
			rectOver2 = false;
			rectOver3 = false;
		} 
		else if(overRect(rectX, rectY2, rectWidth, rectHeight, drawer)){
			rectOver1 = false;
			rectOver2 = true;
			rectOver3 = false;
		}
		else if(overRect(rectX, rectY3, rectWidth, rectHeight, drawer)) {
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
	boolean overRect(float rectX12, float rectY12, float rectWidth2, float rectHeight2, PApplet drawer)  {
		if (drawer.mouseX >= rectX12 && drawer.mouseX <= rectX12+rectWidth2 && drawer.mouseY >= rectY12 && drawer.mouseY <= rectY12+rectHeight2) {
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
