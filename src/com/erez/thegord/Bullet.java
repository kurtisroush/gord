package com.erez.thegord;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Bullet extends Entity{
	
	int damage = 20;
	int piercing = 1;
	boolean lob = false;
	public LinkedList<Entity> hitEnemies = new LinkedList<Entity>();
	

	public Bullet(float x, float y, float angle, float shotSpeed, int size, Game parentGame, int damage, int piercing, boolean lob) {
		super(x, y, angle, shotSpeed, size, parentGame);
		this.damage = damage;
		this.piercing = piercing;
		this.lob = lob;
		
		try {
			bImage = ImageIO.read(new File("Resources/bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getRemoved() {
		if (xPos > parentGame.gBoard.width || xPos < 0 || yPos > parentGame.gBoard.height || yPos < 0 || piercing <= 0) {
			return true;
		} else if (!lob && (getSquare((int)xPos, (int)yPos).building != null)) {
	      return true;
		} else {
			return false;
		}
	}
	
	public void update() {
	    checkAllEncounters();
		xPos += xVel;
		yPos += yVel;
		
	}
	
  public boolean checkEncounter(Entity enemy) {
      return (size/2+enemy.size/2)*(size/2+enemy.size/2) >= (xPos - enemy.xPos)*(xPos - enemy.xPos)+(yPos - enemy.yPos)*(yPos - enemy.yPos);
  }
  
   public void checkAllEncounters() {
        for (Entity e : parentGame.entities) {
	        	if (e instanceof Enemy && checkEncounter(e) && hitEnemies.indexOf(e) == -1) {
	        		hitEnemies.offer(e);
	        		e.health -= damage;
	            	--piercing; 
	        	}
        }

    }
	    

	
}
