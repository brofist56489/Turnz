package com.gb.turnz.menu;

import java.awt.event.KeyEvent;

import com.gb.turnz.base.Game;
import com.gb.turnz.level.Level;

public class GameMenu extends Menu {
	
	public GameMenu(Game game) {
		super(game);
	}

	public GameMenu(Menu menu) {
		super(menu);
	}
	
	public void tick() {
		Level level = Game.getLevel();
		level.tick();
		if(Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_RIGHT)) {
			level.getWorld().initializeRotation(1);
		}
		if(Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_LEFT)) {
			level.getWorld().initializeRotation(0);
		}
		if(Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_ESCAPE)) {
			Game.setMenu(new PauseMenu(this));
		}
	}
	
	public void render() {
		Game.getLevel().getWorld().render();
	}
}
