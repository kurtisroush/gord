package com.erez.thegord;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sniper extends Building{
	
	private int tick = 0;
	private float angle = (float) (Math.PI/2);
	private float shotSpeed = 20;
	private int damage = 100;
	private double range = 600;
	private int fireRate = 250;
	protected Entity targetEntity;

	public Sniper(GameSquare parent) {
		super(parent);
		cost = 250;
		try {
			bImage = ImageIO.read(new File("Resources/Ammo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(){
		
		tick++;
		rotate();
		if(tick%fireRate==0 && targetEntity != null) {
			fireBullet();
			tick = 1;
		}
	}
	
	public void render(Graphics2D g, int xPos, int yPos, int width, int height) {
		AffineTransform at = AffineTransform.getScaleInstance((double) width / bImage.getWidth(null),
                (double) height / bImage.getHeight(null));
		at.translate((xPos)/at.getScaleX(), (yPos)/at.getScaleY());
		if (parentSquare.parentGame.battleMode) {
			at.rotate(angle - Math.PI/2, (width/2)/at.getScaleX(), (height/2)/at.getScaleY());
		}
		g.drawImage(bImage, at, null);
		
	}
	
	public void rotate(){
	   angle = getRotation();

	}
	
	public float getRotation(){
		targetEntity = findNearestEnemy();
		if(targetEntity != null){
			double dx = parentSquare.xPos - targetEntity.getX();
			double dy = parentSquare.yPos - targetEntity.getY();
		
			return (float) Math.atan2(dy, dx);
		}
		return (float) (Math.PI/2);
	}
	
	public Entity findNearestEnemy(){
		Entity e1 = null;
		double closestDistance = range;
		double currentDistance;
		for(Entity e: parentSquare.parentGame.entities) {
			if (e instanceof Enemy) {
			   currentDistance = Math.sqrt((parentSquare.xPos - e.getX())*(parentSquare.xPos - e.getX())+(parentSquare.yPos - e.getY())*(parentSquare.yPos - e.getY()));
			   if(currentDistance < closestDistance){
				   e1 = e;
				   closestDistance = currentDistance;
			   }
			}
		}
		return e1;
	}
	
	public void fireBullet(){
		if (parentSquare != null) {
			parentSquare.parentGame.entities.add(new Bullet((float) (parentSquare.xPos + parentSquare.width/2 - parentSquare.width*Math.cos(angle)), 
			(float) (parentSquare.yPos + parentSquare.height/2 - parentSquare.width*Math.sin(angle)), (float)angle, (float) -shotSpeed, 20, parentSquare.parentGame, damage, 1, lob));
		}
	}
	
	@Override
	public Building newBuilding() {
		return new Sniper(parentSquare);
	}
}
