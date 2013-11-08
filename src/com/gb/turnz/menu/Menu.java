package com.gb.turnz.menu;

import java.util.ArrayList;
import java.util.List;

import com.gb.turnz.base.BaseGame;

public abstract class Menu {
	protected List<MenuObject> objects = new ArrayList<MenuObject>();
	protected BaseGame game;
	protected Menu parentMenu;
	
	public Menu(Menu menu) {
		this.game = menu.game;
		this.parentMenu = menu;
	}
	
	public Menu(BaseGame game) {
		this.parentMenu = null;
		this.game = game;
	}
	
	public void tick() {
		
	}
	
	public void onClick() {
		
	}
	
	public void render() {
		for(int i=0; i<objects.size(); i++) {
			objects.get(i).render();
		}
	}
}
