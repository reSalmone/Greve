package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import main.Utility;

public class Player extends Entity{
	
	KeyHandler keyH;
	EntityManager entityM;
	
	//actual player screen position
	public final double screenX;
	public final double screenY;
	
	//player image position (in case of jump)
	public double finalScreenX;
	public double finalScreenY;
	public boolean jumping = false;
	
	int shiftSpeed;
	
	int lastShot = 0; //store the current time for the jump cool down
	
	public String nameTag; //displayed on top of the entity
	
	public Player(GamePanel gp, KeyHandler keyH, EntityManager entityM) {
		super(gp);
		this.keyH = keyH;
		this.entityM = entityM;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		finalScreenX = screenX;
		finalScreenY = screenY;
		
		collisionArea = new Rectangle(8 * GamePanel.scale, 11 * GamePanel.scale, 17 * GamePanel.scale, 13 * GamePanel.scale);
		int startPosX = 5;
		int startPosy = 2;
		
		worldX = gp.tileSize * startPosX;
		worldY = gp.tileSize * startPosy;
		speed = 2;
		shiftSpeed = 4;
		direction = "n";
		collidable = true;
		collidableExtra = true;
		nameTag = "maria";
		
		getImage();
	}
	
	public void getImage() {
		ArrayList<BufferedImage> n = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> s = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> w = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> e = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> ne = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> nw = new ArrayList<BufferedImage>();
		
		n.add(Utility.setUpImage("/player/SlimeU1.png"));
		n.add(Utility.setUpImage("/player/SlimeU2.png"));
		s.add(Utility.setUpImage("/player/SlimeD1.png"));
		s.add(Utility.setUpImage("/player/SlimeD2.png"));
		w.add(Utility.setUpImage("/player/SlimeL1.png"));
		w.add(Utility.setUpImage("/player/SlimeL2.png"));
		e.add(Utility.setUpImage("/player/SlimeR1.png"));
		e.add(Utility.setUpImage("/player/SlimeR2.png"));
		nw.add(Utility.setUpImage("/player/SlimeNW1.png"));
		nw.add(Utility.setUpImage("/player/SlimeNW2.png"));
		ne.add(Utility.setUpImage("/player/SlimeNE1.png"));
		ne.add(Utility.setUpImage("/player/SlimeNE2.png"));
		
		
		animationMap.put("n", n);
		animationMap.put("s", s);
		animationMap.put("w", w);
		animationMap.put("e", e);
		
		animationMap.put("ne", ne);
		animationMap.put("nw", nw);
		animationMap.put("sw", w);
		animationMap.put("se", e);
		
		shadow = Utility.setUpImage("/player/SlimeO.png");
	}
	
	public void update() {
		
		if (jumping) {
			if (finalScreenY > screenY - 30) {
				finalScreenY -= 3;
			} else {
				jumping = false;
			}
		}
		
		if (!jumping && finalScreenY < screenY) {
			//if (keyH.jump) {
			//	finalScreenY += 0.5;
			//} else {
				finalScreenY += 1.5;
			//}
			
		}
		
		//key: space
		if (keyH.shoot && Utility.coolDown(lastShot, 500)) {
			Slimeball slimeB = new Slimeball(gp, this);
			slimeB.directionAngle = directionAngle;
			slimeB.thrower = this;
			entityM.spawnEntity(slimeB, worldX + finalScreenX - screenX - 1, worldY + finalScreenY - screenY - 1);
			
			lastShot = GamePanel.timeMilli;
		}
		
		if (keyH.jump && finalScreenY >= screenY) {
			jumping = true;
		}
		
		//key: w | s | a | d
		if (keyH.up || keyH.down || keyH.left || keyH.right) {
			
			//start the player's moving animation
			moving = true;
		
			//if player is moving set it's current speed
			currentSpeed = speed;
			
			if (keyH.shift) {
				currentSpeed = shiftSpeed;
			} else {
				//if player is not shifting set back to normal speed
				currentSpeed = speed;
			}
			
			//you have to make this a bit smaller: make a function that checks the collision
			if (keyH.up) {
				double newPosX = worldX + currentSpeed * Math.cos(Math.toRadians(directionAngle));
				double newPosY = worldY - currentSpeed * Math.sin(Math.toRadians(directionAngle));
				
				isColliding = false;
				gp.collisionM.checkTile(this, newPosX, worldY);
				gp.collisionM.checkCollision(this, newPosX, worldY);
				if (!isColliding) { worldX = newPosX; }
				
				isColliding = false;
				gp.collisionM.checkTile(this, worldX, newPosY);
				gp.collisionM.checkCollision(this, worldX, newPosY);
				if (!isColliding) { worldY = newPosY; }
			}
			if (keyH.down) {
				double newPosX = worldX + currentSpeed * Math.cos(Math.toRadians(directionAngle + 180));
				double newPosY = worldY - currentSpeed * Math.sin(Math.toRadians(directionAngle + 180));
				
				isColliding = false;
				gp.collisionM.checkTile(this, newPosX, worldY);
				gp.collisionM.checkCollision(this, newPosX, worldY);
				if (!isColliding) { worldX = newPosX; }
				
				isColliding = false;
				gp.collisionM.checkTile(this, worldX, newPosY);
				gp.collisionM.checkCollision(this, worldX, newPosY);
				if (!isColliding) { worldY = newPosY; }
			}
			if (keyH.left) {
				double newPosX = worldX + currentSpeed * Math.cos(Math.toRadians(directionAngle + 90));
				double newPosY = worldY - currentSpeed * Math.sin(Math.toRadians(directionAngle + 90));
				
				isColliding = false;
				gp.collisionM.checkTile(this, newPosX, worldY);
				gp.collisionM.checkCollision(this, newPosX, worldY);
				if (!isColliding) { worldX = newPosX; }
				
				isColliding = false;
				gp.collisionM.checkTile(this, worldX, newPosY);
				gp.collisionM.checkCollision(this, worldX, newPosY);
				if (!isColliding) { worldY = newPosY; }
			}
			if (keyH.right) {
				double newPosX = worldX + currentSpeed * Math.cos(Math.toRadians(directionAngle - 90));
				double newPosY = worldY - currentSpeed * Math.sin(Math.toRadians(directionAngle - 90));
				
				isColliding = false;
				gp.collisionM.checkTile(this, newPosX, worldY);
				gp.collisionM.checkCollision(this, newPosX, worldY);
				if (!isColliding) { worldX = newPosX; }
				
				isColliding = false;
				gp.collisionM.checkTile(this, worldX, newPosY);
				gp.collisionM.checkCollision(this, worldX, newPosY);
				if (!isColliding) { worldY = newPosY; }
			}
		} else {
			moving = false;
			currentSpeed = 0;
		}
	}
}
