package com.gb.turnz.base;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gb.turnz.graphics.Light;
import com.gb.turnz.graphics.Screen;

public class BaseGame extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private int ticksPerSecond = 60;
	private boolean lockedFps = false;
	
	private Thread gameThread;
	private boolean running = false;
	
	private static Logger logger;
	
	public void init() {
		logger = Logger.getLogger(BaseGame.class.getName());
		logger.setLevel(Level.ALL);
		
		Screen.initialize();
		Screen.setProperty(Screen.LIGHTING, Screen.TRUE);
		Screen.addLight(new Light(150, 150, 100, 255));
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		gameThread = new Thread(this, "MAIN_Game");
		gameThread.start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		init();
		
		long lt = System.nanoTime();
		double nsPt = 1000000000.0/ticksPerSecond;
		double delta = 0.0;
		long ltr = System.currentTimeMillis();
		long now;
		boolean shouldRender = false;
		int ticks = 0, frames = 0;
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lt) / nsPt;
			lt = now;
			shouldRender = lockedFps;
			
			while(delta >= 1) {
				tick();
				ticks++;
				delta--;
				shouldRender = true;
			}
			
			if(shouldRender) {
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - ltr >= 1000) {
				ltr += 1000;
				logger.log(Level.INFO, ticks + " tps, " + frames + " fps");
				ticks = frames = 0;
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		Screen.clear(0xff00ff, 100);
		
		Screen.finalizeLighting();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(Screen.getImage(), 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public static Logger getLogger() {
		return logger;
	}
}
