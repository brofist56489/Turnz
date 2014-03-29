package com.gb.turnz.menu;

import java.awt.event.KeyEvent;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.Level;

public class GameMenu extends Menu {
	
	static {
		ImageManager.addImage("/textures/arrows/left.png", "arrow-left");
		ImageManager.addImage("/textures/arrows/right.png", "arrow-right");
	}
	
	public GameMenu(Game game) {
		super(game);
		init();
	}

	public GameMenu(Menu menu) {
		super(menu);
		init();
	}
	
	private void init() {
		addObject(new MenuObject.MenuImage("arrow-right", Screen.WIDTH / 2 - 34, Screen.HEIGHT - 32) {
			public void onClick() {
				Game.getLevel().getWorld().initializeRotation(0);
			}
		});
		addObject(new MenuObject.MenuImage("arrow-left", Screen.WIDTH / 2 + 2, Screen.HEIGHT - 32) {
			public void onClick() {
				Game.getLevel().getWorld().initializeRotation(1);
			}
		});
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
		
		super.tick();
	}
	
	public void render() {
		Game.getLevel().getWorld().render();
		super.render();
	}
}
