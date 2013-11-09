package com.gb.turnz.base;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.input.KeyHandler;
import com.gb.turnz.input.MouseHandler;
import com.gb.turnz.level.World;
import com.gb.turnz.menu.Menu;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static Game instance;
	private static Logger logger;
	private static MouseHandler mouse;
	private static KeyHandler keyboard;
	private static Random random;

	private static Thread mainThread;
	private static boolean running = false;

	private static World world;
	private static Menu menu;

	public void init() {
		instance = this;
		
		requestFocusInWindow();
		random = new Random();
		logger = Logger.getLogger(Game.class.getName());
		logger.setLevel(Level.ALL);
		mouse = new MouseHandler();
		keyboard = new KeyHandler();
		
		world = new World();
		
		menu = new Menu(this);
		menu.addObject(new Menu.Button(100, 100, "Play") {
			public void onClick() {
				System.out.println("Hello!");
			}
		});
		ImageManager.addImage("/textures/turnzMenu.png", "menuImage");
	}

	public void tick() {
		mouse.poll();
		keyboard.poll();
		
		if(keyboard.isKeyDownOnce(KeyEvent.VK_RIGHT))
			world.rotateRight();
		if(keyboard.isKeyDownOnce(KeyEvent.VK_LEFT))
			world.rotateLeft();
		menu.tick();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Screen.clear(0x0, 255);
		
		world.render();
		menu.render();
		Screen.renderScaled(ImageManager.getImage("menuImage"), 0, 0, 1);
		
		Screen.finalizeLighting();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(Screen.getImage(), 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public void run() {

		init();

		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000.0 / 60.0;
		double unprocessed = 0.0;
		long now;
		int ticks = 0, frames = 0;
		long lastTimer = System.currentTimeMillis();
		boolean shouldRender = false;

		while (running) {
			now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			shouldRender = false;

			while (unprocessed >= 1) {
				tick();
				ticks++;
				shouldRender = true;
				unprocessed--;
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				logger.log(Level.INFO, ticks + " ticks, " + frames + " frames");
				ticks = frames = 0;
			}
		}

	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		mainThread = new Thread(this, "MAIN_game");
		mainThread.start();
	}

	public synchronized void stop() {
		running = false;
	}

	public static Game getInstance() {
		return instance;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static MouseHandler getMouse() {
		return mouse;
	}

	public static KeyHandler getKeyboard() {
		return keyboard;
	}
	
	public static World getWorld() {
		return world;
	}
	
	public static Random getRandom() {
		return random;
	}
}
