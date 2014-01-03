package com.gb.turnz.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	public static enum ImageLocation {
		INTERNAL, EXTERNAL;
	}
	
	private BufferedImage loadedImage;
	private int[] pixels;

	private int width, height;

	public Image(String path) {
		this(path, ImageLocation.INTERNAL);
	}
	
	public Image(String path, ImageLocation loc) {
		try {
			if(loc == ImageLocation.INTERNAL)
				loadedImage = ImageIO.read(Image.class.getResource(path));
			else
				loadedImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		width = loadedImage.getWidth();
		height = loadedImage.getHeight();

		pixels = loadedImage.getRGB(0, 0, width, height, null, 0, width);

		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i] < 0) {
				pixels[i] += 0x1000000;
			}
		}
	}

	public Image(int[] pixels, int w, int h) {
		this.pixels = pixels;
		this.width = w;
		this.height = h;
		this.loadedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		loadedImage.setRGB(0, 0, w, h, pixels, w, 0);
	}

	public Image(int w, int h) {
		this.width = w;
		this.height = h;
		this.pixels = new int[w * h];
		this.loadedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * @return a brand new image
	 */
	public Image getScaledImage(double scale) {
		Image i = new Image((int) (width * scale), (int) (height * scale));
		for (int x = 0; x < i.width; x++) {
			for (int y = 0; y < i.height; y++) {
				i.pixels[x + y * i.width] = pixels[(int) ((int) (x / scale) + (int) (y / scale) * width)];
			}
		}
		return i;
	}

	public Image getScaledImage(double xScale, double yScale) {
		Image i = new Image((int) (width * xScale), (int) (height * yScale));
		for (int x = 0; x < i.width; x++) {
			for (int y = 0; y < i.height; y++) {
				i.pixels[x + y * i.width] = pixels[(int) ((int) (x / xScale) + (int) (y / yScale) * width)];
			}
		}
		return i;
	}

	public Image getScaledImage(int w, int h) {
		double xScale = (float) w / width;
		double yScale = (float) h / height;
		return getScaledImage(xScale, yScale);
	}

	/**
	 * @param scale
	 *            Alters the image
	 */
	public void resize(double scale) {
		width *= scale;
		height *= scale;
		int[] newPixels = new int[width * height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				newPixels[x + y * width] = pixels[(int) ((int) (x / scale) + (int) (y / scale) * (int) (width / scale))];
			}
		}
		pixels = newPixels;
	}

	public void resize(double xScale, double yScale) {
		width *= xScale;
		height *= yScale;
		int[] newPixels = new int[width * height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				newPixels[x + y * width] = pixels[(int) ((int) (x / xScale) + (int) (y / yScale) * (int) (width / xScale))];
			}
		}
		pixels = newPixels;
	}

	public void resize(int w, int h) {
		double xScale = w / (float) width;
		double yScale = h / (float) height;
		resize(xScale, yScale);
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixel(int key, int val) {
		pixels[key] = val;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BufferedImage getBufferedImage() {
		return loadedImage;
	}
}
