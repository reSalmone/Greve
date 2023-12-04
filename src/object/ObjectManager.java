package object;

import java.util.Random;

import main.Body;
import main.GamePanel;

public class ObjectManager {
	
	GamePanel gp;
	
	int treeChance = 10;
	
	public ObjectManager(GamePanel gp) {
		this.gp = gp;
		
		populateLand();
	}
	
	public void addObject(Object o, int x, int y) {
		o.worldX = x;
		o.worldY = y;
	}
	
	public void removeObject(Object o) {
		o.removeBody();
	}
	
	public void populateLand() {
		for (int i = 0; i < gp.tileM.mapX; i++) {
			for (int j = 0; j < gp.tileM.mapY; j++) {
				if (gp.tileM.tile[gp.tileM.map[i][j]].id == 1) {
					Random r = new Random();
				    if (r.nextInt(100) <= treeChance) {
				    	addObject(new Tree(gp), i * gp.tileSize, j * gp.tileSize);
				    }
				}
			}
		}
	}
	
	public int currentObjects() {
		int n = 0;
		for (Body b : GamePanel.bodies) {
			if (b instanceof Object) {
				n++;
			}
		}
		return n;
	}
}
