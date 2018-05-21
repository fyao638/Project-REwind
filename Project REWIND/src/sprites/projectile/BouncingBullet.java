package sprites.projectile;

import java.util.ArrayList;

import processing.core.PImage;
import sprites.obstacles.Obstacle;

/**
 * 
 * @author Aakarsh Anand
 * This class represents the bouncing bullet shot by Technician.
 *
 */
public class BouncingBullet extends Bullet{
	private int timesBounced;
	
	public BouncingBullet(PImage image, double x, double y, double dir, double speed) {
		super(image, x, y, dir, speed);
		
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
					if(timesBounced > 4)
						return true;
					}
				}
		}
			
			return false;
		}
	
}
