package com.erez.thegord;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UpgradeButton extends GameButton{
	
	public Building uBuilding = null; 

	public UpgradeButton(int x, int y, int w, int h, Game game, Building b) {
		super(x, y, w, h, game);
		
		try {
			imageNotClicked = ImageIO.read(new File("Resources/Ammo.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		uBuilding = b;
	}
	
	@Override
	protected void handleClick() {
		System.out.println("upgraded");
		if(uBuilding.cost <= parentGame.money) {
			uBuilding.upgrade();
			parentGame.money -= uBuilding.cost;
		}
	}

}
