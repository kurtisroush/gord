package com.erez.thegord;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;



public class Panel {

	public final int width;
	public final int height;
	public final int xPos;
	public final int yPos;
	
	public ArrayList<GameButton> buttons = new ArrayList<GameButton>();
	public ArrayList<BuildingButton> buildingButtons = new ArrayList<BuildingButton>();
	public ModeButton modeB;
	public Building upgradeBuilding = null;
	
	public UpgradeButton uButton;
	public SellButton sButton;
	private Image panel = null;

	private Game parentGame;
	
	public Panel(int width, int height, Game parentGame) {
		
		this.width = width;
		this.height = height;
		this.parentGame = parentGame;
		xPos = Game.width - width;
		yPos = 0;
		
		try {
			panel = ImageIO.read(new File("Resources/panel.png"));
			
			BuildingButton regB = new BuildingButton(xPos + width/8, height/2, 50, 50, parentGame, new Sniper(null));
			BuildingButton ammoB = new BuildingButton(xPos + width/8, height/2 + 100, 50, 50, parentGame, new Wall(null));
			BuildingButton turret = new BuildingButton(xPos + width/8 + 100, height/2, 50, 50, parentGame, new Turret(null));
			
			modeB = new ModeButton(xPos + width/8, 3*height/4, 200, 100, parentGame);

			buildingButtons.add(regB);
			buildingButtons.add(ammoB);
			buildingButtons.add(turret);
			buttons.add(regB);
			buttons.add(ammoB);
			buttons.add(turret);
			uButton= new UpgradeButton(xPos + width/8, height/2, 50, 50, parentGame, upgradeBuilding);
			sButton = new SellButton(xPos + width/8 + 100, height/2, 50, 50, parentGame, upgradeBuilding);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		modeB.update();
		if (!parentGame.battleMode && upgradeBuilding == null) {
			for (GameButton button : buttons) {
		        button.update();
		    }
		} else if (upgradeBuilding != null){
	        uButton.update();
	        sButton.update();
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(panel, xPos, yPos, width, height, null);
		Font moneyFont = new Font("Helvetica", Font.PLAIN, 30);
		g.setFont(moneyFont);
		g.drawString("Coins: " + parentGame.money, xPos, yPos + 30);
		g.drawString("Health: " + parentGame.gordHealth, xPos, yPos + 70);
		
		modeB.render(g);
		if (!parentGame.battleMode && upgradeBuilding == null) {
			for (GameButton button : buttons) {
				button.render(g);
	    	}
		} else if (upgradeBuilding != null){
		        uButton.render(g);
		        sButton.render(g);
		}
	}
	
	public void resetBuildingButtons() {
		for (BuildingButton bButton : buildingButtons) {
	        bButton.setPressed(false);
	    }
	}
	
	public void displayHealth() {
		
		
	}
	
	public void displayCoins() {
		
		
	}
	
	public void displayBuildings() {
		
	}
}
