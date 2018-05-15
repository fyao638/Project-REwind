package clientside;

import java.util.ArrayList;
import gui.MenuScreen;
import network.backend.Packet;
import network.frontend.NetworkDataObject;
import network.frontend.NetworkListener;
import network.frontend.NetworkManagementPanel;
import network.frontend.NetworkMessenger;
import processing.core.PApplet;
import sound.SoundManager;
/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class controls what screen is drawn repeatedly: PlayScreen or MenuScreen.
 *
 */
public class DrawingSurface extends PApplet implements NetworkListener{
/* 
 * 
 */
	private PlayScreen playScreen;
	private MenuScreen menuScreen;
	private ArrayList<Integer> keys;
	private boolean isOffline;
	private NetworkMessenger nm;
	
	private SoundManager sound;
	
	private Packet packet;
	
	//States:
	// 0 = main menu
	// 1 = playScreen
	private int gameState;
	
	
	
	public DrawingSurface() {
		super();
		sound = new SoundManager();
		gameState = 0;
		playScreen = new PlayScreen();
		menuScreen = new MenuScreen();
		keys = new ArrayList<Integer>();
		
	}

	public void setup() {
		sound.playMenuMusic();
		menuScreen.setup(this);
		playScreen.setup(this);
	}

	//already an infinite loop
	public void draw() {
		if(gameState == 0) {
			menuScreen.draw(this);
		}
		else {
			sound.stopMusic();
			playScreen.draw(this);
		}
		
		if (!isOffline && nm != null) {
			nm.sendMessage(NetworkDataObject.MESSAGE, playScreen.getPacket());
		}
		else {
			if (nm == null)
				System.out.println("NULL");
		}
	}
	public void openNetworkingPanel() {
		NetworkManagementPanel nmp = new NetworkManagementPanel("SwingChat", 20, this);  
	}
	
	// 0 = menu, 1 = in game
	public void changeState(int newState) {
		gameState = newState;
	}
	
	public void keyPressed() {
		keys.add(keyCode);
	}

	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	public boolean isOffline() {
		return isOffline;
	}
	public void setIsOffline(boolean offline) {
		this.isOffline = offline;
	}
	public NetworkMessenger getNetM() {
		return nm;
	}
	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
	}
	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		packet = (Packet) ndo.message[0];
		
	}
	
	public Packet getPacket() {
		return packet;
	}
}


