package com.gb.turnz.menu;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Screen;

public class Menu {
	protected List<MenuObject> objects = new ArrayList<MenuObject>();
	protected Game game;
	protected Menu parentMenu;
	protected MenuObject hoverObject;
	
	public Menu(Menu menu) {
		this.game = menu.game;
		this.parentMenu = menu;
	}
	
	public Menu(Game game) {
		this.parentMenu = null;
		this.game = game;
	}
	
	public void addObject(MenuObject o) {
		objects.add(o);
	}
	
	public void tick() {
		if(Game.getMouse().hasMoved()) {
			hoverObject = null;
			for(MenuObject o : objects) {
				if(o.getRect().intersects(Game.getMouse().getRect())) {
					o.onHover();
					hoverObject = o;
				} else {
					o.onNotHover();
				}
			}
		}
		
		if(hoverObject != null) {
			if(Game.getMouse().buttonDownOnce(1)) {
				hoverObject.onClick();
			}
		}
	}
	
	public void onClick() {
		
	}
	
	public void render() {
		for(int i=0; i<objects.size(); i++) {
			objects.get(i).render();
		}
	}
	
	
	public static class Button extends MenuObject {

		protected String text;
		
		public Button(int x, int y, String text) {
			super(x, y, text.length() * 10 - 5, 12);
			this.text = text;
		}
		
		public void render() {
			Screen.renderRect(x, y, width, height, color);
			Font.render(text, x + 1, y + 1);
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
	
	public static abstract class MenuObject {
		
		protected int x, y;
		protected int width, height;
		protected int color;
		
		public MenuObject(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.width = w;
			this.height = h;
		}
		
		public Rectangle getRect() {
			return new Rectangle(x, y, width, height);
		}
		
		public abstract void onClick();
		
		public abstract void onHover();
		
		public abstract void onNotHover();
		
		public abstract void render();
	}
}
