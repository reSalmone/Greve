package ui;

import java.awt.Color;

	public class Component {
	public int x, y, width, height;
	public Color color;
	public String text;
	public String name;
	public String state;
	
	public Component(int x, int y, int width, int height, String text, String name, Color color, String state) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.text = text;
		this.color = color;
		this.state = state;
		UI.components.add(this);
	}
}
