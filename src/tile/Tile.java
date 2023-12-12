package tile;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Tile {
	public int id;
	public String name;
	
	public BufferedImage image;
	public BufferedImage belowImage;
	public boolean collidableTile = false;
	public boolean collidableTileExtra = false;
	
	public Rectangle collisionArea;
}
