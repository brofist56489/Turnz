package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Image;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.level.creator.LevelCreatorToolMenu;

public class MainMenu extends Menu {

	static {
		ImageManager.addImage("/textures/turnzMenu3.png", "menuBorder");
		ImageManager.addImage("/textures/turnzTitle.png", "menuTitle");
	}
	
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
	
	public void render() {
		super.render();
		
		ImageManager.render("menuTitle", Image.getScreenCenterX("menuTitle"), 60, 0);
	}
}
