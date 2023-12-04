package main;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ui.Component;
import ui.UI;

public class MouseHandler implements MouseListener, MouseMotionListener{
	
	GamePanel gp;
	
	public MouseHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		Component c = UI.getComponentAt((int) p.getX(), (int) p.getY());
		if (c != null && c.name != null && c.name.equals("resume")) {
			if (GameState.state == GameState.pause) {
				GameState.state = GameState.state == 0 ? GameState.pause : GameState.play;
			}
		}
		if (c != null && c.name != null && c.name.equals("quit")) {
			((JFrame) SwingUtilities.getWindowAncestor(gp)).dispose();
			System.exit(0);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (GameState.state == GameState.play) {
			GamePanel.mouseX = e.getX();
			GamePanel.mouseY = e.getY();
			String looking = "";
			
			double centerX = gp.screenWidth / 2.0;
	        double centerY = gp.screenHeight / 2.0 + 12; //because it's a little off since I want it below 12px the player

	        double dx = e.getX() - centerX;
	        double dy = centerY - e.getY();

	        double angle = Math.toDegrees(Math.atan2(dx, dy));
	        angle = (450 - angle) % 360;
			if (angle <= 112.5 && angle > 67.5) {
				looking = "n";
			}
			if (angle <= 67.5 && angle > 22.5) {
				looking = "ne";
			}
			if (angle <= 22.5 || angle > 337.5) {
				looking = "e";
			}
			if (angle <= 337.5 && angle > 292.5) {
				looking = "se";
			}
			if (angle <= 292.5 && angle > 247.5) {
				looking = "s";
			}
			if (angle <= 247.5 && angle > 202.5) {
				looking = "sw";
			}
			if (angle <= 202.5 && angle > 157.5) {
				looking = "w";
			}
			if (angle <= 157.5 && angle > 112.5) {
				looking = "nw";
			}
			gp.player.directionAngle = (int) angle;
			gp.player.direction = looking;
		}
	}
}
