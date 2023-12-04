package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.Utility;

public class SmallSlime extends Entity {
	
	int checkTime = GamePanel.timeMilli;

	public SmallSlime(GamePanel gp) {
		super(gp);
		speed = 1;
		currentSpeed = speed;
		
		moving = false;
		direction = "n";
		directionAngle = 0;
		collidable = true;
		collidableExtra = true;
		
		collisionArea = new Rectangle(11 * GamePanel.scale, 15 * GamePanel.scale, 15, 15);
		
		getImage();
	}
	
	public void getImage() {
		ArrayList<BufferedImage> up = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> down = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> left = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> right = new ArrayList<BufferedImage>();
		
		up.add(Utility.setUpImage("/entities/SmallSlimeU1.png"));
		down.add(Utility.setUpImage("/entities/SmallSlimeD1.png"));
		left.add(Utility.setUpImage("/entities/SmallSlimeL1.png"));
		right.add(Utility.setUpImage("/entities/SmallSlimeR1.png"));
		
		animationMap.put("n", up);
		animationMap.put("s", down);
		animationMap.put("w", left);
		animationMap.put("e", right);
		
		animationMap.put("ne", up);
		animationMap.put("nw", up);
		animationMap.put("se", up);
		animationMap.put("sw", up);
		
		shadow = Utility.setUpImage("/entities/SmallSlimeO.png");
	}
	
	public void update() {
		if (Utility.coolDown(checkTime, 3000)) {
			checkTime = GamePanel.timeMilli;
			if (Utility.chance(50)) {
				moving = true;
				double r = Utility.randomNumber(100);
				if (r <= 25) {
					direction = "n";
					directionAngle = 90;
				}
				if (r > 25 && r <= 50) {
					direction = "s";
					directionAngle = 270;
				}
				if (r > 50 && r <= 75) {
					directionAngle = 180;
				}
				if (r > 75 && r <= 100) {
					directionAngle = 0;
				}
			} else {
				moving = false;
			}
		}
		if (Utility.coolDown(checkTime, 1000)) {
			moving = false;
		}
		
		if (moving) {
			double newPosX = 0;
			double newPosY = 0;
			if (direction == "n") {
				newPosX = worldY - currentSpeed;
			}
			if (direction == "s") {
				newPosX = worldY + currentSpeed;
			}
			if (direction == "w") {
				newPosX = worldX - currentSpeed;
			}
			if (direction == "e") {
				newPosX = worldX + currentSpeed;
			}
			isColliding = false;
			gp.collisionM.checkTile(this, newPosX, worldY);
			gp.collisionM.checkCollision(this, newPosX, worldY);
			if (!isColliding) { worldX = newPosX; }
			
			isColliding = false;
			gp.collisionM.checkTile(this, worldX, newPosY);
			gp.collisionM.checkCollision(this, worldX, newPosY);
			if (!isColliding) { worldY = newPosY; }
		}
	}	

}
