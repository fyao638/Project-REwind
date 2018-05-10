package testers;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Hud {
	
	public Hud() {
	}
	public void draw(PApplet drawer, int currentHealth, PImage icon1, PImage icon2, PImage icon3, long shotReadyTime, long rewindReadyTime, long secondaryReadyTime, long currentTime, float abilWidth, float abilHeight) {
		drawer.noFill();
		
		drawer.stroke(255);
		drawer.strokeWeight(2);
		drawer.rect( 10, 10, 200, 50);
		
		boolean isDead = false;
		
		if(currentHealth < 0) {
			isDead = true;
		}
		else if(currentHealth < 3 && currentHealth > -1) {
			drawer.fill(255, 0, 0);
		}
		else if(currentHealth < 4) {
			drawer.fill(255, 255, 0);
		}
		else if(currentHealth < 6){
			drawer.fill(0, 255, 0);
		}
		else {
			isDead = true;
			// or broken
		}
		if(!isDead) {
			drawer.rect( 10, 10, (float)(200 * (double)currentHealth / 5), 50);
		}
		
		drawer.noFill();
		drawer.stroke(0, 102, 153);
		drawer.strokeWeight(10); 
		
		drawer.rect(20, 480, abilWidth, abilHeight, 20);
		drawer.rect(140, 480, abilWidth, abilHeight, 20);
		drawer.rect(260, 480, abilWidth, abilHeight, 20);
		
		drawer.fill(0, 102, 153, 128);
		drawer.textSize(26); 
		
		
		if(shotReadyTime - currentTime > 0) {
			drawer.rectMode(PApplet.CORNERS);
			drawer.rect(20, 580, abilWidth + 20, 580 - 100 * (shotReadyTime - currentTime) / 1000, 20);
			drawer.rectMode(PApplet.CORNER);
		}
		if(rewindReadyTime - currentTime > 0) {
			drawer.rectMode(PApplet.CORNERS);
			drawer.rect(140, 580, 140 + abilWidth, 580 - 100 * (rewindReadyTime - currentTime) / 15000, 20);
			drawer.rectMode(PApplet.CORNER);
		}
		if(secondaryReadyTime - currentTime > 0) {
			drawer.rectMode(PApplet.CORNERS);
			drawer.rect(260, 580, 260 + abilWidth, 580 - 100 * (secondaryReadyTime - currentTime) / 7000, 20);
			drawer.rectMode(PApplet.CORNER);
		}
		
		drawer.noStroke();
		drawer.strokeWeight(1);
		
		drawer.textSize(26); 
		drawer.fill(0, 102, 153);
		
		if(shotReadyTime - currentTime <= 0) {
			drawer.textSize(26); 
			//drawer.text("SHOT", 37, 540);
			drawer.image(icon1, 30, 490, 80, 80);
			drawer.textSize(26); 
		}
		else {
			drawer.fill(255, 255, 255);
			drawer.text("0." + Math.round((double)(shotReadyTime - currentTime) * 10) / 1000 + "sec", 30, 540);
			drawer.fill(0, 102, 153);
		}
		if(rewindReadyTime - currentTime <= 0) {
			drawer.textSize(20);
			//drawer.text("REwind", 155, 539);
			drawer.image(icon2, 150, 490, 80, 80);
			drawer.textSize(26); 
		}
		else {
			drawer.fill(255, 255, 255);
			drawer.text(Math.round((double)(rewindReadyTime - currentTime) * 10) / 10000 + "sec", 150, 540);
			drawer.fill(0, 102, 153);
		}
		if(secondaryReadyTime - currentTime <= 0) {
			drawer.textSize(20);
			//drawer.text("spread", 280, 539);
			drawer.image(icon3, 270, 490, 80, 80);
			drawer.textSize(26); 
		}
		else {
			drawer.fill(255, 255, 255);
			drawer.text(Math.round((double)(secondaryReadyTime - currentTime) * 10) / 10000 + "sec", 280, 540);
			drawer.fill(0, 102, 153);
		}
		
	}
}

