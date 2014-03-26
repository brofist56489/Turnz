package com.gb.turnz.menu;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.gb.turnz.base.Game;
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

	public static class Text extends MenuObject {

		protected String text;

		public Text(String text, int x, int y) {
			super(x, y, text.length() * Font.getWidth(), Font.getHeight());
			this.text = text.trim();
			color = 0xffffff;
		}

		public Text(String text, int x, int y, int color) {
			super(x, y, text.length() * Font.getWidth(), Font.getHeight());
			this.text = text.trim();
			this.color = color;
		}

		public void render() {
			Font.render(text, x + ((width - (text.length() * Font.getWidth())) / 2), y, color);
		}

		public void onClick() {

		}

		public void onHover() {

		}

		public void onNotHover() {

		}
	}

	public static class Button extends Text {

		private int backupColor = 0x000000;
		public Button(int x, int y, String text) {
			super(text, x, y, 0x000000);
		}

		public Button(int x, int y, String text, int color) {
			this(x, y, text);
			this.color = color;
			backupColor = color;
		}

		public void render() {
			Screen.renderRect(x, y, width, height, color);
			Font.render(text, x, y);
		}

		public void onClick() {

		}

		public void onHover() {
			color = 0x007fff;
		}

		public void onNotHover() {
			color = backupColor;
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

	public static class TextBox extends MenuObject {

		private boolean selected = true;
		private boolean renderCursor = false;
		
		private String msg = "";

		public TextBox(int x, int y, int chars) {
			super(x, y, chars * (Font.getWidth()), Font.getHeight());
		}

		public void tick() {
			if (selected) {
				for (int i = 0; i < 256; i++) {
					if (Game.getKeyboard().isKeyDownOnce(i)) {
						msg += (char) i;
					}
				}
				if (Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_BACK_SPACE)) {
					if(msg.length() >= 2)
						msg = msg.substring(0, msg.length() - 2);
					else
						msg = "";
				}
				renderCursor = (System.currentTimeMillis() % 1000) > 500;
			}
		}

		public void onClick() {

		}

		public void render() {
			tick();
			Screen.renderRect(x - 5, y - 5, width + 10, height + 10, 0xffffff);
			Screen.renderRect(x, y, width, height, 0x0);
			
			Font.render(msg + (renderCursor ? ">" : ""), x, y);

		}

		public void onHover() {
		}

		public void onNotHover() {
		}
	}
}
