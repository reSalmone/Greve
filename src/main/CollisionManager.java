package main;

import java.awt.Rectangle;
import java.util.ArrayList;

import entity.Entity;
import entity.Projectile;
import tiles.Tile;

public class CollisionManager {
	GamePanel gp;
	
	public CollisionManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity, double newWorldX, double newWorldY) {
		if (entity.collidable) {
			//get the corners of the player's collision area
			int tileLX = (int) (newWorldX + entity.collisionArea.x) / gp.tileSize;
			int tileRX = (int) (newWorldX + entity.collisionArea.x + entity.collisionArea.width) / gp.tileSize;
			int tileTY = (int) (newWorldY + entity.collisionArea.y) / gp.tileSize;
			int tileBY = (int) (newWorldY + entity.collisionArea.y + entity.collisionArea.height) / gp.tileSize;
			
			//get the tiles at these corners
			ArrayList<Tile> tiles = new ArrayList<Tile>();
			tiles.add(gp.tileM.tile[gp.tileM.getTileAt(tileLX, tileTY).id]); //top left
			tiles.add(gp.tileM.tile[gp.tileM.getTileAt(tileRX, tileTY).id]); //top right
			tiles.add(gp.tileM.tile[gp.tileM.getTileAt(tileLX, tileBY).id]); //bottom left
			tiles.add(gp.tileM.tile[gp.tileM.getTileAt(tileRX, tileBY).id]); // bottom right
			
			//new area for entity
			Rectangle entityArea = new Rectangle((int) (newWorldX + entity.collisionArea.x), 
					(int) (newWorldY + entity.collisionArea.y), entity.collisionArea.width, entity.collisionArea.height);
			
			for (Tile t : tiles) {
				//new area for tiles
				t.collisionArea = new Rectangle(tileLX * gp.tileSize, tileTY * gp.tileSize, t.collisionArea.width, t.collisionArea.height);
				if (t.collidableTileExtra && entityArea.intersects(t.collisionArea) && entity.collidableExtra) {
					entity.isColliding = true;
				}
				if (t.collidableTile && entityArea.intersects(t.collisionArea)) {
					entity.isColliding = true;
				}
			}
		}
	}
	
	public void checkCollision(Entity entity, double newWorldX, double newWorldY) {
		if (entity.collidable) {
			for (Body body : GamePanel.bodies) {
				
				//the entity can't hit the projectile and reverse
				Body exclusion = null;
				if (entity instanceof Projectile) {
					exclusion = ((Projectile) entity).thrower;
				}
				if (body instanceof Projectile) {
					if (((Projectile) body).thrower == entity) {
						exclusion = body;
					}
				}
				
				//entity's can't collide with itself
				if (body != entity && body != exclusion && body.collidable && body.collisionArea != null) {
					
					//new area for body
					Rectangle bodyArea = new Rectangle((int) (body.worldX + body.collisionArea.x), 
							(int) (body.worldY + body.collisionArea.y), body.collisionArea.width, body.collisionArea.height);
					//new area for entity
					Rectangle entityArea = new Rectangle((int) (newWorldX + entity.collisionArea.x), 
							(int) (newWorldY + entity.collisionArea.y), entity.collisionArea.width, entity.collisionArea.height);
					
					if (entityArea.intersects(bodyArea)) {
						entity.isColliding = true;
					}
				}	
			}
		}
	}
}
