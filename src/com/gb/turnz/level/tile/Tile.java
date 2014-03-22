package com.gb.turnz.level.tile;

import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.World;

public class Tile {
	
	static {
		ImageManager.addImage("/textures/tileMap.png", "tileMap");
	}
	
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	
	protected int color;
	protected boolean solid;
	protected int textId;
	protected int id;
	protected int data1;
	protected int data2;
	
	public static Tile newTile(Tiles tileType) {
		if(tileType.isConnected()) {
			return new ConnectedTile(tileType);
		} else {
			return new Tile(tileType);
		}
	}
	
	public static Tile newTile(int id) {
		return newTile(Tiles.getById(id));
	}
	
	protected Tile(Tiles tileType) {
		setAs(tileType);
	}
	
	protected Tile(int id) {
		setAs(Tiles.getById(id));
	}
	
	public void setAs(Tiles tileType) {
		color = tileType.getColor();
		solid = tileType.isSolid();
		textId = tileType.getTextureId();
		id = tileType.getId();
	}
	
	public void render(int x, int y, World world) {
		if(textId < 0) {
			if(color != 0x0)
				Screen.renderRect(x, y, WIDTH, HEIGHT, color);
		} else {
			if(world.getRotation() == 0)
				ImageManager.renderFromTileMap("tileMap", x, y , textId, WIDTH, 0);
			else
				ImageManager.renderFromTileMap("tileMap", x, y, textId, WIDTH, 0, Math.PI / 2 - world.getRotationInRadians());
		}
	}
	
	public enum Tiles {
		AIR(0x000000, false, false, 15, 0),
		BLUE_WALL(0x7f7f7f, true, true, 0, 1),
		RED_WALL(0xff0000, true, true, 17, 4),
		FINISH(0x9a89a8, false, false, 16, 2),
		BORDER_TILE(0x000000, true, false, -1, 5),
		BLOB(0x000000, true, false, -1, 3);
		
		private int color;
		private boolean solid;
		private int textId;
		private int id;
		private boolean connected;
		Tiles(int c, boolean s, boolean con, int t, int i) {
			color = c;
			solid = s;
			textId = t;
			id = i;
			connected = con;
		}
		
		public static Tiles getById(int id) {
			for(Tiles t : Tiles.values()) {
				if(t.getId() == id) {
					return t;
				}
			}
			return null;
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
		
		public boolean isConnected() {
			return connected;
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
