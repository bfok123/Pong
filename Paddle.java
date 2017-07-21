import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Paddle extends Rectangle implements KeyListener {
	private boolean toggleLeftOn, toggleRightOn;
	private int xvel, left, right;
	
	public Paddle(int x, int y, int width, int height, int left, int right) {
		super(x, y, width, height);
		xvel = 0;
		toggleLeftOn = false;
		toggleRightOn = false;
		this.left = left;
		this.right = right;
	}
	
	public void update() {
		x += xvel;
		if(x <= 0) {
			x = 0;
		}
		else if(x >= 433) {
			x = 433;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == left) {
			xvel = -10;
			toggleLeftOn = true;
		} else if(e.getKeyCode() == right) {
			xvel = 10;
			toggleRightOn = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == left) {
			toggleLeftOn = false;
			
			if(!toggleRightOn) {
				xvel = 0;
			}
		} else if(e.getKeyCode() == right) {
			toggleRightOn = false;
			
			if(!toggleLeftOn) {
				xvel = 0;
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
}
