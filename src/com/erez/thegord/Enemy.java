package com.erez.thegord;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Enemy extends Entity{

	protected int damage = 300;
	protected int value = 250;
	
	public Enemy(float x, float y, int size, Game parentGame) {
		super(x, y, 0, 2, size, parentGame);
		health = 100;
		try {
			bImage = ImageIO.read(new File("Resources/BlackWidow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	
	public boolean getRemoved() {
		if (xPos > parentGame.gBoard.width || xPos < 0 || health <= 0) {
			parentGame.money += value;
			return true;
		} 
		else if(yPos > parentGame.gBoard.height) {
			parentGame.gordHealth -= damage;
			return true;
		} else {
			return false;
		}
	}
	
	public void update() {
		xPos += xVel;
		yPos += yVel;
	}
	
	public GameSquare getSquare(int x, int y) {
			return parentGame.gBoard.board[parentGame.gBoard.getCol(x)][parentGame.gBoard.getRow(y)];
	}
		
	public void goToBlock(int x, int y) {
	   float x1 = (float)parentGame.gBoard.board[x][y].getX();
	   float y1 = (float)parentGame.gBoard.board[x][y].getY();
	   float magnitude = (float)Math.sqrt((this.getX()-x1)*(this.getX()-x1)+(this.getY()-y1)*(this.getY()-y1));
	   xVel = -((this.getX()-x1)/magnitude)*speed;
	   yVel = -((this.getY()-y1)/magnitude)*speed;
	}
	
	
	protected void handleClick() {
	}
}
