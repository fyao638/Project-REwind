package clientside;

import java.util.ArrayList;

import gui.MenuScreen;
import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;
import processing.core.PApplet;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class controls what screen is drawn repeatedly: PlayScreen or MenuScreen.
 *
 */
public class DrawingSurface extends PApplet implements JayLayerListener {
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
	
	JayLayer sound;
	//States:
	// 0 = main menu
	// 1 = playScreen
	private int gameState;
	
	//Networking fields
	
	
	public DrawingSurface() {
		super();
		gameState = 0;
		playScreen = new PlayScreen();
		menuScreen = new MenuScreen();
		keys = new ArrayList<Integer>();
		
	}

	public void setup() {
		String[] songs = new String[]{"title2.mp3"};
		
		 sound=new JayLayer("audio/","audio/",false);
		  sound.addPlayList();
		  sound.addSongs(0,songs);
		  sound.changePlayList(0);
		  sound.addJayLayerListener(this);
		  
		  sound.nextSong();
		
		menuScreen.setup(this);
		playScreen.setup(this);
	}

	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() {
		if(gameState == 0) {
			menuScreen.draw(this);
		}
		else {
			if(sound.isPlaying()) {
				sound.stopSong();
			}
			playScreen.draw(this);
		}
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

	@Override
	public void musicStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void musicStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playlistEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void songEnded() {
		// TODO Auto-generated method stub
		
	}
}


