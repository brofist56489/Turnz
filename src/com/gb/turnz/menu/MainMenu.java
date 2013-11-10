package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.ImageManager;

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
				Game.setMenu(new LevelSelector(menu));
			}
		});
		
		addObject(new MenuObject.Button(Font.getScreenCenterX("Options"), 250, "Options") {
			public void onClick() {
				Game.setMenu(new OptionsMenu(menu));
			}
		});
		
		addObject(new MenuObject.Button(Font.getScreenCenterX("Quit"), 300, "Quit") {
			public void onClick() {
				System.exit(1);
			}
		});
		
	}
}
