package sprites.projectile;

import java.util.ArrayList;

import gui.ScaleImage;
import sprites.obstacles.Obstacle;
import sprites.player.Player;

/**
 * 
 * @author Aakarsh Anand
 * This class represents the bouncing bullet shot by Technician.
 *
 */
public class BouncingBullet extends Bullet{
	private int timesBounced;
	
	public BouncingBullet(ScaleImage image, double x, double y, double dir, double speed, int type) {
		super(image, x, y, dir, speed, type);
		
		turn(getDirection());
		
	}
	// return true if it hits an obstacle and timesBounced > 3, false if otherwise
	public boolean checkObstacles(ArrayList<Obstacle> obstacles) {
		double direction = getDirection();
		for(Obstacle s : obstacles) {
			for(int i = 0; i < 4; i++) {
				if(s.getLineComposition()[i].intersects(this.getX(),this.getY(), BULLET_WIDTH, BULLET_HEIGHT)) {
					double incidence = (direction - s.getLineCompositionNormals()[i]);
					if (s.getLineCompositionNormals()[i] == 0) {
						turn(Math.PI - incidence);
					}
					else {
						turn((Math.PI/2) - incidence);
					}
					timesBounced++;
					if(timesBounced > 5)
						return true;
					}
				}
		}
			
			return false;
		}
		

	public int checkPlayer(Player player) {
		for(int i = 0; i < 4; i++) {
			if(player.intersects(this.getX(),this.getY(), BULLET_WIDTH, BULLET_HEIGHT)) {
				return 2;
						
			}
		}
		return 0;
	}
}
