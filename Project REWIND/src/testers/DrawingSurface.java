package testers;

import java.util.ArrayList;

import processing.core.PApplet;


public class DrawingSurface extends PApplet {
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
	//States:
	// 0 = main menu
	// 1 = playScreen
	private int state;
	
	public DrawingSurface() {
		super();
		state = 0;
		playScreen = new PlayScreen();
		menuScreen = new MenuScreen();
		keys = new ArrayList<Integer>();
		
	}

	public void setup() {
		menuScreen.setup(this);
		playScreen.setup(this);
	}

	public void draw() {
		if(state == 0) {
			menuScreen.draw(this);
		}
		else {
			playScreen.draw(this);
		}
	}
	public void swapState() {
		if(state == 0) {
			state = 1;
		}
		else {
			state = 0;
		}
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

