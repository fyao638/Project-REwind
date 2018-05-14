package clientside;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import gui.MenuScreen;
import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;
import network.client.ClientManager;
import network.server.ServerManager;
import processing.core.PApplet;
import sound.SoundManager;
/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class controls what screen is drawn repeatedly: PlayScreen or MenuScreen.
 *
 */
public class DrawingSurface extends PApplet{
/* Ghost class ?
 * Use the obstacle class (style)
 * move the code to playScreen
 * make a main menu
 * player should be the one shooting and rewinding (style)
 * 
 * 
 */
	private PlayScreen playScreen;
	private MenuScreen menuScreen;
	private ArrayList<Integer> keys;
	private boolean isOffline;
	
	private SoundManager sound;
	
	//States:
	// 0 = main menu
	// 1 = playScreen
	private int gameState;
	
	//Networking fields
	private ClientManager clientStarter;
	private ServerManager serverStarter;
	
	
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
			if(clientStarter != null) {
				if(clientStarter.isConnected()) {
					clientStarter.send(playScreen.getPacket());
					//playScreen.setIncomingPackets(clientStarter.getPacket());
					
				}
			}
				playScreen.draw(this);
		}
		if (serverStarter != null)
			serverStarter.send("Hello!");
		if (clientStarter != null)
			clientStarter.recieve();
	}
	public void startServer() {
		serverStarter = new ServerManager();
		serverStarter.setUp(this);
		
	}
	public void startClient() {
		clientStarter = new ClientManager();
		clientStarter.setUp(this, "localhost", 1337);
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
	
}


