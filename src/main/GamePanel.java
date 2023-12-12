package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;

import entity.EntityManager;
import entity.Player;
import entity.SmallSlime;
import object.ObjectManager;
import tile.TileManager;
import ui.UI;

public class GamePanel extends JPanel implements Runnable{
	//SCREEN SETTINGS
	final int originalTileSize = 32;
	public static final int scale = 2;
	
	public final int tileSize = originalTileSize * scale;
	public final int panelTileWidth = 16;
	public final int panelTileHeight = 12;
	public final int screenWidth = panelTileWidth * tileSize;
	public final int screenHeight = panelTileHeight * tileSize;
	
	//WORLD SETTINGS
	public final int maxWorldX = 50;
	public final int maxWorldY = 50;
	public final int worldWidth = maxWorldX * tileSize;
	public final int worldHeight = maxWorldY * tileSize;
	
	public final int mapWidth;
	public final int mapHeight;
	
	//MOUSE POSITION
	public static double mouseX, mouseY;
	public int range = 100;
	
	//FPS
	int FPS = 60;
	public int currentFPS;
	public double drawTime;
	
	//game timer
	public static int timeMilli = 0;
	long lastTimeNano = 0;
	
	//accessible from anywhere
	public static ArrayList<Body> bodies = new ArrayList<Body>();
	
	//bodies to remove/add after the update
	public static ArrayList<Body> bodiesToRemove = new ArrayList<Body>();
	public static ArrayList<Body> bodiesToAdd = new ArrayList<Body>();
	
	public CollisionManager collisionM = new CollisionManager(this);
	public TileManager tileM = new TileManager(this);
	public EntityManager entityM = new EntityManager(this);
	UI ui = new UI(this);
	public KeyHandler keyH = new KeyHandler(this);
	public MouseHandler mouseH = new MouseHandler(this);
	Thread gameLoop;
	public Player player = new Player(this, keyH, entityM);
	public ObjectManager objectM = new ObjectManager(this);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(new Color(161, 217, 255));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(mouseH);
		this.addMouseMotionListener(mouseH);
		this.setFocusable(true);
		
		mapWidth = tileM.mapX * tileSize;
		mapHeight = tileM.mapY * tileSize;
		
		entityM.spawnRandom(new SmallSlime(this));
	}
	
	public void startGame() {
		GameState.state = GameState.title;
	}
	
	public void startGameLoop() {
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS; //every 16.66 ms
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		long timer = 0;
		int gameFPS = 0;
		
		while (gameLoop != null) {
			currentTime =  System.nanoTime();
			timer += currentTime - lastTime;
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			
			if (delta >= 1) {
				
				update();
				
				//calls the paintComponent method
				repaint();
				
				delta--;
				gameFPS++;
			}
			if (timer >= 1000000000) {
				currentFPS = gameFPS;
				timer = 0;
				gameFPS = 0;
			}
			
			//time passes only in the play state of the game
			if (GameState.state == GameState.play) {
				if (System.nanoTime() > lastTimeNano + 1000000) {
					lastTimeNano = System.nanoTime();
					timeMilli += 1;
				}
			}
		}
	}
	
	public void update() {
		if (GameState.state == GameState.play) {
			for (Body b : bodies) {
				b.update();
			}
		}
		if (GameState.state == GameState.pause) {
			
		}
	}
	
	public void paintComponent(Graphics graphics) {
		double timeStart = System.nanoTime();
		
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		
		//title screen
		if (GameState.state != GameState.title) {
			//draw shit only if in a game
			
			//tile draw
			tileM.draw(g);
			
			//player direction line draw
			g.setColor(new Color(255, 255, 255, 150));
	    	Utility.drawLine(g, screenWidth / 2, screenHeight / 2 + 12, mouseX, mouseY, 3, range);
			
			//update bodies lists
			bodies.removeAll(bodiesToRemove);
			bodiesToRemove.clear();
			bodies.addAll(bodiesToAdd);
			bodiesToAdd.clear();
			
			//shadow draw
			for (Body b : bodies) {

				//if there is a shadow
				if (b.shadow != null) {
					
					//show it only if it's in the screen
					if (Utility.isInScreen(this, b, player)) {
						double screenX = b.worldX - player.worldX + player.screenX;
						double screenY = b.worldY - player.worldY + player.screenY;
						
						g.drawImage(b.shadow, (int) screenX, (int) screenY + 8, null);
					}
				}
			}
			
			//body draw
			bodies.sort(Comparator.comparing(Body::getWorldY));
			for (Body b : bodies) {
				b.draw(g);
				
				//hit box draw
				if (keyH.displayHitBox) {
					if (Utility.isInScreen(this, b, player)) {
						g.setColor(new Color(255, 0, 0));
						
						double screenX = b.worldX - player.worldX + player.screenX;
						double screenY = b.worldY - player.worldY + player.screenY;
						
						g.drawRect((int) screenX + b.collisionArea.x, (int) screenY + b.collisionArea.y, b.collisionArea.width, b.collisionArea.height);
						//body center draw
						//g.drawRect((int) screenX + tileSize / 2, (int) screenY + tileSize / 2, 1, 1);
					}
				}
			}
		}
		
		
		ui.draw(g);
		
		g.dispose();
		
		drawTime = System.nanoTime() - timeStart;
	}
}
