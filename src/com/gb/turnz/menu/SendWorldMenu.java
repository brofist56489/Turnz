package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.net.ServerConnection;

public class SendWorldMenu extends Menu {

	private ServerConnection connection;
	
	public SendWorldMenu(Menu menu, ServerConnection conn) {
		super(menu);
		init();
		connection = conn;
	}
	
	private void init() {
		connection.sendWorld(Game.getLevel().getWorld());
		addObject(new MenuObject.Text("Loading...", Font.getScreenCenterX("Loading..."), Screen.HEIGHT / 2 - Font.getHeight() / 2));
	}
}
