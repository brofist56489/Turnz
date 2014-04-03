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
		addObject(new MenuObject.Text("Waiting for", Font.getScreenCenterX("Waiting for"), 150));
		addObject(new MenuObject.Text("other player...", Font.getScreenCenterX("other player..."), 190));
		
		String ip = ServerConnection.getExternalIp();
		addObject(new MenuObject.Text(ip, Font.getScreenCenterX(ip), 240));
		
		MultiplayerLevel level = (MultiplayerLevel) Game.getLevel();
		level.setConnection(new ServerConnection((ServerConnection c) -> {
			Game.setMenu(new LevelSelector(new SendWorldMenu(Game.getInstance()), new MainMenu(Game.getInstance())));
		}).start());
		level.getConnection().setCallback((conn) -> {
			
		});
	}
}
