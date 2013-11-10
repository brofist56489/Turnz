package com.gb.turnz.graphics;

import java.util.HashMap;

public class ImageManager {
	private static HashMap<String, Image> images;
	
	static {
		images = new HashMap<String, Image>();
	}
	
	public static void addImage(String path, String name) {
		Image i = new Image(path);
		images.put(name, i);
	}
	
	public static void removeImage(String name) {
		images.remove(name);
	}
	
	public static Image getImage(String name) {
		return images.get(name);
	}
	
	public static void render(String name, int x, int y, int flip) {
		Image i = getImage(name);
		Screen.render(i, x, y, flip);
	}
	
	public static void render(String name, int x, int y, double rad) {
		Image i = getImage(name);
		Screen.render(i, x, y, rad);
	}
	
	public static void renderFromTileMap(String name, int x, int y, int tileId, int tileWidth, int flip) {
		Image i = getImage(name);
		Screen.renderFromTileMap(i, x, y, tileId, tileWidth, flip);
	}
	
	public static void renderFromTileMap(String name, int x, int y, int tileId, int tileWidth, int flip, double rotation) {
		Image i = getImage(name);
		Screen.renderFromTileMap(i, x, y, tileId, tileWidth, flip, rotation);
	}
	
	public static void setImage(String name, Image img) {
		images.put(name, img);
	}
}
