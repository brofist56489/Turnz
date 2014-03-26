package com.gb.turnz.menu;

import com.gb.turnz.base.Game; 
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.graphics.Font;

public class ScoreMenu extends Menu {
	
	private long millisTaken = Long.MAX_VALUE;
//	private int diskCollect = 0;
	
	public ScoreMenu(Game game, long millisTaken, int diskCollect) {
		super(game);
		this.millisTaken = millisTaken;
//		this.diskCollect = diskCollect;
		init();
	}
	
	public ScoreMenu(Menu menu, long millisTaken, int diskCollect) {
		super(menu);
		this.millisTaken = millisTaken;
//		this.diskCollect = diskCollect;
		init();
	}
	
	private void init() {
		
		addObject(new MenuObject.Text("Score(s)", Font.getScreenCenterX("Score(s)"), 30));
		
		addObject(new MenuObject.Text("Player 1:\t\t\t\t\t" + millisTaken, Font.getScreenCenterX("Player 1:\t\t\t\t\t" + millisTaken), 75));
		
		addObject(new MenuObject.Button(Font.getScreenCenterX("Continue"), 300, "Continue", 0x7f007f) {
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
