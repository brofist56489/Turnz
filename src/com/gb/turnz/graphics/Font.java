package com.gb.turnz.graphics;

public class Font {
	static {
		ImageManager.addImage("/textures/font.png", "FONT");
		ImageManager.getImage("FONT").resize(3);
	}
	
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz{}|~!#%')+-/\"$&(*,.0123456789;=?:<>[] ";
	
	public static void render(String msg, int x, int y) {
		for(int i = 0; i < msg.length(); i++) {
			int index = chars.indexOf(msg.charAt(i));
			
			if(index >= 0) {
				ImageManager.renderFromImage("FONT", x + i * 24, y, index, 10 * 3, 0);
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