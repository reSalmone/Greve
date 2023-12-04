package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import entity.Player;
import main.Body;
import main.GamePanel;
import main.GameState;
import main.Utility;

public class UI {
	
	GamePanel gp;
	//Font pixel;
	
	public static ArrayList<Component> components = new ArrayList<Component>();
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		loadComponents();
		
		//pixel = loadFont("/fonts/x12y16pxMaruMonica.ttf");
	}
	
	void loadComponents() {
		new Component(0, 0, 300, 75, "resume", "resume", new Color(0, 0, 0, 200));
		new Component(0, 0, 300, 75, "fuck yourself", "fuck", new Color(0, 0, 0, 200));
		new Component(0, 0, 300, 75, "straight up die", "die", new Color(0, 0, 0, 200));
		new Component(0, 0, 300, 75, "straight up die", "die", new Color(0, 0, 0, 200));
		new Component(0, 0, 300, 75, "quit", "quit", new Color(0, 0, 0, 200));
	}
	
	Font loadFont(String fontPath) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(fontPath));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void draw(Graphics2D g) {
		if (GameState.state == GameState.play) {
			if (gp.keyH.displayInfo) {
				g.setColor(Color.black);
				g.setBackground(Color.white);
				//g.setFont(pixel);
				
				for (Body e : GamePanel.bodies) {
					if (e instanceof Player) {
						Player p = (Player) e;
						Utility.drawCenteredString(g, p.nameTag, Utility.getPlayerCenter(gp, p).x, Utility.getPlayerCenter(gp, p).y - 15);
					}
				}
				
				//the tile coordinates are a bit buggy fix ;)
				g.drawString("COORDINATES: " + (int) (gp.player.worldX + gp.tileSize / 2) / gp.tileSize + " " + (int) (gp.player.worldY + gp.tileSize / 2) / gp.tileSize, 5, 15);
				g.drawString(String.valueOf("FPS: " + gp.currentFPS), 5, 30);
				g.drawString(String.valueOf("DRAW TIME: " + gp.drawTime/1000000 + "ms"), 5, 45);
				
				g.drawString(String.valueOf("OBJECTS: " + gp.objectM.currentObjects()), 5, 75);
				g.drawString(String.valueOf("ENTITIES: " + gp.entityM.currentEntities()), 5, 90);
				
				g.drawString(String.valueOf("DIRECTION: " + gp.player.direction), 5, 105);
				g.drawString(String.valueOf("TILE: " + gp.tileM.tile[gp.tileM.getTileAt((int) (gp.player.worldX + gp.tileSize / 2) / gp.tileSize, (int) (gp.player.worldY + gp.tileSize / 2) / gp.tileSize).id].name), 5, 120);
			}
		}
		if (GameState.state == GameState.pause) {
			drawPauseScreen(g);
		}
	}
	
	public static Component getComponentAt(int x, int y) {
		for (Component c : components) {
			if (x >= c.x && x <= c.x + c.width && y >= c.y && y <= c.y + c.height) {
				return c;
			}
		}
		return null;
	}
	
	public void drawPauseScreen(Graphics2D g) {
		//obscure the game
		g.setColor(new Color(0, 0, 0, 120));
		g.fillRect(0, 0, gp.getWidth(), gp.getHeight());
		
		//pause screen
		g.setColor(new Color(0, 0, 0, 140));
		int x = (gp.screenWidth - 400) / 2;
		int y = (gp.screenHeight - 500) / 2;
		g.fillRect(x, y, 400, 500);
		
		x += 50;
		y += 50;
		for (Component c : components) {
			c.x = x;
			c.y = y;
			g.setColor(c.color);
			g.fillRect(c.x, c.y, c.width, c.height);
			g.setColor(Color.white);
			g.setFont(g.getFont().deriveFont(Font.PLAIN, 30));
			Utility.drawCenteredString(g, c.text, c.x + c.width / 2, c.y + c.height / 2 + 25);
			y += c.height + 10;
		}
	}
}
