package network.gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import network.frontend.NetworkDataObject;
import network.frontend.NetworkListener;
import network.frontend.NetworkMessenger;
import processing.core.PApplet;

import java.util.*;
import java.util.Queue;
import java.io.Serializable;


public class DrawingSurface extends PApplet implements NetworkListener
{

	private static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
	
	private float scaleX,scaleY;
	
	private ArrayList<Cursor> cursors;
	private ArrayList<Cursor> trails;

	private Cursor me;
	
	private Button colorButton;

	private static final String messageTypeInit = "CREATE_CURSOR";
	private static final String messageTypeMove = "MOUSE_MOVE";
	private static final String messageTypePress = "MOUSE_PRESS";
	private static final String messageTypeColor = "COLOR_SWITCH";

	private NetworkMessenger nm;
	
	public DrawingSurface () {
		
		cursors = new ArrayList<Cursor>();
		trails = new ArrayList<Cursor>();

		me = new Cursor();
		me.color = Color.BLACK;
		me.host = "me!";
		cursors.add(me);
	}
	
	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		colorButton = new Button(650,15,110,40,"Change Color",Color.RED);
	}

	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() { 
		background(255);   // Clear the screen with a white background

		pushMatrix();

		scaleX = (float)width/SCREEN_WIDTH;
		scaleY = (float)height/SCREEN_HEIGHT;

		scale(scaleX, scaleY);

		stroke(0,0,0,0);

		for (Cursor c : trails) {
			fill(c.color.getRed(), c.color.getGreen(), c.color.getBlue(), c.timeOut*255/Cursor.TIMEOUT_MAX);
			ellipse(c.x, c.y, 10, 10);
		}


		for (Cursor c : cursors) {
			fill(c.color.getRGB());
			ellipse(c.x, c.y, 10, 10);
		}
		
		fill(Color.BLACK.getRGB());
		text("Connected users: ", 10, 25);
		float x = 10 + textWidth("Connected users: ");

		for (Cursor c : cursors) {
			x += 25;
			fill(c.color.getRGB());
			rect(x, 15, 10, 10);
		}
		
		colorButton.draw();


		popMatrix();


		for (int i = trails.size()-1; i >= 0; i--) {
			Cursor c = trails.get(i);
			c.timeOut--;
			if (c.timeOut <= 0)
				trails.remove(i);
		}
		
		
		processNetworkMessages();

	}
	
	
	
	public void processNetworkMessages() {
		
		if (nm == null)
			return;
		
		Queue<NetworkDataObject> queue = nm.getQueuedMessages();
		
		while (!queue.isEmpty()) {
			NetworkDataObject ndo = queue.poll();

			String host = ndo.getSourceIP();

			if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
				if (ndo.message[0].equals(messageTypeMove)) {
					
						for (Cursor c : cursors) {
							if (c.host.equals(host)) {
								c.x = (Integer)ndo.message[1];
								c.y = (Integer)ndo.message[2];
							}
						}
					
				} else if (ndo.message[0].equals(messageTypePress)) {
					
						for (Cursor c : cursors) {
							if (c.host.equals(host)) {
								c.x = (Integer)ndo.message[1];
								c.y = (Integer)ndo.message[2];
								c.makeTrailCopy();
							}
						}
					
				} else if (ndo.message[0].equals(messageTypeInit)) {
					
						for (Cursor c : cursors) {
							if (c.host.equals(host))
								return;
						}
						Cursor c = new Cursor();
						c.x = (Integer)ndo.message[1];
						c.y = (Integer)ndo.message[2];
						c.color = (Color)ndo.message[3];
						c.host = host;
						cursors.add(c);
					
				} else if (ndo.message[0].equals(messageTypeColor)) {
					
						for (Cursor c : cursors) {
							if (c.host.equals(host)) {
								c.color = (Color)ndo.message[1];
							}
						}
					
				}
			} else if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
				nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeInit, me.x, me.y, me.color);
				
			} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
				
					if (ndo.dataSource.equals(ndo.serverHost)) {
						cursors.clear();
						cursors.add(me);
					} else {
						for (int i = cursors.size()-1; i >= 0; i--)
							if (cursors.get(i).host.equals(host))
								cursors.remove(i);
					}
				
			}

		}

	}
	
	
	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		

	}
	
	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
	}



	
	public void mouseDragged() {
		int x = (int)(mouseX / scaleX);
		int y = (int)(mouseY / scaleY);
		me.x = x;
		me.y = y;
		me.makeTrailCopy();
		if (nm != null)
			nm.sendMessage(NetworkDataObject.MESSAGE, messageTypePress, x, y);
	}

	@Override
	public void mouseMoved() {
		int x = (int)(mouseX / scaleX);
		int y = (int)(mouseY / scaleY);
		me.x = x;
		me.y = y;
		if (nm != null)
			nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, x, y);
	}


	public void mousePressed() {
		int x = (int)(mouseX / scaleX);
		int y = (int)(mouseY / scaleY);

		if (colorButton.contains(x, y)) {

			Color out = JColorChooser.showDialog(null, "Choose a color!", me.color);
			if (out != null)
				me.color = out;
			if (nm != null)
				nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeColor, me.color );

		} else {
			me.x = x;
			me.y = y;
			me.makeTrailCopy();
			if (nm != null)
				nm.sendMessage(NetworkDataObject.MESSAGE, messageTypePress,x,y);
		}
	}


	class Cursor implements Serializable {
		private static final long serialVersionUID = 1651417152103363037L;

		public static final int TIMEOUT_MAX = 250;

		public int x, y;
		public String host;
		public Color color;
		public int timeOut;

		public void makeTrailCopy() {
			Cursor c = new Cursor();
			c.x = x;
			c.y = y;
			c.host = host;
			c.color = color;
			c.timeOut = TIMEOUT_MAX;
			trails.add(c);
		}
	}
	
	
	class Button extends Rectangle2D.Float {
		private String text;
		private Color c;
		
		private int size;
		
		public Button(float x, float y, float w, float h, String text, Color c) {
			super(x,y,w,h);
			this.text = text;
			this.c = c;
			
			size = 1;
			pushStyle();
			textSize(size);
			while (textWidth(text)/w < 0.85) {
				size++;
				textSize(size);
			}
			size--;
			popStyle();
		}
		
		public void draw() {
			pushStyle();
			
			fill(c.getRGB());
			stroke(0);
			textSize(size);
			rect(x,y,width,height,width/10);
			textAlign(CENTER,CENTER);
			fill(0);
			text(text, x+width/2,y+height/2);
			
			popStyle();
		}
	}






}
