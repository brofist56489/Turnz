package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.level.creator.LevelCreatorToolMenu;

public class MainMenu extends Menu {

	public MainMenu(Game game) {
		super(game);
		Font.setScale(2);
		init();
	}

	public MainMenu(Menu menu) {
		super(menu);
		Font.setScale(2);
		init();
	}

	private void init() {
		ImageManager.addImage("/textures/turnzMenu.png", "menuImage");
		addObject(new MenuObject.MenuImage("menuImage", 0, 0));
		addObject(new MenuObject.Button(Font.getScreenCenterX("Play"), 200, "Play") {
			public void onClick() {
				Game.setMenu(new PlayMenu(menu));
			}
		});

		addObject(new MenuObject.Button(Font.getScreenCenterX("Options"), 225, "Options") {
			public void onClick() {
				Game.setMenu(new OptionsMenu(menu));
			}
		});

		addObject(new MenuObject.Button(Font.getScreenCenterX("Level Creator"), 250, "Level Creator") {
			public void onClick() {
				Game.setMenu(new LevelCreatorToolMenu());
			}
		});

		addObject(new MenuObject.Button(Font.getScreenCenterX("Quit"), 275, "Quit") {
			public void onClick() {
				System.exit(1);
			}
		});

	}
}
