package gui;

import clientside.DrawingSurface;
import clientside.PlayScreen;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import sprites.player.Player;

/**
 * 
 * @author Frank Yao
 * @version 1.0
 * This class represents the all the HUD elements of the game including: 
 * - Cooldowns
 * - Health
 * - Score
 * - Character marker
 * - Ability Icons
 */
public class Hud {
	
	public Hud() {
	}
	public void draw(DrawingSurface drawer, PlayScreen play, Player p, long currentTime, float abilWidth, float abilHeight) {
		
		double x = p.getX();
		double y = p.getY();
		double width = p.width;
		double height = p.height;
		
		
		drawer.stroke(255,140,0);
		drawer.fill(255,140,0);
		drawer.triangle((float)(x + width/ 4), (float)(y - height / 3), (float)(x + width / 2), (float)y, (float)(x + 3 * width/ 4), (float)(y - height / 3));
		
		
		
		PImage icon1 = play.getAssets().get(4);
		PImage icon2 = play.getAssets().get(5);
		PImage icon3 = null;
		PImage icon4 = null;
		
		if(p.getType() == 1) {
			icon3 = play.getAssets().get(6);
		}
		else if(p.getType() == 2) {
			icon3 = play.getAssets().get(15);
		}
		else {
			icon3 = play.getAssets().get(11);
		}
		
		if(p.getType() == 1) {
			icon4 = play.getAssets().get(7);
		}
		else if(p.getType() == 2) {
			icon4 = play.getAssets().get(14);
		}
		else {
			icon4 = play.getAssets().get(16);
		}
		
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
		
		drawer.textSize(40); 
		
		drawer.fill(255,140,0);
		drawer.text(play.getYouPlayer().getScore() + " vs " + play.getEnemyPlayer().getScore(),  670 , 50);
		
		drawer.fill(0, 102, 153, 100);
		
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
			drawer.rect(260, 580, 260 + abilWidth, 580 - 100 * (p.getCooldowns()[1] - currentTime) / 5000, 20);
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
		drawer.fill(0, 102, 153, 100);
		
		if(p.getCooldowns()[0] - currentTime <= 0) {
			drawer.textSize(26); 
			//drawer.text("SHOT", 37, 540);
			drawer.fill(255, 100);
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
			drawer.fill(255, 100);
			drawer.image(icon2, 150, 490, 80, 80);
			drawer.textSize(26); 
		}
		else {
			drawer.fill(255, 255, 255);
			drawer.text(Math.round((double)(p.getCooldowns()[2] - currentTime) * 10) / 10000 + "sec", 150, 540);
			drawer.fill(0, 102, 153,100);
		}
		if(p.getCooldowns()[1] - currentTime <= 0) {
			drawer.textSize(20);
			//drawer.text("spread", 280, 539);
			drawer.fill(255, 100);
			
			drawer.image(icon3, 270, 490, 80, 80);
			
			drawer.textSize(26); 
		}
		else {
			drawer.fill(255, 255, 255);
			drawer.text(Math.round((double)(p.getCooldowns()[1] - currentTime) * 10) / 10000 + "sec", 280, 540);
			drawer.fill(0, 102, 153);
		}
		if(p.getCooldowns()[3] - currentTime <= 0) {
			drawer.textSize(20);
			drawer.fill(255, 100);
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

