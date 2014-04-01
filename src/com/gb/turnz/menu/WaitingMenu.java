package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.MultiplayerLevel;
import com.gb.turnz.level.World;
import com.gb.turnz.net.Connection;
import com.gb.turnz.util.Logger;

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
		addObject(new MenuObject.Text("Waiting For Server...", Font.getScreenCenterX("Waiting For Server..."), Screen.HEIGHT / 2 - 16));

		Connection connection = ((MultiplayerLevel) Game.getLevel()).getConnection();
		connection.setCallback((conn) -> {
			Game.getLogger().log("TRYING TO RECEIVE WORLD", Logger.WARNING);
			World world = conn.receiveWorld();
			if (world != null) {
				Game.getLogger().log("RECEIVED WORLD", Logger.WARNING);
				conn.setCallback((conne) -> {});
				Game.getLevel().setWorld(world);
				world.checkConnections();
				conn.write("recv world");
				Game.setMenu(new MultiplayerGameMenu(new MainMenu(Game.getInstance())));
			}
		});
	}

}
