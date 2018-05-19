package clientside;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Queue;

import gui.MenuScreen;
import network.frontend.NetworkDataObject;
import network.frontend.NetworkListener;
import network.frontend.NetworkManagementPanel;
import network.frontend.NetworkMessenger;
import processing.core.PApplet;
import sound.SoundManager;
import sprites.Particle;
import sprites.player.Assault;
import sprites.player.Demolitions;
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
	private static final String messageTypeReset = "RESET";
	
	private int clientCount = 0;
	private SoundManager sound;
	
	private boolean isConnected;
	//States:
	// 0 = main menu
	// 1 = playScreen
	private int gameState;
	
	
	
	public DrawingSurface() {
		super();
		isConnected = false;
		sound = new SoundManager();
		gameState = 0;
		playScreen = new PlayScreen();
		menuScreen = new MenuScreen();
		keys = new ArrayList<Integer>();
		
	}

	public void setup() {
		sound.playMenuMusic();
		menuScreen.setup(this);
	}

	//already an infinite loop
	public void checkConnection() {
		
		if(clientCount > 0) {
			gameState = 1;
			playScreen.setup(this);
			if(clientCount == 1) {
				sound.stopMusic();
				playScreen.spawnNewHost();
				isConnected = true;
			}
			else {
				sound.stopMusic();
				playScreen.spawnNewClient();
				isConnected = true;
			}
		}
	}
	public void draw() {
		
		if(clientCount == 0) {
			isConnected = false;
		}
		
		if(!isConnected) {
			checkConnection();
			processNetworkMessages();
		}
		if(gameState == 0) {
			menuScreen.draw(this);
		}
		else {
			processNetworkMessages();
			playScreen.draw(this);
		}
	}
	public void processNetworkMessages() {
		if (nm == null)
			return;
		
		Queue<NetworkDataObject> queue = nm.getQueuedMessages();
		
		while (!queue.isEmpty()) {
			NetworkDataObject ndo = queue.poll();

			String host = ndo.getSourceIP();
			
			Demolitions p = (Demolitions) playScreen.getEnemyPlayer();
			Demolitions p2 = (Demolitions) playScreen.getClientPlayer();

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
					
					ArrayList<Projectile> fan = p.secondary(playScreen.getAssets().get(12));

					for(Projectile b : fan) {
						playScreen.getOtherBullets().add(b);
					}
				}
				else if (ndo.message[0].equals(messageTypeFlash)) {
					for (int i = 0; i < (int) (10 + Math.random() * 10); i++) {
						playScreen.getParticles().add(new Particle(playScreen.getAssets().get(10), p.x + p.getWidth() / 2, p.y + p.getHeight() / 2, 20, 20));
					}
					//p.shiftAbility(playScreen.getObstacles());
					
					//player uses flash
				}
				else if (ndo.message[0].equals(messageTypeRewind)) {
					p.moveToLocation((Double) ndo.message[1], (Double) ndo.message[2]);
				}
				else if(ndo.message[0].equals(messageTypeReset)) {
					
					p2.setCooldowns(0, 0);
					p2.setCooldowns(1, 0);
					p2.setCooldowns(2, 0);
					p2.setCooldowns(3, 0);
					p2.setCooldowns(4, 0);
					if((Boolean)ndo.message[1]) {
						//isHost
						p.moveToLocation(800/2-Player.PLAYER_WIDTH/2,50);
						p.changeHealth(5);
						p2.moveToLocation(800/2-Player.PLAYER_WIDTH/2,500);
						p2.win();
						
					}
					else {
						p.moveToLocation(800/2-Player.PLAYER_WIDTH/2,500);
						p.changeHealth(5);
						p2.moveToLocation(800/2-Player.PLAYER_WIDTH/2,50);
						p2.win();
					}
				}
			} else if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
				/*
				for (int i = 0; i < ndo.message.length; i++) {
					InetAddress address = (InetAddress)ndo.message[i];
					
				}
				*/
				clientCount = ndo.message.length;
				
				
				
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
	public int getClientCount() {
		return clientCount;
	}
	public SoundManager getSoundM() {
		return sound;
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


