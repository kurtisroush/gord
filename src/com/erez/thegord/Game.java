package com.erez.thegord;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import com.erez.thegord.input.Keyboard;
import com.erez.thegord.input.Mouse;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L; //by convention
	
	public static int width = 1200;
	public static int height = width/16 * 9;

	public static String title = "The Gord";
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Mouse mouse;
	
	public Building mouseBuilding;
	private boolean running;
	
	public GameBoard gBoard;
	public Panel gPanel;
	public Screen introScreen;
	
	public boolean battleMode;
	public int displayScreen = 0;
	public int money;
	public int gordHealth;
	public int gameLevel;
	public 	int enemyTick;
	
	public List<Component> components = new LinkedList<Component>();
	public List<Entity> entities = new LinkedList<Entity>();
    
	Enemy[][] levelEnemies;
	
	int pos;
	
	public Game() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
	
		frame = new JFrame();
		
		key = new Keyboard(this);
		addKeyListener(key);
		mouse = new Mouse(this);
		addMouseListener(mouse);	
		addMouseMotionListener(mouse);
		
		gBoard = new GameBoard(3*width/4, height, this);
		gPanel = new Panel(width/4, height, this);
		running = false;
		battleMode = false;
		money = 100000;
		gordHealth = 1000;
		gameLevel = 0;
		enemyTick = 0;
		pos = 0;
		introScreen = new Screen(this);
		
		 levelEnemies = new Enemy[][]{
				{new Walker(300, -5, 40, this), new Walker(550, -5, 40, this), new Walker(750, -5, 40, this)},
				{new Walker(120, -5, 0, this), new Walker(450, -5, 40, this), new Flyer(800, -5, 40, this), new Walker(450, -5, 40, this)},
				{new Walker(220, -5, 40, this), new Flyer(700, -5, 40, this), new Flyer(800, -5, 40, this), new Walker(450, -5, 40, this)},
				{new Flyer(320, -5, 40, this), new Walker(350, -5, 40, this), new Walker(650, -5, 40, this), new Walker(450, -5, 40, this)},
				{new Flyer(320, -5, 40, this), new Walker(350, -5, 40, this), new Flyer(650, -5, 40, this), new Flyer(450, -5, 40, this)},
				{new Flyer(320, -5, 40, this), new Flyer(350, -5, 40, this), new Flyer(650, -5, 40, this), new Flyer(450, -5, 40, this)}
		};
		
		for (GameSquare[] i : gBoard.board) {
			for (GameSquare j : i) {
				components.add(j);
			}
	    }
		for (GameButton button : gPanel.buttons) {
			components.add(button);
	    }
		for (GameButton button : introScreen.buttons) {
			components.add(button);
	    }
		components.add(gPanel.modeB);
	}
	
	public boolean enemyAlive() {
		for(Entity e : entities) {
			if (e instanceof Enemy) {
				return true;
			}
		}
		return false;
	}
	
	public void battleStart(){
		gBoard.path();
	}
	
	public synchronized void start() { //starts game
		createBufferStrategy(3);
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() { //stops game
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() { //The big loop of the game
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double fps = 60.0; //the fps of the update method
		final double ns = 1000000000.0 / fps;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update(); //logic 60 fps
				updates++;
				delta--;
			}
			render(); //display unlimited fps
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(title + "  |  " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void  update() {
		switch(displayScreen) {
			case 0:
				introScreen.update();
				break;
			case 1:
				if (gordHealth <= 0) {
					loss();
				}
				++enemyTick;
				gBoard.update();
				gPanel.update();	
				for(Entity e : entities) {
					e.update();
				}
				List<Entity> toRemove = new LinkedList<Entity>();

			    for(Entity e : entities) {
			    	if(e.getRemoved()) {
			    		toRemove.add(e);
			    	}
			    }
			    
			    for (Entity e: toRemove) {
			    	entities.remove(e);
			    }
			    
			    if (battleMode) {
			    	if(gameLevel > levelEnemies.length) {
				    		victory();
				    	
					} else if (enemyTick%20 == 0 && pos < levelEnemies[gameLevel - 1].length) {
						entities.add(levelEnemies[gameLevel - 1][pos]);
						enemyTick = 0;
						++pos;
			    	} else if (pos == levelEnemies[gameLevel - 1].length && !enemyAlive()) {
						battleMode = false;
						pos = 0;
					}
			    }
			    	
	    }
	}

	
	private void victory() {
		
	}
	private void loss() {
	}


	public void render() {
		BufferStrategy bs = getBufferStrategy();
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		switch(displayScreen) {
			case 0:
				introScreen.render(g);
				break;
			case 1:
				gBoard.render(g);
				gPanel.render(g);
				for(Entity e : entities) {
					e.render(g);
				}
				break;
		}
			
		//Graphics above
		g.dispose(); //destroys current graphics
		bs.show();
	}
	
	public void setMouseBuilding(Building b) {
		mouseBuilding = b;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game); //game is subclass of canvas
		game.frame.pack(); //sets size of window to that of the dimensions
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); //sets window to center of screen
		game.frame.setVisible(true);
		game.start();
	}
}
