package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GameSquare extends Component {
	public Image imageHovered;
	public Image imageNotHovered;
	public Image unclickableSquare;
	public boolean placeable;
	
	
	
	public Building building = null;
	
	public GameSquare(int x, int y, int w, int h, Game game, Building b, boolean p) {
		super(x, y, w, h, game);
		building = b;
		placeable = p;
		
		try {
			imageNotHovered = ImageIO.read(new File("Resources/square.png"));
			imageHovered = ImageIO.read(new File("Resources/clickedSquare.png"));
			unclickableSquare = ImageIO.read(new File("Resources/unclickableSquare.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void render(Graphics2D g) {
			if (!parentGame.battleMode) {
				if (mouseOver && placeable) {
					g.drawImage(imageHovered, xPos, yPos, width, height, null);
				} else if (!mouseOver && placeable){
					g.drawImage(imageNotHovered, xPos, yPos, width, height, null);
				}
				if (!placeable) {
					g.drawImage(unclickableSquare, xPos, yPos, width, height, null);
				}
			}
			
			if (building != null) {
				building.render(g, xPos, yPos, width, height);
			}			
	}
	
	public void update() {
		if (building != null) {
			building.update();
		}
		if(building != null && building.remove){
			building = null;
		}
	}
	
	@Override
	protected void handleOver() {
	}
	
	@Override
	protected void handleClick() {
		parentGame.gPanel.upgradeBuilding = null;
		if (building == null && parentGame.mouseBuilding != null && placeable && !parentGame.battleMode && parentGame.money >= parentGame.mouseBuilding.cost ) {
			building = parentGame.mouseBuilding.newBuilding();
			building.parentSquare = this;
			if (parentGame.gBoard.isDefenseBlocking()) {
			  building = null;
			} else {
			  parentGame.money -= building.cost;
			}
		} else if(building != null) {
			parentGame.gPanel.upgradeBuilding = building;
		}
		
	}
}
