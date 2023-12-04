package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	public boolean up, down, left, right, shift, shoot, jump, pause;
	
	//on | off for the p key (hit box)
	boolean pPressed = false;
	public boolean displayHitBox = false;
	
	//on | off for the o key (info)
		boolean oPressed = false;
		public boolean displayInfo = true;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (GameState.state == GameState.play) {
			if (code == KeyEvent.VK_W) {
				up = true;
			}
			if (code == KeyEvent.VK_S) {
				down = true;
			}
			if (code == KeyEvent.VK_A) {
				left = true;
			}
			if (code == KeyEvent.VK_D) {
				right = true;
			}
			if (code == KeyEvent.VK_SHIFT) {
				shift = true;
			}
			if (code == KeyEvent.VK_E) {
				shoot = true;
			}
			if (code == KeyEvent.VK_SPACE) {
				jump = true;
			}
			if (code == KeyEvent.VK_ESCAPE) {
				if (!pause) {
					GameState.state = GameState.state == 0 ? GameState.pause : GameState.play;
					pause = true;
				}
			}
			if (code == KeyEvent.VK_P) {
				if (!pPressed) {
					displayHitBox = !displayHitBox;
					pPressed = true;
				}
			}
			if (code == KeyEvent.VK_O) {
				if (!oPressed) {
					displayInfo = !displayInfo;
					oPressed = true;
				}
			}
		}
		if (GameState.state == GameState.pause) {
			if (code == KeyEvent.VK_ESCAPE) {
				if (!pause) {
					GameState.state = GameState.state == 0 ? GameState.pause : GameState.play;
					pause = true;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			up = false;
		}
		if (code == KeyEvent.VK_S) {
			down = false;
		}
		if (code == KeyEvent.VK_A) {
			left = false;
		}
		if (code == KeyEvent.VK_D) {
			right = false;
		}
		if (code == KeyEvent.VK_SHIFT) {
			shift = false;
		}
		if (code == KeyEvent.VK_E) {
			shoot = false;
		}
		if (code == KeyEvent.VK_P) {
			pPressed = false;
		}
		if (code == KeyEvent.VK_O) {
			oPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			jump = false;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			pause = false;
		}
	}
}
