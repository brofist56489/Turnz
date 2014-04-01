package com.gb.turnz.menu;

import java.util.HashMap;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.MultiplayerLevel;
import com.gb.turnz.net.Connection;
import com.gb.turnz.net.ServerConnection;
import com.gb.turnz.util.Constants;

public class MultiplayerScoreMenu extends Menu {
	
	private HashMap<String, Integer> scores = null;
	private String winner;
	
	public MultiplayerScoreMenu(Game game, HashMap<String, Integer> scores, String winner) {
		super(game);
		this.scores = scores;
		this.winner = winner;
		init();
	}
	
	private void init() {
		String title = "Score" + ((scores.size() > 1) ? "s" : "");
		addObject(new MenuObject.Text(title, Font.getScreenCenterX(title), 30));
		
		int i = 0;
		for(String name : scores.keySet()) {
			int score = scores.get(name);
			String msg = name + ":\t\t\t\t\t" + score;
			addObject(new MenuObject.Text(msg, Font.getScreenCenterX(msg), i * 50 + 50));
			i++;
		}
		
		addObject(new MenuObject.Text(winner + " has won", Font.getScreenCenterX(winner + " has won"), (++i) * 50 + 100));
		
		addObject(new MenuObject.Button(Font.getScreenCenterX("Continue"), Constants.BACK_BUTTON_Y, "Continue", 0x7f007f) {
			public void onClick() {
				Connection conn = ((MultiplayerLevel) Game.getLevel()).getConnection();
				if(conn instanceof ServerConnection)
					Game.setMenu(new LevelSelector(new SendWorldMenu(Game.getInstance()), new MainMenu(Game.getInstance())));
				else
					Game.setMenu(new WaitingMenu((new MainMenu(Game.getInstance()))));
			}
			public void onNotHover() {
				color = 0x7f007f;
			}
		});
	}

	public void render() {
		Game.getLevel().getWorld().render();
		Screen.fade(100);
		
		super.render();
	}
}
