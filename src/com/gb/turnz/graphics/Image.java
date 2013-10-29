package com.gb.turnz.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	private BufferedImage loadedImage;
	private int[] pixels;
	
	private int width, height;
	
	public Image(String path) {
		try {
			loadedImage = ImageIO.read(Image.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		width = loadedImage.getWidth();
		height = loadedImage.getHeight();
		
		pixels = loadedImage.getRGB(0, 0, width, height, null, 0, width);
		pixels = new int[100];
		for(int i = 0; i < 100; i++) {
			pixels[i] = 0xff;
		}
		width = 10;
		height = 10;
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
}
