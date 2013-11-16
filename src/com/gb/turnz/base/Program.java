package com.gb.turnz.base;

import com.gb.turnz.graphics.Screen;

public class Program {
	public static void main(String[] args) {
		Game game = new Game();
		Screen.makeJFrame(game);
		game.start();
	}
}
