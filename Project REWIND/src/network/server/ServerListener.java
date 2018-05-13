package network.server;
import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;
import com.jmr.wrapper.server.ConnectionManager;

import network.packet.GamePacket;

public class ServerListener implements SocketListener{

	@Override
	public void connected(Connection con) {
		System.out.println("Client connected");
		ConnectionManager.getInstance().addConnection(con);
	}

	@Override
	public void disconnected(Connection con) {
		// TODO Auto-generated method stub
		System.out.println("Client disconnected");
		ConnectionManager.getInstance().addConnection(con);
	}

	@Override
	public void received(Connection con, Object obj) {
		if(obj instanceof GamePacket) {
			GamePacket packet = (GamePacket) obj;
			for(Connection c : ConnectionManager.getInstance().getConnections()) {
				c.sendUdp(packet);
			}
		}
	}

}
