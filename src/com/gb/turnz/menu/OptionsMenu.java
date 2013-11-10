package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;

public class OptionsMenu extends Menu {

	public OptionsMenu(Game game) {
		super(game);
		init();
	}

	public OptionsMenu(Menu menu) {
		super(menu);
		init();
	}

	private void init() {
		addObject(new MenuObject.Button(136, 300, "Done") {
			public void onClick() {
				Game.setMenu(parentMenu);
			}
		});
		addObject(new MenuObject.Option(26, 100, 300, (int)(Font.getScale() * 10), "SFX", "On", "Off", false) {
			public void apply() {
				Game.setSFX(active);
			}
		});
		addObject(new MenuObject.Option(26, 130, 300, (int)(Font.getScale() * 10), "Music", "On", "Off", false) {
			public void apply() {
				Game.setMusic(active);
			}
		});
	}
}
