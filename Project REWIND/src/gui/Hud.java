package gui;

import clientside.PlayScreen;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import sprites.player.Player;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class represents the all the HUD elements of the game, like ability icons with cooldowns and the life bar.
 */
public class Hud {
	
	public Hud() {
	}
	public void draw(PApplet drawer, PlayScreen play, Player p, PImage icon1, PImage icon2, PImage icon3, PImage icon4, PImage icon5, long currentTime, float abilWidth, float abilHeight) {
		drawer.noFill();
		// Draw the health bar
		drawer.fill(255, 100);
		drawer.stroke(255);
		drawer.strokeWeight(2);
		drawer.rect( 10, 10, 200, 50, 20);
		
		boolean isDead = false;
		
		if(p.getHealth() < 0) {
			isDead = true;
		}
		else if(p.getHealth() < 3 && p.getHealth() > -1) {
			drawer.fill(255, 0, 0, 100);
		}
		else if(p.getHealth() < 4) {
			drawer.fill(255, 255, 0, 100);
		}
		else if(p.getHealth()< 6){
			drawer.fill(0, 255, 0, 100);
		}
		else {
			isDead = true;
			// or broken
		}
		if(!isDead) {
			drawer.rect( 10, 10, (float)(200 * (double)p.getHealth() / 5), 50, 20);
		}
		
		drawer.noFill();
		drawer.stroke(0, 102, 153);
		drawer.strokeWeight(10); 
		
		drawer.rect(20, 480, abilWidth, abilHeight, 20);
		drawer.rect(140, 480, abilWidth, abilHeight, 20);
		drawer.rect(260, 480, abilWidth, abilHeight, 20);
		drawer.rect(380, 480, abilWidth, abilHeight, 20);
		
		drawer.textSize(26); 
		
		drawer.fill(0,255,0);
		drawer.text(play.getClientPlayer().getScore() + " vs " + play.getEnemyPlayer().getScore(), 720, 40);
		
		drawer.fill(0, 102, 153, 128);
		
		if(p.getCooldowns()[0] - currentTime > 0) {
			drawer.rectMode(PApplet.CORNERS);
			drawer.rect(20, 580, abilWidth + 20, 580 - 100 * (p.getCooldowns()[0] - currentTime) / 1000, 20);
			drawer.rectMode(PApplet.CORNER);
		}
		if(p.getCooldowns()[2] - currentTime > 0) {
			drawer.rectMode(PApplet.CORNERS);
			drawer.rect(140, 580, 140 + abilWidth, 580 - 100 * (p.getCooldowns()[2] - currentTime) / 15000, 20);
			drawer.rectMode(PApplet.CORNER);
		}
		if(p.getCooldowns()[1] - currentTime > 0) {
			drawer.rectMode(PApplet.CORNERS);
			drawer.rect(260, 580, 260 + abilWidth, 580 - 100 * (p.getCooldowns()[1] - currentTime) / 7000, 20);
			drawer.rectMode(PApplet.CORNER);
		}
		if(p.getCooldowns()[3] - currentTime > 0) {
			drawer.rectMode(PApplet.CORNERS);
			drawer.rect(380, 580, 380 + abilWidth, 580 - 100 * (p.getCooldowns()[3] - currentTime) / 7000, 20);
			drawer.rectMode(PApplet.CORNER);
		}
		
		drawer.noStroke();
		drawer.strokeWeight(1);
		
		drawer.textSize(26); 
		drawer.fill(0, 102, 153);
		
		if(p.getCooldowns()[0] - currentTime <= 0) {
			drawer.textSize(26); 
			//drawer.text("SHOT", 37, 540);
			drawer.image(icon1, 30, 490, 80, 80);
			drawer.textSize(26); 
		}
		else {
			drawer.fill(255, 255, 255);
			drawer.text("0." + Math.round((double)(p.getCooldowns()[0] - currentTime) * 10) / 1000 + "sec", 30, 540);
			drawer.fill(0, 102, 153);
		}
		if(p.getCooldowns()[2] - currentTime <= 0) {
			drawer.textSize(20);
			//drawer.text("REwind", 155, 539);
			drawer.image(icon2, 150, 490, 80, 80);
			drawer.textSize(26); 
		}
		else {
			drawer.fill(255, 255, 255);
			drawer.text(Math.round((double)(p.getCooldowns()[2] - currentTime) * 10) / 10000 + "sec", 150, 540);
			drawer.fill(0, 102, 153);
		}
		if(p.getCooldowns()[1] - currentTime <= 0) {
			drawer.textSize(20);
			//drawer.text("spread", 280, 539);
			if(p.getType() == 1) {
				drawer.image(icon3, 270, 490, 80, 80);
			}
			else {
				drawer.image(icon5, 270, 490, 80, 80);
			}
			drawer.textSize(26); 
		}
		else {
			drawer.fill(255, 255, 255);
			drawer.text(Math.round((double)(p.getCooldowns()[1] - currentTime) * 10) / 10000 + "sec", 280, 540);
			drawer.fill(0, 102, 153);
		}
		if(p.getCooldowns()[3] - currentTime <= 0) {
			drawer.textSize(20);
			//drawer.text("FLASH", 400, 539);
			drawer.image(icon4, 390, 490, 80, 80);
			drawer.textSize(26); 
		}
		else {
			drawer.fill(255, 255, 255);
			drawer.text(Math.round((double)(p.getCooldowns()[3] - currentTime) * 10) / 10000 + "sec", 400, 540);
			drawer.fill(0, 102, 153);
		}
		
	}
}

