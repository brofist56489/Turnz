package com.gb.turnz;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Screen;

/** Main entry point of the game */
public class Program {

	public static void main(String[] args) {
		Game game = new Game();
		Screen.makeJFrame(game);
		game.start();
	}
}
