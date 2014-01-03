package com.gb.turnz.base;

import java.applet.Applet;
import java.awt.BorderLayout;

public class GameApplet extends Applet {
	private static final long serialVersionUID = -6849201575098466008L;
	private Game game = new Game();
	
	public GameApplet() {
		setLayout(new BorderLayout());
		add(game);
	}
	
	public synchronized void start() {
		game.start();
	}
	
	public synchronized void stop() {
		game.stop();
	}
	
	public void destroy() {
		game.stop();
	}
}
