package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.level.MultiplayerLevel;
import com.gb.turnz.net.ServerConnection;

public class HostMenu extends Menu {

	public HostMenu(Menu menu) {
		super(menu);
		init();
	}
	
	private void init() {
		addObject(new MenuObject.Text("Waiting for other player", Font.getScreenCenterX("Waiting for other player"), 150));
		
		String ip = ServerConnection.getExternalIp();
		addObject(new MenuObject.Text(ip, Font.getScreenCenterX(ip), 200));
		
		MultiplayerLevel level = (MultiplayerLevel) Game.getLevel();
		level.setConnection(new ServerConnection((ServerConnection c) -> {
			Game.setMenu(new LevelSelector(Game.getInstance()));
		}).start());
	}
}
