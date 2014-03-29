package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.level.Level;
import com.gb.turnz.level.MultiplayerLevel;

public class ConnectionMenu extends Menu {

	public ConnectionMenu(Menu menu) {
		super(menu);
		init();
	}

	public ConnectionMenu(Game game) {
		super(game);
		init();
	}

	private void init() {
		Game.setLevel(new MultiplayerLevel());

		addObject(new MenuObject.Button(Font.getScreenCenterX("Host"), 60, "Host") {
			public void onClick() {
				Game.setMenu(new HostMenu(menu));
			}
		});
		addObject(new MenuObject.Button(Font.getScreenCenterX("Join"), 100, "Join") {
			public void onClick() {
				Game.setMenu(new JoinMenu(menu));
			}
		});
	}

}
