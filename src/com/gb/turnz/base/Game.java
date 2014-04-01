package com.gb.turnz.base;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import com.gb.turnz.util.Logger;
import com.gb.turnz.level.Level;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.input.KeyHandler;
import com.gb.turnz.input.MouseHandler;
import com.gb.turnz.menu.MainMenu;
import com.gb.turnz.menu.Menu;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static Game instance;
	private static Logger logger;
	private static MouseHandler mouse;
	private static KeyHandler keyboard;
	private static Random random;

	private static boolean sfx = false, music = false;

	private static int tickCount = 0;

	private static Thread mainThread;
	private static boolean running = false;

	private static Level level;
	private static Menu menu;

	public void init() {
		instance = this;
		
		level = new Level();

		requestFocusInWindow();
		random = new Random();
		logger = new Logger();
		logger.setLevel(Logger.WARNING);
		
		mouse = new MouseHandler();
		keyboard = new KeyHandler();
		logger.log("Hello, " + System.getProperty("user.name"));
		menu = new MainMenu(this);
	}

	public void tick() {
		tickCount++;
		mouse.poll();
		keyboard.poll();

		menu.tick();
		mouse.poll();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Screen.clear(0x0, 255);

		menu.render();

//		Screen.finalizeLighting();
//		ImageManager.render("pickleMouse", mouse.x() - 4, mouse.y() - 4, 0);
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
				logger.log(ticks + " ticks, " + frames + " frames");
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

	public static Level getLevel() {
		return level;
	}

	public static Random getRandom() {
		return random;
	}

	public static int getTickCount() {
		return tickCount;
	}

	public static void setMenu(Menu menu) {
		Game.menu = menu;
		menu.switchedTo();
	}
	
	public static void setLevel(Level l) {
		Game.level = l;
	}

	public static boolean sfxOn() {
		return sfx;
	}

	public static boolean musicOn() {
		return music;
	}

	public static void setMusic(boolean music) {
		Game.music = music;
	}

	public static void setSFX(boolean sfx) {
		Game.sfx = sfx;
	}

	public static String getApplicationDataFolder() {
		String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN"))
			return System.getenv("APPDATA");
		else if (OS.contains("MAC"))
			return System.getProperty("user.home") + "/Library/Application " + "Support";
		else if (OS.contains("NUX"))
			return System.getProperty("user.home");
		return System.getProperty("user.dir");
	}
}
