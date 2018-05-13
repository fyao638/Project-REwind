package network.client;

import com.jmr.wrapper.client.Client;

import network.packet.GamePacket;

public class ClientStarter {

	private Client client;
	private ClientListener listener;
	
	
	public ClientStarter() {

		client = new Client("localhost", 1337, 1337);
		listener = new ClientListener();
		

		client.connect();
		
		client.setListener(listener);
		//infinite loop = constant data transfer
		// yes, this is the right way to do it
	}
	public void send(GamePacket packet) {
		client.getServerConnection().sendTcp(packet);
	}
	public boolean isConnected() {
		return client.isConnected();
	}
	// I have no idea how to access the packet in the drawingSurface
	
	public GamePacket getPacket() {
		return listener.getPacket();
	}
}
