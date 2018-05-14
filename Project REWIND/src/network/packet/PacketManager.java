package network.packet;

import java.io.Serializable;
import java.util.ArrayList;

import clientside.PlayScreen;
import sprites.player.Player;
import sprites.projectile.Bullet;


public class PacketManager {
	
	String data;
	
	public PacketManager() {
		data = "";
	}
	
	public String toString() {
		return data;
	}
	
	public String fillPacket(PlayScreen screen) {
		Player thisPlayer = screen.getPlayer();
		ArrayList<Bullet> bullets = screen.getBullets();
		
		data += thisPlayer.toString() + "|";
		for (Bullet b : bullets) {
			data += b.toString() + ",";
		}
		return data;
	}
}
