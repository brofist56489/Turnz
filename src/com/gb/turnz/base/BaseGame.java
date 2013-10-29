package com.gb.turnz.base;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.gb.turnz.graphics.Image;
import com.gb.turnz.graphics.Screen;

public class BaseGame extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private Thread gameThread;
	private boolean running = false;
	
	Image testImage;
	
	private double rot = 0.0;

	public BaseGame() {
		Screen.initialize();
		testImage = new Image("/textures/testImage.png");
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
		long lt = System.nanoTime();
		double nsPt = 1000000000.0/60.0;
		double delta = 0.0;
		long ltr = System.currentTimeMillis();
		long now;
		boolean shouldRender = false;
		int ticks = 0, frames = 0;
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lt) / nsPt;
			lt = now;
			shouldRender = false;
			
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
				System.out.println(ticks + " tps, " + frames + " fps");
				ticks = frames = 0;
			}
		}
	}
	
	public void tick() {
		rot += 0.01;
		while(rot >= Math.PI * 2) rot -= Math.PI * 2;
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Screen.clear(0xff0000);
		
		Screen.render(testImage, 0, 0, rot);
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(Screen.getImage(), 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
}
