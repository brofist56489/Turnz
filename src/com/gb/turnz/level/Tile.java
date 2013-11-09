package com.gb.turnz.level;

import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;

public class Tile {
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	
	private int color;
	private boolean solid;
	private int textId;
	private int id;
	
	public Tile(Tiles tileType) {
		setAs(tileType);
	}
	
	public void setAs(Tiles tileType) {
		color = tileType.getColor();
		solid = tileType.isSolid();
		textId = tileType.getTextureId();
		id = tileType.getId();
	}
	
	public void render(int x, int y, World world) {
		if(textId < 0) {
			Screen.renderRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT, color);
		} else {
			ImageManager.renderFromImage("tileMap", x * WIDTH, y * HEIGHT, textId, WIDTH, 0);
		}
	}
	
	public enum Tiles {
		AIR(0x000000, false, -1, 0), WALL(0x7f7f7f, true, -1, 1);
		
		private int color;
		private boolean solid;
		private int textId;
		private int id;
		
		Tiles(int c, boolean s, int t, int i) {
			color = c;
			solid = s;
			textId = t;
			id = i;
		}
		
		public int getColor() {
			return color;
		}
		
		public boolean isSolid() {
			return solid;
		}
		
		public int getTextureId() {
			return textId;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public int getColor() {
		return color;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public int getTextureId() {
		return textId;
	}
	
	public int getId() {
		return id;
	}
}
