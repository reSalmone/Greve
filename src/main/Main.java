package main;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
			JFrame finestra = new JFrame();			
			
			finestra.setResizable(false);
			finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			finestra.setTitle("dajeroma");
			
			GamePanel panel = new GamePanel();
			finestra.add(panel);
			
			panel.startGameLoop();
			panel.startGame();
			
			finestra.pack();
			finestra.setLocationRelativeTo(null);
			finestra.setVisible(true);
	}
	
	//TODO LIST
	//FIRST
	
	//tile renewal
	//infinite world
	//experience bar
	//entity interaction
	//entity real AI
	//dialogue system
	//npc: big slime (goth mommy slime) spawning next to you telling you what the fuck is happening
	//add z axis
	//slime melt to climb walls and recreate on top
	
	//LAST
}
