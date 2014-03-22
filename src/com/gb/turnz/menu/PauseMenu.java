package com.gb.turnz.menu;

import com.gb.turnz.base.Game; 
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.graphics.Font;

public class PauseMenu extends Menu {
	
	public PauseMenu(Game game) {
		super(game);
		init();
	}
	
	public PauseMenu(Menu menu) {
		super(menu);
		init();
	}
	
	private void init() {
		addObject(new MenuObject.Text("Paused", Font.getScreenCenterX("Paused"), 30));
		addObject(new MenuObject.Button(Font.getScreenCenterX("Play"), 150, "Play", 0x7f007f) {
			public void onClick() {
				Game.setMenu(parentMenu);
			}
			public void onNotHover() {
				color = 0x7f007f;
			}
		});
		addObject(new MenuObject.Button(Font.getScreenCenterX("Options"), 200, "Options", 0x7f007f) {
			public void onClick() {
				Game.setMenu(new OptionsMenu(menu));
			}
			public void onNotHover() {
				color = 0x7f007f;
			}
		});
		addObject(new MenuObject.Button(Font.getScreenCenterX("Quit"), 300, "Quit", 0x7f007f) {
			public void onClick() {
				Game.setMenu(new MainMenu(menu));
			}
			public void onNotHover() {
				color = 0x7f007f;
			}
		});
	}
	
	public void render() {
		Game.getWorld().render();
		Screen.fade(100);
		
		super.render();
	}
}
