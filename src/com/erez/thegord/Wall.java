package com.erez.thegord;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends Building{

	public Wall(GameSquare parent) {
		super(parent);
		cost = 25;

		
		try {
			bImage = ImageIO.read(new File("Resources/building.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(){		
		super.update();
	}

	@Override
	public Building newBuilding() {
		return new Wall(parentSquare);
	}
}
