package network.client;

import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;
import com.jmr.wrapper.server.ConnectionManager;

import network.packet.GamePacket;

public class ClientListener implements SocketListener {

	GamePacket packet;
	
	@Override
	public void connected(Connection con) {
		System.out.println("Connected to server");
	}

	@Override
	public void disconnected(Connection con) {
		System.out.println("you have disconnected");
		
	}

	@Override
	public void received(Connection con, Object obj) {
		if(obj instanceof GamePacket) {
			packet = (GamePacket) obj;
			// unwrap the packet and use it to draw the other player
			
		}
	}
	public GamePacket getPacket() {
		return packet;
	}
}
