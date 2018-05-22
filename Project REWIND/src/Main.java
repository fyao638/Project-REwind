
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clientside.DrawingSurface;
import gui.MenuScreen;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * @version 1.0
 * This class handles the initial setup of the window and DrawingSurface.
 *
 */
public class Main {

	private JFrame window;
	
	private JPanel cardPanel;
	  
	private MenuScreen panel1;
	private DrawingSurface panel2;
	
	private PSurfaceAWT.SmoothCanvas processingCanvas;
	
	public Main() {
		panel2 = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, panel2);
		
		PSurfaceAWT surf = (PSurfaceAWT) panel2.getSurface();
		processingCanvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		window = (JFrame)processingCanvas.getFrame();

		window.setBounds(100,0,800, 600);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		cardPanel = new JPanel();
	    CardLayout cl = new CardLayout();
	    cardPanel.setLayout(cl);
	    
	    window.getContentPane().removeAll();
	    
	    panel2 = new DrawingSurface();
	    
	    cardPanel.add(processingCanvas,"2");
	    
	    window.setLayout(new BorderLayout());
	    
	    window.add(cardPanel);
	    window.revalidate();
	}
	

	public static void main(String[] args)
	{
		Main m = new Main();
	}
  
	public void changePanel() {
		((CardLayout)cardPanel.getLayout()).next(cardPanel);
		processingCanvas.requestFocus();
	}
}


