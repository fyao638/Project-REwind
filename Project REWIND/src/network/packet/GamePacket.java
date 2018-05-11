package network.packet;

import java.io.Serializable;

import sprites.player.Player;


public class GamePacket implements Serializable {

	private int x, y;
	private double dir;
	
	public GamePacket(Player player){
		x = (int) player.getX();
		y = (int) player.getY();
		dir = player.getDirection();
	}
	
}
