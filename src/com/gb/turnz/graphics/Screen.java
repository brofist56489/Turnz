package com.gb.turnz.graphics;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import com.gb.turnz.base.Game;

public class Screen {
	public static final String WINDOW_TITLE = "Turnz";

	public static final int WIDTH = 352;
	public static final int HEIGHT = 384;
	public static final int SCALE = 2;

	public static final int TRUE = 0x01;
	public static final int FALSE = 0x00;

	public static final int LIGHTING = 0x100;
	public static final int TRANSPARENT_COLOR = 0x200;

	private static JFrame frame;

	private static HashMap<Integer, Integer> properties;

	private static List<Light> lights;

	private static int xOff = 0;
	private static int yOff = 0;

	private static int[] pixels;
	private static int[] lighting;
	private static BufferedImage destImage;

	private static int mOff = 0;

	static {
		properties = new HashMap<Integer, Integer>();
		initProperties();

		destImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) destImage.getRaster().getDataBuffer()).getData();

		lights = new ArrayList<Light>();
		lighting = new int[WIDTH * HEIGHT];
		ImageManager.addImage("/textures/Icon.png", "icon");
		ImageManager.addImage("/textures/pickleMouse.png", "pickleMouse");
	}

	private static void initProperties() {
		properties.put(LIGHTING, TRUE);
		properties.put(TRANSPARENT_COLOR, 0x7f007f);
	}

	public static void clear(int clearColor, int lightLevel) {
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = clearColor;
			lighting[i] = lightLevel;
		}
	}

	public static void fade(int fade) {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				pixels[x + y * WIDTH] = applyLighting(pixels[x + y * WIDTH], fade);
			}
		}
	}

	public static void render(Image img, int x, int y, int flip) {
		render(img.getPixels(), x, y, img.getWidth(), img.getHeight(), flip);
	}

	public static void render(Image img, int xp, int yp, double rad) {
		render(img.getPixels(), xp, yp, img.getWidth(), img.getHeight(), rad);
	}

	public static void renderScaled(Image img, int xp, int yp, double scale) {
		render(img.getScaledImage(scale), xp, yp, 0);
	}

	public static void render(int[] pixels, int x, int y, int w, int h, int flip) {
		x -= xOff;
		y -= yOff;

		boolean flipx = (flip & 0x01) == 0x01;
		boolean flipy = (flip & 0x02) == 0x02;

		int COLOR_KEY = properties.get(TRANSPARENT_COLOR);

		int ys, xs, col;
		for (int yy = 0; yy < h; yy++) {
			if (flipy)
				ys = (h - 1 - yy) + y;
			else
				ys = yy + y;
			if (ys < 0 || ys >= HEIGHT)
				continue;
			for (int xx = 0; xx < w; xx++) {
				if (flipx)
					xs = (w - 1 - xx) + x;
				else
					xs = xx + x;
				if (xs < 0 || xs >= WIDTH)
					continue;

				col = pixels[xx + yy * w];
				if (col == COLOR_KEY) {
					continue;
				}

				Screen.pixels[xs + ys * WIDTH] = col;
			}
		}
	}

	public static void renderRect(int xp, int yp, int w, int h, int color) {
		if (color == properties.get(TRANSPARENT_COLOR)) {
			return;
		}
		xp -= xOff;
		yp -= yOff;

		int xs, ys;
		for (int y = 0; y < h; y++) {
			ys = y + yp;
			if (ys < 0 || ys >= HEIGHT)
				continue;
			for (int x = 0; x < w; x++) {
				xs = x + xp;
				if (xs < 0 || xs >= WIDTH)
					continue;

				pixels[xs + ys * WIDTH] = color;
			}
		}
	}

	public static void render(int[] pixels, int xp, int yp, int w, int h, double rot) {
		xp -= xOff;
		yp -= yOff;

		float sin = (float) Math.sin(rot);
		float cos = (float) Math.cos(rot);

		int COLOR_KEY = properties.get(TRANSPARENT_COLOR);

		int x, y;
		float fromX, fromY, toX, toY;
		float x2, y2;
		for (y = 0; y < h; y++) {
			for (x = 0; x < w; x++) {

				toX = (w / 2) - x;
				toY = (h / 2) - y;
				fromX = (cos * toX) - (sin * toY);
				fromY = (sin * toX) + (cos * toY);
				fromX += (w / 2);
				fromY += (h / 2);

				x2 = fromX + xp;
				y2 = fromY + yp;
				if (x2 < 0 || y2 < 0 || x2 >= WIDTH || y2 >= HEIGHT) {
					continue;
				} else if (x < 0 || y < 0 || x >= w || y >= h) {
					continue;
				} else if (pixels[x + y * w] == COLOR_KEY) {
					continue;
				} else {
					Screen.pixels[(int) x2 + (int) y2 * WIDTH] = pixels[x + y * w];
					if (y2 + 1 >= HEIGHT)
						continue;
					Screen.pixels[(int) x2 + (int) (y2 + 1) * WIDTH] = pixels[x + y * w];
				}
			}
		}
	}

	public static void renderFromTileMap(Image i, int xp, int yp, int tileId, int tileWidth, int flip) {
		xp -= xOff;
		yp -= yOff;

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

	public static void renderColorFont(Image i, int xp, int yp, int tileId, int color) {
		xp -= xOff;
		yp -= yOff;

		int tileWidth = Font.getHeight();

		int COLOR_KEY = properties.get(TRANSPARENT_COLOR);

		int tw = i.getWidth() / 10;
		int xt = tileId % tw;
		int yt = tileId / tw;
		int tileOffset = xt * tileWidth + yt * tileWidth * i.getWidth();

		for (int y = 0; y < tileWidth; y++) {
			if ((y + yp) < 0 || (y + yp) >= HEIGHT)
				continue;
			int ys = y;

			for (int x = 0; x < tileWidth; x++) {
				if ((x + xp) < 0 || (x + xp) >= WIDTH)
					continue;
				int xs = x;

				int c = i.getPixels()[xs + (ys * i.getWidth()) + tileOffset];
				if (c == COLOR_KEY)
					continue;
				if (c == 0xffffff)
					c = color;

				pixels[(x + xp) + (y + yp) * WIDTH] = c;
			}
		}
	}

	public static void renderFromTileMap(Image i, int xp, int yp, int tileId, int tileWidth, int flip, double rot) {
		if (rot == 0) {
			renderFromTileMap(i, xp, yp, tileId, tileWidth, flip);
		}
		xp -= xOff;
		yp -= yOff;

		int COLOR_KEY = properties.get(TRANSPARENT_COLOR);

		boolean flipx = (flip & 0x01) == 0x01;
		boolean flipy = (flip & 0x02) == 0x02;

		rot *= -1;
		rot -= Math.PI / 2;

		float sin = (float) Math.sin(rot);
		float cos = (float) Math.cos(rot);

		int tw = i.getWidth() / tileWidth;
		int xt = tileId % tw;
		int yt = tileId / tw;
		int tileOffset = xt * tileWidth + yt * tileWidth * i.getWidth();

		float fromX, fromY, toX, toY, x2, y2;
		for (int y = 0; y < tileWidth; y++) {
			int ys = y;

			for (int x = 0; x < tileWidth; x++) {
				int xs = x;

				toX = (tileWidth / 2) - x;
				toY = (tileWidth / 2) - y;
				fromX = (cos * toX - sin * toY);
				fromY = (sin * toX + cos * toY);
				fromX += (tileWidth / 2);
				fromY += (tileWidth / 2);

				x2 = fromX + xp;
				y2 = fromY + yp;

				if (flipy)
					ys = (tileWidth - 1) - ys;
				if (flipx)
					xs = (tileWidth - 1) - xs;

				if (x2 < 0 || y2 < 0 || x2 >= WIDTH || y2 >= HEIGHT) {
					continue;
				} else if (xs < 0 || ys < 0 || xs >= tileWidth || ys >= tileWidth) {
					continue;
				}

				int c = i.getPixels()[xs + (ys * i.getWidth()) + tileOffset];
				if (c == COLOR_KEY)
					continue;

				pixels[(int) x2 + (int) y2 * WIDTH] = c;
				if (y2 + 1 >= HEIGHT)
					continue;
				pixels[(int) x2 + (int) (y2 + 1) * WIDTH] = c;
			}
		}
	}

	// private static int applyLighting(int c, int x, int y) {
	// return applyLighting(c, lighting[x + y * WIDTH]);
	// }

	private static int applyLighting(int color, int brightness) {
		if (brightness >= 255)
			return color;

		int r = (color >> 16) & 0xff;
		int g = (color >> 8) & 0xff;
		int b = color & 0xff;

		r = r * brightness / 255;
		b = b * brightness / 255;
		g = g * brightness / 255;

		color = r << 16 | g << 8 | b;
		return color;
	}

	public static void addLight(Light l) {
		lights.add(l);
	}

	public static void finalizeLighting() {
		if (properties.get(LIGHTING) != TRUE)
			return;

		for (Light l : lights) {
			l.apply(lighting);
		}

		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = applyLighting(pixels[i], lighting[i]);
		}
	}

	public static void setOffset(int x, int y) {
		xOff = x;
		yOff = y;
	}

	public static BufferedImage getImage() {
		return destImage;
	}

	public static int getXoff() {
		return xOff;
	}

	public static int getYoff() {
		return yOff;
	}

	public static void setProperty(int property, int value) {
		properties.put(property, value);
	}

	public static int getProperty(int property) {
		return properties.get(property);
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void makeJFrame(Game game) {
		frame = new JFrame(WINDOW_TITLE);
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (System.getProperty("os.name").equals("Windows 7")) {
			mOff = 16;
		} else if (System.getProperty("os.name").equals("Mac")) {
			mOff = 4;
		} else if (System.getProperty("os.name").contains("nux")) {
			mOff = 4;
		}
		frame.add(game);
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageManager.getImage("pickleMouse").getBufferedImage(), new Point(mOff, mOff), "null cursor"));
		frame.setIconImage(ImageManager.getImage("icon").getBufferedImage());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
