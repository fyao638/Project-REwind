package clientside;
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
 * @author  Frank Yao
 * @version 1.0
 * This class controls what screen is drawn repeatedly: PlayScreen or MenuScreen.
 * This class is also responsible for handling messages from the network and displaying them on playScreen.
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
	
	public static final String messageTypeMove = "MOVE";
	public static final String messageTypeTurn = "TURN";
	public static final String messageTypeRewind = "REWIND";
	public static final String messageTypeShoot = "SHOOT";
	public static final String messageTypeSecondary = "SECONDARY";
	public static final String messageTypeShift = "SHIFT";
	public static final String messageTypeReset = "RESET";
	public static final String messageTypePType = "PTYPE";
	
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
			changeState(2);
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
			selectionScreen.draw(this);
		}
		else {
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
			
			Player enemyPlayer = playScreen.getEnemyPlayer();
			Player myPlayer = playScreen.getYouPlayer();

			//IT SHOULDNT CALL PLAYER (SHOULD BE THE OTHER PLAYER)
			if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
				if (ndo.message[0].equals(messageTypePType)) {
					playScreen.updateEnemy(this, (Integer) ndo.message[1]);
					if(playScreen.getIsHost()) {
						this.getNetM().sendMessage(NetworkDataObject.MESSAGE, messageTypePType, selectionScreen.getType());
					}
					
				} else if (ndo.message[0].equals(messageTypeMove)) {
					enemyPlayer.walk((Integer)ndo.message[1],(Integer)ndo.message[2], playScreen.getObstacles());
					
					//move the player
				}
				else if (ndo.message[0].equals(messageTypeTurn)) {
					//turn the player
					if(enemyPlayer != null) {
						enemyPlayer.turnToward((Integer)ndo.message[1],(Integer)ndo.message[2]);
					}
				}
				else if (ndo.message[0].equals(messageTypeShoot)) {
					//player shoots
					playScreen.getOtherBullets().add(enemyPlayer.shoot(playScreen.getAssets().get(2)));
				}
				//player uses secondary
				else if (ndo.message[0].equals(messageTypeSecondary)) {
					if((Integer)ndo.message[1] == 1) {

						ArrayList<Projectile> fan = ((Assault)(enemyPlayer)).secondary(playScreen.getAssets().get(3));

						for(Projectile b : fan) {
							playScreen.getOtherBullets().add(b);
						}
					}
					else if((Integer)ndo.message[1] == 2) {
						ArrayList<Projectile> fan = ((Demolitions) enemyPlayer).secondary(playScreen.getAssets().get(13));
						for(Projectile b : fan) {
							playScreen.getOtherBullets().add(b);
						}
					}
					else {
						ArrayList<Projectile> fan = ((Technician) enemyPlayer).secondary(playScreen.getAssets().get(2));
						for(Projectile b : fan) {
							playScreen.getOtherBullets().add(b);
						}
					}
				}
				else if (ndo.message[0].equals(messageTypeShift)) {
					if((Integer)ndo.message[1] == 1) {
						for (int i = 0; i < (int) (10 + Math.random() * 10); i++) {
							playScreen.getParticles().add(new Particle(playScreen.getAssets().get(10), enemyPlayer.x + enemyPlayer.getWidth() / 2, enemyPlayer.y + enemyPlayer.getHeight() / 2, 20, 20, 1));
						}
						((Assault)enemyPlayer).shiftAbility(playScreen.getObstacles());
						
						//player uses flash
					}
					else if((Integer)ndo.message[1] == 2) {
						ArrayList<Projectile> fan = ((Demolitions)(enemyPlayer)).shiftAbility(playScreen.getAssets().get(12));
						for(Projectile b : fan) {
							playScreen.getOtherBullets().add(b);
						}
					}
					else {
						((Technician) enemyPlayer).shiftAbility();
					}
				}
				else if (ndo.message[0].equals(messageTypeRewind)) {
					enemyPlayer.moveToLocation((Double) ndo.message[1], (Double) ndo.message[2]);
				}
				else if(ndo.message[0].equals(messageTypeReset)) {
					
					playScreen.resetProjectiles();
					
					if((Boolean)ndo.message[1]) {
						//isHost
						enemyPlayer.moveToLocation(800/2-Player.PLAYER_WIDTH/2,50);
						enemyPlayer.resetHealth();
						myPlayer.resetHealth();
						myPlayer.moveToLocation(800/2-Player.PLAYER_WIDTH/2,500);
						myPlayer.win();
						
					}
					else {
						enemyPlayer.moveToLocation(800/2-Player.PLAYER_WIDTH/2,500);
						enemyPlayer.resetHealth();
						myPlayer.resetHealth();
						myPlayer.moveToLocation(800/2-Player.PLAYER_WIDTH/2,50);
						myPlayer.win();
					}
					myPlayer.setCooldowns(0, millis() + 1000);
					myPlayer.setCooldowns(1, millis() + 1000);
					myPlayer.setCooldowns(2, millis() + 1000);
					myPlayer.setCooldowns(3, millis() + 1000);
					myPlayer.setCooldowns(4, millis() + 1000);
					enemyPlayer.setCooldowns(0, millis() + 1000);
					enemyPlayer.setCooldowns(1, millis() + 1000);
					enemyPlayer.setCooldowns(2, millis() + 1000);
					enemyPlayer.setCooldowns(3, millis() + 1000);
					enemyPlayer.setCooldowns(4, millis() + 1000);
				}
				else {
					System.out.println("Its not detecting it");
				}
			} else if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
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
		if(newState == 2) {
			sound.next();
		}
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


