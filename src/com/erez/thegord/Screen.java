package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;



public class Screen {
	
	ArrayList<GameButton> buttons = new ArrayList<GameButton>();
	
	private Image display = null;
	private Image title = null;

	@SuppressWarnings("unused")
	private Game parentGame;
	
	public Screen(Game parentGame) {
		
		this.parentGame = parentGame;
		
		try {
			display = ImageIO.read(new File("Resources/panel.png"));
			title = ImageIO.read(new File("Resources/TheGord.png"));
			buttons.add(new StartButton(3*Game.width/8, Game.height/2, Game.width/4, Game.height/4, parentGame));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		for (GameButton button : buttons) {
	        button.update();
	    }
	}
	
	public void render(Graphics2D g) {
		g.drawImage(display, 0, 0, Game.width, Game.height, null);
		g.drawImage(title, Game.width/4, Game.height/8, Game.width/2, Game.height/4, null);
		
		for (GameButton button : buttons) {
	        button.render(g);
	    }
	}
}
