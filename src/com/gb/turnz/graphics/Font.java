package com.gb.turnz.graphics;

public class Font {
	public static void init() {
//		ImageManager.loadImage("/textures/font.png", "FONT");
		ImageManager.addImage("/textures/font2.png", "FONT_2");
	}
//	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,!?:><";
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz{}|~!#%')+-/\"$&(*,.0123456789;=?:<>[] ";
	
	public static void render(String msg, int x, int y) {
		for(int i = 0; i < msg.length(); i++) {
			int index = chars.indexOf(msg.charAt(i));
			
			if(index >= 0) {
				ImageManager.renderFromImage("FONT_2", x + i * 8, y, index, 10, 0);
			}
		}
	}
	
//	public static void render(String msg, int x, int y, int color) {
//		
//		for(int i = 0; i < msg.length(); i++) {
//			int index = chars.indexOf(msg.charAt(i));
//			
//			if(index >= 0) {
//				Image img = ImageManager.getImage("FONT_2");
//				b.renderColorFont(img, x + i * 10, y, index, 10, color);
//			}
//		}
//	}
}