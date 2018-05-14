package network.client;

import processingNet.Client;
import network.packet.PacketManager;
import processing.core.PApplet;

public class ClientManager {

	private Client client;
	
	public ClientManager() {
		
	}
	
	public void setUp(PApplet drawer, String host, int port) {
		client = new Client(drawer, host, port);
	}
	
	public void send(PacketManager packet) {
	}
	
	// return true if client is connected to a server, false otherwise
	public boolean isConnected() {
		if (client != null) {
			return client.active();
		}
		return false;
	}
	
	public void recieve() {
		if (client.available() > 0) {
			System.out.println(client.readString());
		}
	}
	
}
