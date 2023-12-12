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
		
		//you have to make the other direction animations
		animationMap.put("ne", up);
		animationMap.put("nw", up);
		animationMap.put("se", up);
		animationMap.put("sw", up);
		
		shadow = Utility.setUpImage("/entities/SmallSlimeO.png");
	}
	
	public void update() {
		//random direction every 3s, 50% of the times
		if (Utility.coolDown(checkTime, 3000)) {
			checkTime = GamePanel.timeMilli;
			if (Utility.chance(50)) {
				moving = true;
				directionAngle = (int) Utility.randomNumber(360);
				setDirection();
			} else {
				moving = false;
			}
		}
		
		//stops after a second and chills for 2
		if (Utility.coolDown(checkTime, 1000)) {
			moving = false;
		}
		
		//for some reason it lags when the player moves
		//move
		if (moving) {
			double newPosX = worldX + currentSpeed * Math.cos(Math.toRadians(directionAngle));
			double newPosY = worldY - currentSpeed * Math.sin(Math.toRadians(directionAngle));
			
			checkCollisions(newPosX, newPosY);
		}
	}	

}
