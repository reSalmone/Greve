package tiles;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Scanner;

import main.GamePanel;
import main.Utility;

public class TileManager {
	
	//merge all the tiles in a map to load just 1 image in the draw
	
	GamePanel gp;
	public Tile[] tile;
	public int map[][];
	public int mapX;
	public int mapY;
	
	public TileManager(GamePanel gp) {
		this.gp = gp;	
		tile = new Tile[10];
		map = new int[gp.maxWorldX][gp.maxWorldY];
		
		loadMap("/maps/map1.txt");
		getImage();
	}
	
	void loadMap(String mapPath) {
		try {
			Scanner scanner = new Scanner(getClass().getResourceAsStream(mapPath));
			int col = 0;
			int row = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				for (String s : line.split(" ")) {
					map[row][col] = Integer.valueOf(s);
					row++;
				}
				mapX = row;
				row = 0;
				col++;
			}
			mapY = col;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	void getImage() {
		setUpImage(0, "Void.png", false, true);
		setUpImage(1, "Grass.png", false, false);
		setUpImage(2, "Sand.png", false, false);
		setUpImage(3, "Water.png", false, true);
		setUpImage(4, "Wall.png", true, false);
	}
	
	void setUpImage(int index, String imageName, boolean collidableTile, boolean collidableTileExtra) {
		tile[index] = new Tile();
		tile[index].image = Utility.setUpImage("/tiles/" + imageName);
		tile[index].id = index;
		tile[index].collidableTileExtra = collidableTileExtra;
		tile[index].collidableTile = collidableTile;
		tile[index].name = imageName.split("\\.")[0];
		tile[index].collisionArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
	}
	
	public Tile getTileAt(int worldX, int worldY) {
		return tile[map[worldX][worldY]];
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < gp.maxWorldX; i++) {
			for (int j = 0; j < gp.maxWorldY; j++) {
				//position of tile in the world
				int worldX = i * gp.tileSize;
				int worldY = j * gp.tileSize;
				
				//where the tile needs to be positioned in the screen, so:
				//position of tile in world - position of player in world + the offset of the player's screen
				//because the player is in the center of the screen and not at the top left
				double screenX = worldX - gp.player.worldX + gp.player.screenX;
				double screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				//draw the tile only if it's in the player's screen, so
				//only if the tile's position +/- 1 tile (to avoid making it tile perfect which would)
				//make the border black if it's < 1 tile wide) is >/< of the player -/+ the screen offset
				if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
					
					//check if the tile is not void
					if (tile[map[i][j]].image != null) {
						tile[map[i][j]].collisionArea = new Rectangle(i * gp.tileSize, j * gp.tileSize, gp.tileSize, gp.tileSize);
						g.drawImage(tile[map[i][j]].image, (int) screenX, (int) screenY, null);
					}
				}
			}
		}
	}
}
