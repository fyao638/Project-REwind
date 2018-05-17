package clientside;

import java.util.ArrayList;
import java.util.Queue;

import gui.MenuScreen;
import network.backend.Packet;
import network.frontend.NetworkDataObject;
import network.frontend.NetworkListener;
import network.frontend.NetworkManagementPanel;
import network.frontend.NetworkMessenger;
import processing.core.PApplet;
import sound.SoundManager;
import sprites.player.Assault;
import sprites.player.Player;
import sprites.projectile.Projectile;
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
	
	private static final String messageTypeMove = "MOVE";
	private static final String messageTypeTurn = "TURN";
	private static final String messageTypeRewind = "REWIND";
	private static final String messageTypeShoot = "SHOOT";
	private static final String messageTypeSecondary = "SECONDARY";
	private static final String messageTypeFlash = "FLASH";
	
	
	private SoundManager sound;
	
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
			processNetworkMessages();
		}
	}
	public void processNetworkMessages() {
		if (nm == null)
			return;
		
		Queue<NetworkDataObject> queue = nm.getQueuedMessages();
		
		while (!queue.isEmpty()) {
			NetworkDataObject ndo = queue.poll();

			String host = ndo.getSourceIP();
			
			Assault p = (Assault) playScreen.getEnemyPlayer();

			//IT SHOULDNT CALL PLAYER (SHOULD BE THE OTHER PLAYER)
			if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
				if (ndo.message[0].equals(messageTypeMove)) {
					p.walk((Integer)ndo.message[1],(Integer)ndo.message[2], playScreen.getObstacles());
					
					//move the player
				}
				else if (ndo.message[0].equals(messageTypeTurn)) {
					//turn the player
					p.turnToward((Integer)ndo.message[1],(Integer)ndo.message[2]);
					
				}
				else if (ndo.message[0].equals(messageTypeShoot)) {
					//player shoots
					playScreen.getOtherBullets().add(p.shoot(playScreen.getAssets().get(2)));
				}
				else if (ndo.message[0].equals(messageTypeSecondary)) {
					//player uses secondary
					
					ArrayList<Projectile> fan = p.secondary(playScreen.getAssets().get(3));

					for(Projectile b : fan) {
						playScreen.getOtherBullets().add(b);
					}
					
					
				}
				else if (ndo.message[0].equals(messageTypeFlash)) {
					p.shiftAbility(playScreen.getObstacles());
					//player uses flash
				}
				else if (ndo.message[0].equals(messageTypeRewind)) {
					p.moveToLocation((int) ndo.message[1], (int) ndo.message[2]);
					//PLAYER DOESNT HAVE A REWIND METHOD
				}
				else {
					System.out.println("Its not detecting it");
				}
			}
		}
	}
	public void openNetworkingPanel() {
		NetworkManagementPanel nmp = new NetworkManagementPanel("REwind", 2, this);  
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
		// TODO Auto-generated method stub
	}
	
}


