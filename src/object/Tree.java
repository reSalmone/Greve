package object;

import java.awt.Rectangle;

import main.GamePanel;
import main.Utility;

public class Tree extends Object{
	
	public Tree(GamePanel gp) {
		super(gp);
		
		name = "Tree";
		
		collidable = true;
		collisionArea = new Rectangle(11 * GamePanel.scale, 14 * GamePanel.scale, 9 * GamePanel.scale, 6 * GamePanel.scale);
		
		getImage();
	}
		
	public void getImage() {
		image = Utility.setUpImage("/objects/Albero.png");
		shadow = Utility.setUpImage("/objects/AlberoO.png");
	}
}
