package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

	public class Component {
	public int x, y, width, height;
	public Color color;
	public String text;
	public String name;
	
	public Component(int x, int y, int width, int height, String text, String name, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.text = text;
		this.color = color;
		UI.components.add(this);
	}
}
