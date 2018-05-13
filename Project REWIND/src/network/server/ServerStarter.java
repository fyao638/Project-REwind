package network.server;

import com.jmr.wrapper.common.exceptions.NNCantStartServer;
import com.jmr.wrapper.server.Server;

import network.packet.GamePacket;

public final class ServerStarter {

	private Server server;
	
	public ServerStarter() {
		try {
			server = new Server(4444, 4444);
			server.setListener(new ServerListener());
			if (server.isConnected()) {
				System.out.println("Server has started.");
			}
		} catch (NNCantStartServer e) {
			e.printStackTrace();
		}
	}
}
