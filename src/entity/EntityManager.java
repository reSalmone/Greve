package entity;

import java.util.Random;

import main.Body;
import main.GamePanel;

public class EntityManager {
	
	GamePanel gp;
	
	public EntityManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void spawnEntity(Entity entity, double x, double y) {
		entity.worldX = x;
		entity.worldY = y;
	}
	
	public int currentEntities() {
		int n = 0;
		for (Body e : GamePanel.bodies) {
			if (e instanceof Entity) {
				n++;
			}
		}
		return n;
	}
	
	public void spawnRandom(Entity entity) {
		boolean spawned = false;
		Random r = new Random();
		while (!spawned) {
			entity.worldX = r.nextInt(gp.mapWidth);
			entity.worldY = r.nextInt(gp.mapHeight);
			entity.isColliding = false;
			gp.collisionM.checkTile(entity, entity.worldX, entity.worldY);
			gp.collisionM.checkCollision(entity, entity.worldX, entity.worldY);
			if (!entity.isColliding) {
				spawned = true;
			}
		}
	}
}
