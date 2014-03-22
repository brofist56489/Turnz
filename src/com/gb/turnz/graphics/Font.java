package com.gb.turnz.graphics;

public class Font {
	static {
		ImageManager.addImage("/textures/font.png", "FONT");
		ImageManager.addImage("/textures/font.png", "FONT_2");
	}
	
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz{}|~!#%')+-/\"$&(*,.0123456789;=?:<>[] ";
	private static double scale = 1;
	
	public static void render(String msg, int x, int y) {
		for(int i = 0; i < msg.length(); i++) {
			int index = chars.indexOf(msg.charAt(i));
			
			if(index >= 0) {
				ImageManager.renderFromTileMap("FONT_2", x + i * Font.getWidth(), y, index, getHeight(), 0);
			}
		}
	}
	
	public static void setScale(double scale) {
		Font.scale = scale;
		ImageManager.setImage("FONT_2", ImageManager.getImage("FONT").getScaledImage(scale));
	}
	
	public static double getScale() {
		return Font.scale;
	}
	
	public static int getWidth() {
		return (int) (scale * 8);
	}
	
	public static int getHeight() {
		return (int) (scale * 10);
	}
	
	public static int getScreenCenterX(String str) {
		int x = (Screen.WIDTH / 2) - ((str.length() * getWidth()) / 2);
		return x;
	}
	
	public static void render(String msg, int x, int y, int color) {
		
		for(int i = 0; i < msg.length(); i++) {
			int index = chars.indexOf(msg.charAt(i));
			
			if(index >= 0) {
				Image img = ImageManager.getImage("FONT_2");
				Screen.renderColorFont(img, x + i * Font.getWidth(), y, index, color);
			}
		}
	}
}