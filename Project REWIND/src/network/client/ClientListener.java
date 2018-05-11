package network.client;

import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;

import network.packet.GamePacket;

public class ClientListener implements SocketListener {

	@Override
	public void connected(Connection con) {
		
	}

	@Override
	public void disconnected(Connection con) {
		System.out.println("you have disconnected");
		
	}

	@Override
	public void received(Connection con, Object obj) {
	}

}
