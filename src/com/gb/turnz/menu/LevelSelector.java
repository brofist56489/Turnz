package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.level.World;

public class LevelSelector extends Menu {
	
	public LevelSelector(Game game) {
		super(game);
		init();
	}
	
	public LevelSelector(Menu menu) {
		super(menu);
		init();
	}
	
	private void init() {
		String[] levels = World.getLevels();
		for(int i = 0; i < levels.length; i++) {
			ImageManager.addImage(levels[i], levels[i]);
			String l = levels[i];
			addObject(new LevelSelectorButton(Font.getScreenCenterX(l), 100 + (i * 50), "World " + i, l));
		}
	}
	
	private static class LevelSelectorButton extends MenuObject.Button {

		private String levelName;
		
		public LevelSelectorButton(int x, int y, String text, String levelName) {
			super(x, y, text);
			this.levelName = levelName;
		}
		
		public void onClick() {
			Game.setWorld(new World());
			Game.getWorld().loadFromImage(ImageManager.getImage(levelName));
			Game.getWorld().checkConnections();
			Game.setMenu(new GameMenu(Game.getInstance()));
		}
	}
}
