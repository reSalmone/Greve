package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import main.Body;
import main.GamePanel;
import main.Utility;

public class Entity extends Body{
	
	public Entity(GamePanel gp) {
		super(gp);
	}
	
	HashMap<String, ArrayList<BufferedImage>> animationMap = new HashMap<String, ArrayList<BufferedImage>>();
	//HashMap<String, Integer> currentSpriteMap = new HashMap<String, Integer>(); //in case you want to change sprite timings for each direction
	
	public int speed;
	public int currentSpeed = speed;
	
	public boolean moving = false; //change sprite only if player is currently moving
	
	public int directionAngle; //direction the player is looking at (360°)
	public String direction; // n | w | s | e direction
	
	public boolean collidableExtra = false; //if this entity can collide with specific tiles (the ones that have collideExtra set to true)
	public boolean isColliding = false; //is the entity currently colliding?
	
	int stillTime = 0;
	int mainSpriteTime = 100;
	
	public int spriteTime = 0;
	int spriteChangeRate = 100;
	
	public void draw(Graphics2D g) {
		double screenX = worldX - gp.player.worldX + gp.player.screenX;
		double screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if (Utility.isInScreen(gp, this, gp.player)) {
			
			//in case there is a simple animation
			if (animation.size() != 0) {
				image = animation.get(currentSprite);
				animation();
			}
			
			//in case the animation is in function of the direction
			if (animationMap.size() != 0) {
				image = animationMap.get(direction).get(currentSprite);
				animationMap();
			}
			
			//if it's a player draw him in jumping state
			if (this instanceof Player) {
				Player player = (Player) this;
				screenX = player.finalScreenX;
				screenY = player.finalScreenY;
			}
			
			//draws the body
			g.drawImage(image, (int) screenX - image.getWidth() + gp.tileSize, (int) screenY - image.getHeight() + gp.tileSize, null);
			
		}
	}
	
	public void update() {
		
	}
	
	void animationMap() {
		if (moving) {
			if (Utility.coolDown(spriteTime, spriteChangeRate)) {
				spriteTime = GamePanel.timeMilli;
				if (currentSprite == animationMap.get(direction).size() - 1) {
					currentSprite = 0;
				} else {
					currentSprite++;
				}
			}
		} else {
			if (Utility.coolDown(stillTime, mainSpriteTime)) {
				stillTime = GamePanel.timeMilli;
				currentSprite = 0;
			}
		}
	}
}