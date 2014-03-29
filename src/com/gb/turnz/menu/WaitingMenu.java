package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;

public class WaitingMenu extends Menu {

	public WaitingMenu(Menu menu) {
		super(menu);
		init();
	}

	public WaitingMenu(Game game) {
		super(game);
		init();
	}

	private void init() {
		addObject(new MenuObject.Text("Waiting For Server...", Font.getScreenCenterX("Waiting For Server..."), 250));
	}

}
