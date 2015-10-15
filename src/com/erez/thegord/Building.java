package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Building {
	
	protected int cost = 100;
	
	public int damage = 25;
	public float shotSpeed = 10;
	public double range = 200;
	public int fireRate = 50;
	public boolean lob = false;
	public boolean remove = false;
	
	//protected Image[] pictures = new Image[5];
	
	protected Image bImage;
	protected GameSquare parentSquare;
	
	public Building(GameSquare parent) {
		parentSquare = parent;
		
		try {
			bImage = ImageIO.read(new File("Resources/building.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {

	}
	
	public void upgrade() {
		System.out.println("Moo");
		damage += 1000;
	}
	
	public void delete() {
		System.out.println("Goo");
		remove = true;
	}

	public void render(Graphics2D g, int xPos, int yPos, int width, int height) {
		g.drawImage(bImage, xPos, yPos, width, height, null);
		
	}
	
	public Building newBuilding() {
		return new Building(parentSquare);
	}
}
