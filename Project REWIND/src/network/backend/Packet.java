package network.backend;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

import network.frontend.NetworkDataObject;
import sprites.player.Player;
import sprites.projectile.Bullet;

public class Packet extends NetworkDataObject implements Serializable {
	private double playerX;
	private double playerY;
	private ArrayList<Point2D.Double> bulletsCoord;
	private ArrayList<Double> bulletsDir;
	
	public Packet(Player player, ArrayList<Bullet> bullets) {
		bulletsCoord = new ArrayList<Point2D.Double>();
		bulletsDir = new ArrayList<Double>();
		playerX = player.getX();
		playerY = player.getY();
		for (Bullet b : bullets) {
			bulletsCoord.add(new Point2D.Double(b.getX(), b.getY()));
			bulletsDir.add(b.getDirection());
		}
	}

	public void update (Player player) {
		playerX = player.getX();
		playerY = player.getY();
	}
	public void update (ArrayList<Bullet> bullets) {
		bulletsCoord = new ArrayList<Point2D.Double>();
		bulletsDir = new ArrayList<Double>();
		for (Bullet b : bullets) {
			bulletsCoord.add(new Point2D.Double(b.getX(), b.getY()));
			bulletsDir.add(b.getDirection());
		}
	}
	
	public double getPlayerX() {
		return playerX;
	}
	public double getPlayerY() {
		return playerY;
	}

	public ArrayList<Point2D.Double> getBullets() {
		return bulletsCoord;
	}
	public ArrayList<Double> getBulletsDir() {
		return bulletsDir;
	}
	
}
