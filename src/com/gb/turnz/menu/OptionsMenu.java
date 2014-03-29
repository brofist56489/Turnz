package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.util.Constants;

public class OptionsMenu extends Menu {
	MenuObject.TextBox port;

	public OptionsMenu(Game game) {
		super(game);
		init();
	}

	public OptionsMenu(Menu menu) {
		super(menu);
		init();
	}

	private void init() {
		
		addObject(new MenuObject.Text("Options", Font.getScreenCenterX("Options"), 30));
		
		addObject(new MenuObject.Button(136, 300, "Done") {
			public void onClick() {
				Constants.PORT = Integer.parseInt(port.getText());
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
		
		addObject(new MenuObject.Text("Host Port:", 26, 160));
		port = new MenuObject.TextBox(226, 160, 5);
		addObject(port);
		
//		ButtonGroup group = new ButtonGroup();
//		MenuObject.TextBox box1 = new MenuObject.TextBox(26, 160, 10);
//		MenuObject.TextBox box2 = new MenuObject.TextBox(50 + (10 * Font.getWidth()), 150, 5);
//		
//		group.add(box1);
//		group.add(box2);
//		
//		addObject(box1);
//		addObject(box2);
	}
}
