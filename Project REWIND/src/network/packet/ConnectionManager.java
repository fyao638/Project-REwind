package network.packet;

import java.util.ArrayList;

import com.jmr.wrapper.common.Connection;

public class ConnectionManager {

	public static ConnectionManager instance;
	
	ArrayList<Connection> connections= new ArrayList<Connection>();
	
	private ConnectionManager() {
		
	}
	
	public void addConnection(Connection con) {
		connections.add(con);
	}
	public void removeConnection(Connection con) {
		connections.remove(con);
	}
	public ArrayList<Connection> getConnections(){
		return connections;
	}
	
	public static ConnectionManager getInstance() {
		return instance;
	}
}
