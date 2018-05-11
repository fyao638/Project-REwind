package clientside;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.jmr.wrapper.client.Client;
import com.jmr.wrapper.server.Server;

import gui.MenuScreen;
import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;
import network.client.ClientStarter;
import network.server.ServerStarter;
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
	
	private SoundManager sound;
	
	private ClientStarter c;
	private ServerStarter s;
	//States:
	// 0 = main menu
	// 1 = playScreen
	private int gameState;
	
	//Networking fields
	
	
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
			if(c != null) {
				if(c.isConnected()) {
					c.send(playScreen.getPacket());
					
				}
			}
			menuScreen.draw(this);
		}
		else {
			sound.stopMusic();
			playScreen.draw(this);
		}
	}
	public void startServer() {
		s = new ServerStarter();
	}
	public void startClient() {
		c = new ClientStarter();
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
}


