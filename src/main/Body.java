package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Body {
	
	public GamePanel gp;
	
	public Rectangle collisionArea;
	public boolean collidable = false; //if implements a collision mechanic
	public double worldX, worldY;
	
	public BufferedImage image;
	public BufferedImage shadow;
	
	public ArrayList<BufferedImage> animation = new ArrayList<BufferedImage>();
	public int currentSprite = 0;
	
	int spriteTime = 0;
	int spriteChangeRate = 1000;
	
	public Body(GamePanel gp) {
		this.gp = gp;
		
		GamePanel.bodiesToAdd.add(this);
	}
	
	public void removeBody() {
		GamePanel.bodiesToRemove.add(this);
	}
	
	public void update() {
	}
	
	public void draw(Graphics2D g) {
		double screenX = worldX - gp.player.worldX + gp.player.screenX;
		double screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if (Utility.isInScreen(gp, this, gp.player)) {
			
			//in case there is an animation
			if (animation.size() != 0) {
				image = animation.get(currentSprite);
				animation();
			}
			
			//draws the body
			g.drawImage(image, (int) screenX - image.getWidth() + gp.tileSize, (int) screenY - image.getHeight() + gp.tileSize, null);
		}
	}
	
	public void animation() {
		if (Utility.coolDown(spriteTime, spriteChangeRate)) {
			spriteTime = GamePanel.timeMilli;
			if (currentSprite == animation.size() - 1) {
				currentSprite = 0;
			} else {
				currentSprite++;
			}
		}
	}
	
	public double getWorldY () {
		return worldY;
	}
}