package network.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import network.frontend.NetworkManagementPanel;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Main {
	public static void main(String args[]) {
		
		String[] options = { "Chat (Swing)", "Draw (Processing)" };
		int demo = JOptionPane.showOptionDialog(null, "Which demo?", "Networking Demo", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		
		if (demo == 0) {
			ChatPanel panel = new ChatPanel();
			
			// To open the network management window, just create an object of type NetworkManagementPanel.
			NetworkManagementPanel nmp = new NetworkManagementPanel("SwingChat", 20, panel);  
		} else if (demo == 1) {
			
			DrawingSurface drawing = new DrawingSurface();
			PApplet.runSketch(new String[]{""}, drawing);
			PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
			PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
			JFrame window = (JFrame)canvas.getFrame();

			window.setSize(800, 600);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);

			window.setVisible(true);
			
			NetworkManagementPanel nmp = new NetworkManagementPanel("ProcessingDrawing", 6, drawing);
		}

		
	}
}
