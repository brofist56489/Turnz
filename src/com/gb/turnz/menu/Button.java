package com.gb.turnz.menu;

import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Screen;

public class Button extends MenuObject {

	protected String text;
	
	public Button(int x, int y, String text) {
		super(x, y, text.length() * 10 + 1, 12);
		this.text = text;
	}
	
	public void render() {
		Screen.renderRect(x, y, width, height, color, true);
		Font.render(text, x + 1, y + 1);
	}
}
