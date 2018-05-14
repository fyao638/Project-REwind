package network.server;

import processingNet.*;

import network.packet.PacketManager;
import processing.core.PApplet;

public final class ServerManager {

	private Server server;
	
	public ServerManager() {
	}
	
	public void setUp(PApplet marker) {
		server = new Server(marker, 1337);
	}
	
	public void send(String message) {
		server.write(message);
	}
}
