import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Ball extends Rectangle2D.Double {
	private double xvel, yvel;
	private static final int BALL_SPEED = 5;
	private static final double MAX_BOUNCE_ANGLE = Math.PI / 2.0; // 90 degrees
	private static final double MIN_BOUNCE_ANGLE = Math.PI / 6.0; // 30 degrees
	private int playerTurn; // 0 for first player, 1 for second player
	
	public Ball(double x, double y, double width, double height) {
		super(x, y, width, height);
		xvel = 0;
		yvel = 5;
		playerTurn = 1;
	}
	
	public void update() {
		this.x += xvel;
		this.y += yvel;
	}
	
	public void oppXVel() {
		xvel = -xvel;
	}
	
	public void oppYVel() {
		yvel = -yvel;
	}
	
	// position of ball on paddle determines the ball's bounce angle
	public void calcBounce(int paddleX, int paddleWidth, int paddlePlayer) {
		// intersects() was being called multiple times, so this makes it so that each player hits exactly once every time
		if(playerTurn == paddlePlayer) {
			double relPosToPaddle = (paddleX + (paddleWidth / 2)) - (this.x + (this.width / 2)); // middle of paddle - middle of ball
			
			double normalizedPos = (double) relPosToPaddle / ((double) paddleWidth / 2.0); // decimal from -1 to 1
			
			// correct normalizedPos just in case
			if(normalizedPos > 1) {
				normalizedPos = 1;
			}
			else if(normalizedPos < -1) {
				normalizedPos = -1;
			}
			
			double bounceAngle = Math.PI / 2.0; // 90 degrees
			
			// hits right of left (not in middle) of paddle
			if(normalizedPos != 0) {
				bounceAngle = Math.abs(normalizedPos) * MAX_BOUNCE_ANGLE; // first quadrant
				System.out.println("hi");
				// if angle is lower than minimum bounce angle, make it equal to the minimum bounce angle
				if(bounceAngle < MIN_BOUNCE_ANGLE) {
					bounceAngle = MIN_BOUNCE_ANGLE;
				}
			}
			
			// if ball hits left side of paddle
			if(normalizedPos > 0 && normalizedPos <= 1) {
				bounceAngle += Math.PI - (bounceAngle * 2.0); // move angle to second quadrant
				System.out.println("left");
			} 
			
			System.out.println("angle: " + bounceAngle * (180.0 / Math.PI));
			
			xvel = Math.cos(bounceAngle) * (double) BALL_SPEED;
			yvel = paddlePlayer == 0 ? Math.sin(bounceAngle) * (double) BALL_SPEED : -Math.sin(bounceAngle) * (double) BALL_SPEED;
			System.out.println("yvel: " + yvel);
			playerTurn = (playerTurn == 0) ? 1 : 0; // switch playerTurn
		}
	}
}
