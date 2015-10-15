package com.erez.thegord;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Flyer extends Enemy{

	public Flyer(float x, float y, int size, Game parentGame) {
		super(x, y, size, parentGame);
		
		try {
			bImage = ImageIO.read(new File("Resources/unclickableSquare.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		xVel = 0;
		yVel = speed;
	}
	

}
