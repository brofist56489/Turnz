package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.MultiplayerLevel;
import com.gb.turnz.util.Logger;

public class SendWorldMenu extends Menu {
	
	public SendWorldMenu(Game game) {
		super(game);
		init();
	}
	
	private void init() {
		addObject(new MenuObject.Text("Loading...", Font.getScreenCenterX("Loading..."), Screen.HEIGHT / 2 - Font.getHeight() / 2));
	}
	
	public void switchedTo() {
		Game.getLogger().log("Sending World", Logger.SEVERE);
		new Thread(() -> {
			((MultiplayerLevel) Game.getLevel()).getConnection().sendWorld(Game.getLevel().getWorld());
		}).start();
		
		((MultiplayerLevel) Game.getLevel()).getConnection().setCallback((conn) -> {
			if(conn.read().equals("recv world")) {
				Game.getLogger().log("WORKED", Logger.SEVERE);
				Game.setMenu(new MultiplayerGameMenu(new MainMenu(Game.getInstance())));
				
				conn.setCallback((con) -> {
					String s = "";
					if((s = conn.read()).startsWith("score")) {
						Game.getLogger().log(s);
						String[] data = s.split("~");
						String name = data[1];
						int score = Integer.parseInt(data[2]);
						((MultiplayerLevel) Game.getLevel()).setOtherScore(name, score);
					}
				});
			}
		});
	}
}
