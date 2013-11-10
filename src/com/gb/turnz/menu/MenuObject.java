package com.gb.turnz.menu;

import java.awt.Rectangle;

import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;

public abstract class MenuObject {

	protected int x, y;
	protected int width, height;
	protected int color;

	protected Menu menu;

	public MenuObject(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

	public abstract void onClick();

	public abstract void onHover();

	public abstract void onNotHover();

	public abstract void render();

	public static class Button extends MenuObject {

		protected String text;

		public Button(int x, int y, String text) {
			super(x, y, text.length() * Font.getWidth(), Font.getHeight());
			this.text = text.trim();
		}
		
		public Button(int x, int y, String text, int color) {
			this(x, y, text);
			this.color = color;
		}

		public void render() {
			Screen.renderRect(x, y, width, height, color);
			Font.render(text, x + ((width - (text.length() * Font.getWidth())) / 2), y);
		}

		public void onClick() {

		}

		public void onHover() {
			color = 0x007fff;
		}

		public void onNotHover() {
			color = 0x000000;
		}
	}

	public static class MenuImage extends MenuObject {

		private String imgName;

		public MenuImage(String imgName, int x, int y) {
			super(x, y, ImageManager.getImage(imgName).getWidth(), ImageManager.getImage(imgName).getHeight());
			this.imgName = imgName;
		}

		public void render() {
			ImageManager.render(imgName, x, y, 0);
		}

		public void onClick() {
		}

		public void onHover() {
		}

		public void onNotHover() {
		}
	}

	public static class Option extends MenuObject {

		protected boolean active;
		protected String trueString = "", falseString = "", option = "", renderString = "";

		public Option(int x, int y, int w, int h, String option, String trueString, String falseString, boolean active) {
			super(x, y, w, h);
			this.trueString = trueString;
			this.falseString = falseString;
			this.option = option;
			this.active = active;
			renderString = active ? trueString : falseString;
		}

		public void onClick() {
			active = !active;
			renderString = active ? trueString : falseString;
			apply();
		}

		public void onHover() {
			color = 0x00ff00;
		}

		public void onNotHover() {
			color = 0x0;
		}
		
		public void apply() {
			
		}

		public void render() {
			Screen.renderRect(x, y, width, height, color);
			Font.render(option, x, y);
			Font.render(renderString, x + (int) (width - (renderString.length() * Font.getWidth())), y);
		}
	}
}
