import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pong extends JFrame {
	private Timer updateTimer;
	private Paddle[] paddles;
	private Ball ball;
	private Player playerOne;
	private Player playerTwo;

	public Pong() {
		this.setSize(500, 500);
		this.setTitle("Pong");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		playerOne = new Player();
		playerTwo = new Player();
		ball = new Ball(245, 200, 10, 10);
		paddles = new Paddle[2];
		paddles[0] = new Paddle(225, 10, 50, 10, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
		paddles[1] = new Paddle(225, 400, 50, 10, KeyEvent.VK_A, KeyEvent.VK_D);
		
		this.addKeyListener(paddles[0]);
		this.addKeyListener(paddles[1]);
		
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				g2.setColor(Color.BLACK);
				
				for(Paddle paddle : paddles) {
					g.drawRect(paddle.x, paddle.y, paddle.width, paddle.height);
				}
				
				Ellipse2D.Double ballShape = new Ellipse2D.Double(ball.x, ball.y, ball.width, ball.height);
				g2.draw(ballShape);
				
				g.setColor(Color.GRAY);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("" + playerOne.getScore(), 245, 100);
				g.drawString("" + playerTwo.getScore(), 245, 300);
			}
		};
		
		updateTimer = new Timer(16, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(Paddle paddle : paddles) {
					paddle.update();
				}
				ball.update();
				
				panel.repaint();
				
				checkForCollision();
				updateTimer.restart();
			}
		});
		
		updateTimer.start();
		
		this.add(panel);
		
		this.setVisible(true);
	}
	
	public void checkForCollision() {
		// point scored
		if(ball.y <= 10) {
			playerTwo.setScore(playerTwo.getScore() + 1);
			resetBall();
		}
		else if(ball.y >= 400) {
			playerOne.setScore(playerOne.getScore() + 1);
			resetBall();
		}
		// wall collision 
		else if(ball.x <= 0 || ball.x >= 475) {
			ball.oppXVel();
		}
		// paddle collisions
		else if(ball.intersects(paddles[0])) {
			ball.calcBounce(paddles[0].x, paddles[0].width, 0);
		}
		else if(ball.intersects(paddles[1])) {
			ball.calcBounce(paddles[1].x, paddles[1].width, 1);
		}
	}
	
	public void resetBall() {
		ball.x = 250;
		ball.y = 200;
	}
	
	public static void main(String[] args) {
		new Pong();
	}
}
