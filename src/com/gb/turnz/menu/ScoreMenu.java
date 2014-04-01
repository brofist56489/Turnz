package com.gb.turnz.menu;

import java.util.HashMap;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.util.Constants;

public class ScoreMenu extends Menu {
	private int numOfPlayers = -1;
	
	private HashMap<String, Integer> scores;
	
	public ScoreMenu(Game game, HashMap<String, Integer> scores) {
		super(game);
		this.scores = scores;
		numOfPlayers = scores.size();
		init();
	}
	
	private void init() {
		String title = "Score" + ((numOfPlayers > 1) ? "s" : "");
		addObject(new MenuObject.Text(title, Font.getScreenCenterX(title), 30));
		
		int i = 0;
		for(String name : scores.keySet()) {
			int score = scores.get(name);
			String msg = name + ":\t\t\t\t\t" + score;
			addObject(new MenuObject.Text(msg, Font.getScreenCenterX(msg), i * 50 + 50));
			i++;
		}
		
		addObject(new MenuObject.Button(Font.getScreenCenterX("Continue"), Constants.BACK_BUTTON_Y - 40, "Continue", 0x7f007f) {
			public void onClick() {
				Game.setMenu(new LevelSelector(new GameMenu(Game.getInstance()) { public void switchedTo() { } }, new MainMenu(Game.getInstance())));
			}
			public void onNotHover() {
				color = 0x7f007f;
			}
		});
		
		addObject(new MenuObject.Button(Font.getScreenCenterX("Main"), Constants.BACK_BUTTON_Y, "Main", 0x7f007f) {
			public void onClick() {
				Game.setMenu(new MainMenu(menu));
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
