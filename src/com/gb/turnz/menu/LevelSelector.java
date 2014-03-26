package com.gb.turnz.menu;

import java.awt.FileDialog;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Image;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.graphics.Image.ImageLocation;
import com.gb.turnz.level.Level;
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
		addObject(new MenuObject.Text("Pick A World", Font.getScreenCenterX("Pick A World"), 30));
		String[] levels = Level.getLevels();
		for(int i = 0; i < levels.length; i++) {
			ImageManager.addImage(levels[i], levels[i]);
			String l = levels[i];
			addObject(new LevelSelectorButton(20, 100 + (i * 35), "World " + (i + 1), l));
		}
		
		addObject(new MenuObject.Button(Screen.WIDTH / 2 - 20, 200, "Custom World", 0xff0000) {
			public void onClick() {
				Game.getLevel().setWorld(Level.getLevels()[0]);
				World world = Game.getLevel().getWorld();
				FileDialog fileLoader = new FileDialog(Screen.getFrame(), "Load Level", FileDialog.LOAD);
				fileLoader.setFile("*.lvl");
				fileLoader.setVisible(true);
				
				String path = fileLoader.getDirectory() + "/" + fileLoader.getFile();
				
				Image image = new Image(path, ImageLocation.EXTERNAL);
				world.loadFromImage(image);
				world.checkConnections();
				Game.setMenu(parentMenu);
			}
		});
	}
	
	private static class LevelSelectorButton extends MenuObject.Button {

		private String levelName;
		
		public LevelSelectorButton(int x, int y, String text, String levelName) {
			super(x, y, text);
			this.levelName = levelName;
		}
		
		public void onClick() {
			Level level = Game.getLevel();
			level.setWorld(levelName);
			Game.setMenu(menu.parentMenu);
		}
	}
}
