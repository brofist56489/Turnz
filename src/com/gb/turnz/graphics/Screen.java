package com.gb.turnz.graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.gb.turnz.base.Game;

public class Screen {
	public static final String WINDOW_TITLE = "Turnz";
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int SCALE = 3;
	
	private static JFrame frame;
	
	private static final int COLOR_KEY = 0x7f007f;
	
	private static int xo = 0;
	private static int yo = 0;
	
	private static int[] pixels;
	private static BufferedImage destImage;
	
	public static void initialize() {
		destImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)destImage.getRaster().getDataBuffer()).getData();
	}
	
	public static void clear(int clearColor) {
		for(int i=0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = clearColor;
		}
	}
	
	public static void render(Image img, int x, int y, int flip) {
		render(img.getPixels(), x, y, img.getWidth(), img.getHeight(), flip);
	}
	
	public static void render(int[] pixels, int x, int y, int w, int h, int flip) {
		x -= xo;
		y -= yo;

		boolean flipx = (flip & 0x01) == 0x01;
		boolean flipy = (flip & 0x02) == 0x02;
		
		int ys, xs, col;
		for(int yy = 0; yy < h; yy++) {
			if (flipy)
				ys = (h - 1 - yy) + y;
			else
				ys = yy + y;
			if(ys < 0 || ys >= HEIGHT) continue;
			for(int xx = 0; xx < w; xx++) {
				if(flipx)
					xs = (w - 1 - xx) + x;
				else
					xs = xx + x;
				if(xs < 0 || xs >= WIDTH) continue;
				
				col = pixels[xx + yy * w];
				if(col == COLOR_KEY) {
					continue;
				}
				
				Screen.pixels[xs + ys * WIDTH] = col;
			}
		}
	}
	
	public static BufferedImage getImage() {
		return destImage;
	}
	
	public static void makeJFrame(Game game) {
		frame = new JFrame(WINDOW_TITLE);
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
	}
}
