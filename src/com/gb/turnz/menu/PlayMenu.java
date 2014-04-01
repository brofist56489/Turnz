package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.util.Constants;

public class PlayMenu extends Menu {

	public PlayMenu(Menu menu) {
		super(menu);
		init();
	}

	public void init() {
		addObject(new MenuObject.Text("Play", Font.getScreenCenterX("Play"), 20));

		addObject(new MenuObject.Button(Font.getScreenCenterX("Single Player"), 100, "Single Player") {
			public void onClick() {
				Game.setMenu(new GameMenu(menu));
			}
		});

		addObject(new MenuObject.Button(Font.getScreenCenterX("Multiplayer"), 150, "Multiplayer") {
			public void onClick() {
				Game.setMenu(new ConnectionMenu(menu));
			}
		});

		addObject(new MenuObject.Button(Font.getScreenCenterX("Back"), Constants.BACK_BUTTON_Y, "Back") {
			public void onClick() {
				Game.setMenu(parentMenu);
			}
		});
	}
}
