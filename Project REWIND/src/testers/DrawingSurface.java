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
	private ArrayList<Integer> keys;
	
	public DrawingSurface() {
		super();
		playScreen = new PlayScreen();
		keys = new ArrayList<Integer>();
		
	}

	public void setup() {
		playScreen.setup(this);
	}

	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() {
		playScreen.draw(this);
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


