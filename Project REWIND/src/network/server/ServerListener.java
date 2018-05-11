package network.server;

import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;

import network.packet.GamePacket;

public class ServerListener implements SocketListener{

	@Override
	public void connected(Connection con) {
		// TODO Auto-generated method stub
		System.out.println("Client connected");
	}

	@Override
	public void disconnected(Connection con) {
		// TODO Auto-generated method stub
		System.out.println("Client disconnected");
	}

	@Override
	public void received(Connection con, Object obj) {
		System.out.println(obj.toString());
	}

}
