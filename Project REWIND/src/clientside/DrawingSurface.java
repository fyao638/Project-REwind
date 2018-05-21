package clientside;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Queue;

import gui.MenuScreen;
import gui.SelectionScreen;
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
import sprites.player.Technician;
import sprites.projectile.Projectile;
/**
 * 
 * @author Aakarsh Anand, Frank Yao, Michael Kim
 * This class controls what screen is drawn repeatedly: PlayScreen or MenuScreen.
 *
 */
public class DrawingSurface extends PApplet implements NetworkListener{
/* BUGS:
 * - people dont die if they are under 0 health
 * - Only Assault works
 * 
 * TO DO:
 * - Fix the classes, you cant select anything but assault
 * - SFX and music?
 * -fix hud icons
 * 
 * 
 * 
 */
	private PlayScreen playScreen;
	private MenuScreen menuScreen;
	private SelectionScreen selectionScreen;
	private ArrayList<Integer> keys;
	private boolean isOffline;
	private NetworkMessenger nm;
	
	private static final String messageTypeMove = "MOVE";
	private static final String messageTypeTurn = "TURN";
	private static final String messageTypeRewind = "REWIND";
	private static final String messageTypeShoot = "SHOOT";
	private static final String messageTypeSecondary = "SECONDARY";
	private static final String messageTypeShift = "SHIFT";
	private static final String messageTypeReset = "RESET";
	private static final String messageTypePType = "PTYPE";
	
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
		selectionScreen = new SelectionScreen();
		keys = new ArrayList<Integer>();
		
	}

	public void setup() {
		sound.playMenuMusic();
		menuScreen.setup(this);
		selectionScreen.setup(this);
	}

	//already an infinite loop
	public void checkConnection() {
		//System.out.println("hello world");
		if(clientCount > 0) {
			gameState = 2;
			playScreen.setup(this);
			if(clientCount == 1) {
				playScreen.setYouType(selectionScreen.getType());
				playScreen.spawnNewHost();
				System.out.println("HOST");
				isConnected = true;
			}
			else {
				playScreen.setYouType(selectionScreen.getType());
				this.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypePType, selectionScreen.getType());
				playScreen.spawnNewClient();
				System.out.println("CLIENT");
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
		else if(gameState == 1) {
			//playScreen.setHostType(selectionScreen.getType());
			selectionScreen.draw(this);
		}
		else {
			sound.stopMusic();
			//System.out.println(selectionScreen.getType());
			
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
			
			Player p = playScreen.getEnemyPlayer();
			Player p2 = playScreen.getYouPlayer();

			//IT SHOULDNT CALL PLAYER (SHOULD BE THE OTHER PLAYER)
			if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
				if (ndo.message[0].equals(messageTypePType)) {
					playScreen.updateEnemy(this, (Integer) ndo.message[1]);
					if(playScreen.getIsHost()) {
						this.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypePType, selectionScreen.getType());
					}
					
				} else if (ndo.message[0].equals(messageTypeMove)) {
					p.walk((Integer)ndo.message[1],(Integer)ndo.message[2], playScreen.getObstacles());
					
					//move the player
				}
				else if (ndo.message[0].equals(messageTypeTurn)) {
					//turn the player
					if(p != null) {
						p.turnToward((Integer)ndo.message[1],(Integer)ndo.message[2]);
					}
				}
				else if (ndo.message[0].equals(messageTypeShoot)) {
					//player shoots
					playScreen.getOtherBullets().add(p.shoot(playScreen.getAssets().get(2)));
				}
				//player uses secondary
				else if (ndo.message[0].equals(messageTypeSecondary)) {
					if((Integer)ndo.message[1] == 1) {

						ArrayList<Projectile> fan = ((Assault)(p)).secondary(playScreen.getAssets().get(3));

						for(Projectile b : fan) {
							playScreen.getOtherBullets().add(b);
						}
					}
					else if((Integer)ndo.message[1] == 2) {
						ArrayList<Projectile> fan = ((Demolitions) p).secondary(playScreen.getAssets().get(13));
						for(Projectile b : fan) {
							playScreen.getOtherBullets().add(b);
						}
					}
					else {
						ArrayList<Projectile> fan = ((Technician) p).secondary(playScreen.getAssets().get(2));
						for(Projectile b : fan) {
							playScreen.getOtherBullets().add(b);
						}
					}
				}
				else if (ndo.message[0].equals(messageTypeShift)) {
					if((Integer)ndo.message[1] == 1) {
						for (int i = 0; i < (int) (10 + Math.random() * 10); i++) {
							playScreen.getParticles().add(new Particle(playScreen.getAssets().get(10), p.x + p.getWidth() / 2, p.y + p.getHeight() / 2, 20, 20, 1));
						}
						((Assault)p).shiftAbility(playScreen.getObstacles());
						
						//player uses flash
					}
					else if((Integer)ndo.message[1] == 2) {
						ArrayList<Projectile> fan = ((Demolitions)(p)).shiftAbility(playScreen.getAssets().get(12));
						for(Projectile b : fan) {
							playScreen.getOtherBullets().add(b);
						}
					}
					else {
						((Technician) p).shiftAbility();
					}
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
						p.setHealth(5);
						p2.setHealth(5);
						p2.moveToLocation(800/2-Player.PLAYER_WIDTH/2,500);
						p2.win();
						
					}
					else {
						p.moveToLocation(800/2-Player.PLAYER_WIDTH/2,500);
						p.setHealth(5);
						p2.setHealth(5);
						p2.moveToLocation(800/2-Player.PLAYER_WIDTH/2,50);
						p2.win();
					}
				}
				else {
					System.out.println("Its not detecting it");
				}
			} else if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
				/*
				for (int i = 0; i < ndo.message.length; i++) {
					InetAddress address = (InetAddress)ndo.message[i];
					
				}
				*/
				//System.out.println("HELLO");
				clientCount = ndo.message.length;
				//System.out.println(clientCount);
				
				
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


