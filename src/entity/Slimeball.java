package entity;

import java.awt.Rectangle;

import main.GamePanel;
import main.Utility;

public class Slimeball extends Projectile {
	
	int checkTime = GamePanel.timeMilli;
	
	public Slimeball(GamePanel gp, Entity thrower) {
		super(gp, thrower);
		
		speed = 10;
		currentSpeed = speed;
		collidable = true;
		
		//this adds player's speed to the projectile's speed
		//if (thrower != null) {
		//	speed += thrower.currentSpeed;
		//}*/
		
		collisionArea = new Rectangle(11 * GamePanel.scale, 13 * GamePanel.scale, 19, 19);
		
		getImage();
	}
	
	public void getImage() {
		image = Utility.setUpImage("/entities/SlimeBall.png");
		shadow = Utility.setUpImage("/entities/SlimeballO.png");
	}
	
	public void update() {
		if (Utility.coolDown(checkTime, 500)) {
			checkTime = GamePanel.timeMilli;
			removeBody();
		}
		
		double newPosX = worldX + currentSpeed * Math.cos(Math.toRadians(directionAngle));
		double newPosY = worldY - currentSpeed * Math.sin(Math.toRadians(directionAngle));
		
		if (checkCollisions(newPosX, newPosY)) {
			removeBody();
		}
	}
}	
