package com.gb.turnz.graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import com.gb.turnz.base.BaseGame;

public class Screen {
	public static final String WINDOW_TITLE = "Turnz";
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = 250;
	public static final int SCALE = 2;
	
	public static final int TRUE = 0x01;
	public static final int FALSE = 0x00;
	
	public static final int LIGHTING = 0x100;
	public static final int TRANSPARENT_COLOR = 0x200;
	
	private static JFrame frame;
	
	private static HashMap<Integer, Integer> properties;
	
	private static List<Light> lights;
	
	private static int xo = 0;
	private static int yo = 0;
	
	private static int[] pixels;
	private static int[] lighting;
	private static BufferedImage destImage;
	
	public static void initialize() {
		properties = new HashMap<Integer, Integer>();
		initProperties();
		
		destImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)destImage.getRaster().getDataBuffer()).getData();
		
		lights = new ArrayList<Light>();
		lighting = new int[WIDTH * HEIGHT];
	}
	
	private static void initProperties() {
		properties.put(LIGHTING, TRUE);
		properties.put(TRANSPARENT_COLOR, 0x7f007f);
	}
	
	public static void clear(int clearColor, int lightLevel) {
		for(int i=0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = clearColor;
			lighting[i] = lightLevel;
		}
	}
	
	public static void render(Image img, int x, int y, int flip) {
		render(img.getPixels(), x, y, img.getWidth(), img.getHeight(), flip, true);
	}
	
	public static void render(Image img, int xp, int yp, double rad) {
		render(img.getPixels(), xp, yp, img.getWidth(), img.getHeight(), rad, true);
	}
	
	public static void render(int[] pixels, int x, int y, int w, int h, int flip, boolean light) {
		x -= xo;
		y -= yo;

		boolean flipx = (flip & 0x01) == 0x01;
		boolean flipy = (flip & 0x02) == 0x02;
		
		int COLOR_KEY = properties.get(TRANSPARENT_COLOR);
		
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
	
	public static void renderRect(int xp, int yp, int w, int h, int color, boolean light) {
		xp -= xo;
		yp -= yo;
		
		int xs, ys;
		for(int y=0; y<h; y++) {
			ys = y + yp;
			if(ys < 0 || ys >= HEIGHT) continue;
			for(int x=0; x<w; x++) {
				xs = x + xp;
				if(xs < 0 || xs >= WIDTH) continue;
				
				pixels[xs + ys * WIDTH] = color;
			}
		}
	}
	
	public static void render(int[] pixels, int xp, int yp, int w, int h, double rot, boolean light) {
		xp -= xo;
		yp -= yo;
		
		double sin = Math.sin(rot);
		double cos = Math.cos(rot);

		int COLOR_KEY = properties.get(TRANSPARENT_COLOR);
		
		int x, y, fromX, fromY, toX, toY;
		int x2, y2;
		for(y=0; y<h; y++) {
			for(x=0; x<w; x++) {
				toX = (w / 2) - x;
				toY = (h / 2) - y;
				fromX = (int) ((cos * toX) - (sin * toY));
				fromY = (int) ((sin * toX) + (cos * toY));
				fromX += (w/ 2);
				fromY += (h / 2);
				
				x2 = x + xp;
				y2 = y + yp;
				if(x2 < 0 || y2 < 0 || x2 >= WIDTH || y2 >= HEIGHT) {
					continue;
				} else if(fromX < 0 || fromY < 0 || fromX >= w || fromY >= h) {
					continue;
				} else if (pixels[x + y * w] == COLOR_KEY) {
					continue;
				} else {
					Screen.pixels[x2 + y2 * WIDTH] = pixels[fromX + fromY * w];
				}
			}
		}
	}
	
	public static void renderFromImage(Image i, int xp, int yp, int tileId, int tileWidth, int flip, boolean light) {
		xp -= xo;
		yp -= yo;

		int COLOR_KEY = properties.get(TRANSPARENT_COLOR);
		
		boolean flipx = (flip & 0x01) == 0x01;
		boolean flipy = (flip & 0x02) == 0x02;

		int tw = i.getWidth() / tileWidth;
		int xt = tileId % tw;
		int yt = tileId / tw;
		int tileOffset = xt * tileWidth + yt * tileWidth * i.getWidth();

		for (int y = 0; y < tileWidth; y++) {
			if ((y + yp) < 0 || (y + yp) >= HEIGHT)
				continue;
			int ys = y;
			if (flipy)
				ys = (tileWidth - 1) - y;

			for (int x = 0; x < tileWidth; x++) {
				if ((x + xp) < 0 || (x + xp) >= WIDTH)
					continue;
				int xs = x;
				if (flipx)
					xs = (tileWidth - 1) - x;

				int c = i.getPixels()[xs + (ys * i.getWidth()) + tileOffset];
				if (c == COLOR_KEY)
					continue;
				
				pixels[(x + xp) + (y + yp) * WIDTH] = c;
			}
		}
	}
	
	public static void renderFromImage(Image i, int xp, int yp, int tileId, int tileWidth, int flip, double rot, boolean light) {
		xp -= xo;
		yp -= yo;

		int COLOR_KEY = properties.get(TRANSPARENT_COLOR);
		
		boolean flipx = (flip & 0x01) == 0x01;
		boolean flipy = (flip & 0x02) == 0x02;
		
		double sin = Math.sin(rot);
		double cos = Math.cos(rot);

		int tw = i.getWidth() / tileWidth;
		int xt = tileId % tw;
		int yt = tileId / tw;
		int tileOffset = xt * tileWidth + yt * tileWidth * i.getWidth();

		int fromX, fromY, toX, toY, x2, y2;
		for (int y = 0; y < tileWidth; y++) {
			int ys = y;
			
			for (int x = 0; x < tileWidth; x++) {
				int xs = x;

				toX = (tileWidth / 2) - xs;
				toY = (tileWidth / 2) - ys;
				fromX = (int) (cos * toX - sin * toY);
				fromY = (int) (sin * toX + cos * toY);
				fromX += (tileWidth / 2);
				fromY += (tileWidth / 2);
				
				x2 = x + xp;
				y2 = y + yp;

				if (flipy)
					fromY = (tileWidth - 1) - fromY;
				if (flipx)
					fromX = (tileWidth - 1) - fromX;
				
				if(x2 < 0 || y2 < 0 || x2 >= WIDTH || y2 >= HEIGHT) {
					continue;
				} else if(fromX < 0 || fromY < 0 || fromX >= tileWidth || fromY >= tileWidth) {
					continue;
				}
				
				int c = i.getPixels()[fromX + (fromY * i.getWidth()) + tileOffset];
				if (c == COLOR_KEY)
					continue;
				
				pixels[x2 + y2 * WIDTH] = c;
			}
		}
	}
	
	
	@SuppressWarnings("unused")
	private static int applyLighting(int c, int x, int y) {
		return applyLighting(c, lighting[x + y * WIDTH]);
	}
	
	private static int applyLighting(int c, int brightness) {
		if(brightness >= 255) return c;
		
		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = c & 0xff;
		
		r = r * brightness / 255;
		b = b * brightness / 255;
		g = g * brightness / 255;
		
		c = r << 16 | g << 8 | b;
		return c;
	}
	
	public static void addLight(Light l) {
		lights.add(l);
	}
	
	public static void finalizeLighting() {
		if(properties.get(LIGHTING) != TRUE) return;
		
		for(Light l : lights) {
			l.apply(lighting);
		}
		
		for(int i=0; i<WIDTH*HEIGHT; i++){
			pixels[i] = applyLighting(pixels[i], lighting[i]);
		}
	}
	
	public static BufferedImage getImage() {
		return destImage;
	}
	
	public static int getXoff() {
		return xo;
	}
	
	public static int getYoff() {
		return yo;
	}
	
	public static void setProperty(int property, int value) {
		properties.put(property, value);
	}
	
	public static int getProperty(int property) {
		return properties.get(property);
	}
	
	public static void makeJFrame(BaseGame game) {
		frame = new JFrame(WINDOW_TITLE);
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
	}
}
