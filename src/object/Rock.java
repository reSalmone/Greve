package object;

import java.awt.Rectangle;

import main.GamePanel;
import main.Utility;

public class Rock extends Object{
	
	public Rock(GamePanel gp) {
		super(gp);
		
		name = "Rock";
		
		collidable = true;
		collisionArea = new Rectangle(21 * GamePanel.scale, 23 * GamePanel.scale, 20, 20);
		
		getImage();
	}
		
	public void getImage() {
		image = Utility.setUpImage("/objects/Pietra.png");
		shadow = Utility.setUpImage("/objects/PietraO.png");
	}
}
