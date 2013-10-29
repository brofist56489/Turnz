package com.gb.turnz;

import com.gb.turnz.base.BaseGame;
import com.gb.turnz.graphics.Screen;

/** Main entry point of the game */
public class Program {

	public static void main(String[] args) {
		BaseGame game = new BaseGame();
		Screen.makeJFrame(game);
		game.start();
	}
}
