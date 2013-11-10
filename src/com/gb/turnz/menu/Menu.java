package com.gb.turnz.menu;

import java.util.ArrayList;
import java.util.List;

import com.gb.turnz.base.Game;

public abstract class Menu {
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
		o.setMenu(this);
		objects.add(o);
	}
	
	public Menu getParentMenu() {
		return parentMenu;
	}

	public void tick() {
		if (Game.getMouse().hasMoved()) {
			hoverObject = null;
			for (MenuObject o : objects) {
				if (o.getRect().intersects(Game.getMouse().getRect())) {
					o.onHover();
					hoverObject = o;
				} else {
					o.onNotHover();
				}
			}
		}

		if (hoverObject != null) {
			if (Game.getMouse().buttonDownOnce(1)) {
				hoverObject.onClick();
			}
		}
	}

	public void onClick() {

	}

	public void render() {
		
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).render();
		}
		
//		if(height != Screen.HEIGHT) {
//			double height = Screen.HEIGHT * (Screen.HEIGHT / (float)this.height);
//			Screen.renderRect(Screen.WIDTH - 10, (int)(Screen.HEIGHT * scroll / height) / 2 + Screen.getYoff() / 2, 8, (int)height, 0x7f7f7f);
//		}
	}

}
