package com.gb.turnz.menu;

import java.awt.FileDialog;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Image;
import com.gb.turnz.graphics.Image.ImageLocation;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.Level;
import com.gb.turnz.level.World;
import com.gb.turnz.util.Constants;

public class LevelSelector extends Menu {

	private LevelSelector(Menu m) {
		super(m);
	}

	private Menu successMenu;
	private Menu backMenu;

	public LevelSelector(Menu successMenu, Menu backMenu) {
		super((Game) null);
		this.successMenu = successMenu;
		this.backMenu = backMenu;
		init();
	}

	public void addObject(MenuObject o) {
		o.setMenu(this);
		objects.add(o);
		if (o instanceof LevelSelectorButton) {
			((LevelSelectorButton) o).succMenu = successMenu;
		}
	}

	private void init() {
		addObject(new MenuObject.Text("Pick A World", Font.getScreenCenterX("Pick A World"), 30));
		String[] levels = Level.getLevels();
		for (int i = 0; i < levels.length; i++) {
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
				Game.setMenu(successMenu);
			}
		});

		addObject(new MenuObject.Button(Font.getScreenCenterX("Back"), Constants.BACK_BUTTON_Y, "Back") {
			public void onClick() {
				Game.setMenu(backMenu);
			}
		});
	}

	private static class LevelSelectorButton extends MenuObject.Button {

		private String levelName;
		public Menu succMenu;

		public LevelSelectorButton(int x, int y, String text, String levelName) {
			super(x, y, text);
			this.levelName = levelName;
		}

		public void onClick() {
			Level level = Game.getLevel();
			level.setWorld(levelName);
			Game.setMenu(succMenu);
		}
	}
}
