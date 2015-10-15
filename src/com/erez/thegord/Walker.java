package com.erez.thegord;

import java.util.LinkedList;

public class Walker extends Enemy{
	
	LinkedList<Pair<Integer, Integer>> ways = new LinkedList<Pair<Integer, Integer>>();
	private int point = 0;

	public Walker(float x, float y, int size, Game parentGame) {
		super(x, y, size, parentGame);
		
		ways = parentGame.gBoard.waypoints;
	}
	
	public void followPath() {
		if (point < ways.size()) {
			goToBlock(ways.get(point).getSecond(), ways.get(point).getFirst());
			if(parentGame.gBoard.getCol((int) xPos + parentGame.gBoard.squareWidth/2) == ways.get(point).getSecond() && parentGame.gBoard.getRow((int) yPos + parentGame.gBoard.squareHeight/2) == ways.get(point).getFirst()) {
					++point;
			}
		} else {
			xVel = 0;
			yVel = speed;
		}

	}
	
	public void update() {
		followPath();
		xPos += xVel;
		yPos += yVel;
	}

}
