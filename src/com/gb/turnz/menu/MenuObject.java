package com.gb.turnz.menu;

import com.gb.turnz.graphics.Screen;

public abstract class MenuObject {
	
	protected int x, y;
	protected int width, height;
	protected int color;
	
	public MenuObject(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	public void render() {
		Screen.renderRect(x, y, width, height, color, true);
	}
}
