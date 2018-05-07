package sprites;

import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Player extends Sprite {

	public static final int PLAYER_WIDTH = 40;
	public static final int PLAYER_HEIGHT = 60;

	public Player(PImage img, int x, int y) {
		super(img, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
	}

	// METHODS
	public void walk(int xDir, int yDir) {
		this.setXVel(xDir * 3);
		this.setYVel(yDir * 3);
	}

	public void act(ArrayList<Shape> obstacles) {
		this.moveByAmount(this.getXVel(), this.getYVel());
	}
}
