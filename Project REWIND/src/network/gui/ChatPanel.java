package network.gui;
import java.awt.*;
import java.awt.event.*;
import java.util.Queue;

import javax.swing.*;

import network.frontend.NetworkDataObject;
import network.frontend.NetworkListener;
import network.frontend.NetworkMessenger;


public class ChatPanel extends JPanel implements ActionListener, NetworkListener
{

	private JTextArea inText;
	private JTextField outText;
	private JButton sendButton;

	private NetworkMessenger nm;

	public ChatPanel () {
		inText = new JTextArea();
		inText.setEditable(false);
		outText = new JTextField();
		sendButton = new JButton("Send");
		sendButton.addActionListener(this);

		setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(inText);
		add(scroll,BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(outText, BorderLayout.CENTER);
		bottomPanel.add(sendButton, BorderLayout.EAST);

		add(bottomPanel,BorderLayout.SOUTH);

		outText.addActionListener(this);

		JFrame window = new JFrame("Peer Chat");
		window.setBounds(300, 300, 800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == outText || source == sendButton) {
			if (!outText.getText().trim().equals("")) {
				String toGo = outText.getText().trim();
				if (nm != null)
					nm.sendMessage(NetworkDataObject.MESSAGE,toGo);
				inText.append("\nYou: "+toGo);
				outText.setText("");
			}
		}


	}


	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {

		if (ndo.messageType.equals(NetworkDataObject.MESSAGE))
			inText.append("\n" + ndo.dataSource + ": " + ndo.message[0]);
		else if (ndo.messageType.equals(NetworkDataObject.HANDSHAKE))
			inText.append("\n" + ndo.dataSource + " connected. ");
		else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
			if (ndo.dataSource.equals(ndo.serverHost)) 
				inText.append("\nDisconnected from server " + ndo.serverHost);
			else
				inText.append("\n" + ndo.dataSource + " disconnected. ");
		}
		
		Queue<NetworkDataObject> queue = nm.getQueuedMessages();
		queue.remove(ndo);		
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
	}
}
