package com.gb.turnz.menu;

import java.awt.event.KeyEvent;

import com.gb.turnz.base.Game;
import com.gb.turnz.level.World;

public class GameMenu extends Menu {

	private World world;
	
	public GameMenu(Game game) {
		super(game);
		world = Game.getWorld();
	}

	public GameMenu(Menu menu) {
		super(menu);
		world = Game.getWorld();
	}
	
	public void tick() {
		world.tick();
		if(Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_RIGHT)) {
			world.initializeRotation(1);
		}
		if(Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_LEFT)) {
			world.initializeRotation(0);
		}
		if(Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_ESCAPE)) {
			Game.setMenu(new PauseMenu(this));
		}
	}
	
	public void render() {
		world.render();
	}
}
