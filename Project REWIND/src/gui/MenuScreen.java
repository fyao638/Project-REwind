package gui;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import clientside.DrawingSurface;
import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;
import network.frontend.NetworkDataObject;
import network.frontend.NetworkListener;
import network.frontend.NetworkMessenger;
import processing.core.PApplet;

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
	ScaleImage logo;
	int frameNum;
	
	ArrayList<ScaleImage> mainMenuFrames;
	
	public MenuScreen() {
		mainMenuFrames = new ArrayList<ScaleImage>();
		frameNum = 0;
		rectOver1 = false;
		rectWidth = 300;
		rectHeight = 100;
	}

	public void setup(PApplet drawer) {
		logo = new ScaleImage(drawer.loadImage("assets/logo.png"));
		
		for(int i = 0; i < 10; i++) {
			mainMenuFrames.add(new ScaleImage(drawer.loadImage("mainMenuGif/frame_0" + i + "_delay-0.03s.gif" )));
		}
		for(int i = 10; i < 74; i++) {
			mainMenuFrames.add(new ScaleImage(drawer.loadImage("mainMenuGif/frame_" + i + "_delay-0.03s.gif")));
		}
		
		rectX = (float) (drawer.width / 2.0 - rectWidth / 2.0);
		rectY = (float) (drawer.height / 2.0 - rectHeight / 2.0);
		
	}	
	public void draw(DrawingSurface drawer) {
	
		update(drawer.mouseX, drawer.mouseY, drawer);
		mainMenuFrames.get(frameNum).draw(drawer, 0, 0, drawer.width, drawer.height);
	  
		if (rectOver1) {
			drawer.fill(0,255,255);
		} 
		else {
			drawer.noFill();;
		}
		
		drawer.stroke(255);
		drawer.strokeWeight(10);
		drawer.rect(rectX, rectY + 100, rectWidth , rectHeight, 30);
		drawer.textSize(80);
		drawer.fill(255);
		drawer.text("PLAY", rectX + 50, rectY + rectHeight + 80);
		
		logo.draw(drawer, (float) (drawer.width / 2.0 - 600 / 2.0), rectY - 130, 600, 100);
		
		
	  	if(drawer.mousePressed && overRect(rectX, rectY + 100, rectWidth, rectHeight, drawer)) {
	  		drawer.changeState(1);
	    }
	  	
	  	frameNum++;
	  	
	  	if(frameNum == 74) {
	  		frameNum = 0;
	  	}
	  	
	  	if(drawer.isPressed(KeyEvent.VK_R)) {
	  		if(drawer.millis() <= 2000) {
	  			System.exit(0);
	  		}
	  	}
	}
	
	
	void update(int x, int y, PApplet drawer) {
		if ( overRect(rectX, rectY + 100, rectWidth, rectHeight, drawer) ) {
			rectOver1 = true;
		} 
		else {
			rectOver1 = false;
		}
	}
	boolean overRect(float x, float y, float width, float height, PApplet drawer)  {
		if (drawer.mouseX >= x && drawer.mouseX <= x+width && drawer.mouseY >= y  && drawer.mouseY <= y+height ) {
			return true;
	    } 
		else {
			return false;
		}
	}
}
