package testers;

import processing.core.PConstants;

public class Hud {
	public Hud() {
		
	}
	public void draw(DrawingSurface d, long shotReadyTime, long rewindReadyTime, long currentTime) {
		d.noFill();
		
		d.stroke(0, 102, 153);
		d.strokeWeight(10); 
		
		d.rect(20, 480, 150, 100, 20);
		d.rect(190, 480, 150, 100, 20);
		
		d.fill(0, 102, 153, 128);
		
		if(shotReadyTime - currentTime > 0) {
			d.rectMode(PConstants.CORNERS);
			
			d.rect(20, 580, 170, 580 - 100 * (shotReadyTime - currentTime) / 1000, 20);
			
			d.rectMode(PConstants.CORNER);
		}
		if(rewindReadyTime - currentTime > 0) {
			d.rectMode(PConstants.CORNERS);
			
			d.rect(190, 580, 340, 580 - 100 * (rewindReadyTime - currentTime) / 15000, 20);
			
			d.rectMode(PConstants.CORNER);
		}
		
		d.noStroke();
		d.strokeWeight(1);
		
		
		d.textSize(26); 
		d.fill(0, 102, 153);
		
		if(shotReadyTime - currentTime <= 0) {
			d.text("SHOT", 60, 540);
		}
		else {
			d.text("0." + Math.round((double)(shotReadyTime - currentTime) * 10) / 1000 + "sec", 60, 540);
		}
		
		if(rewindReadyTime - currentTime <= 0) {
			d.text("REwind", 230, 540);
		}
		else {
			d.text(Math.round((double)(rewindReadyTime - currentTime) * 10) / 10000 + "sec", 230, 540);
		}
	}
}
