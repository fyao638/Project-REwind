package network.client;

import com.jmr.wrapper.client.Client;

public class ClientStarter {

	private Client client;
	
	public ClientStarter() {
		client = new Client("localhost", 1337, 1337);

		client.connect();
		
		if (client.isConnected()) {
			client.getServerConnection().sendTcp("I have connected");
		}
	}
}
