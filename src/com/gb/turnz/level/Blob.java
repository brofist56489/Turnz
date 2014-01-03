package com.gb.turnz.level;

import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Light;
import com.gb.turnz.graphics.Screen;

public class Blob {
	
	static {
		ImageManager.addImage("/textures/blob.png", "BLOB");
	}
	
	private int x, y;
	private int width, height;
	private World world;
	
	private Light light;
	
	public Blob(int x, int y, World world) {
		this.x = x;
		this.y = y;
		this.width = Tile.WIDTH;
		this.height = Tile.HEIGHT;
		this.world = world;
		light = new Light(x + width / 2, y + height / 2, 80, 255);
		Screen.addLight(light);
	}
	
	public void tick() {
		Tile t = world.getTile(x, y + 1);
		if(t == null) {
			return;
		}
		if(t.isSolid()) {
			return;
		}
		world.ableToRotate(false);
		setY(y + 1);
	}
	
	public void render() {
		if(world.getRotation() == 0)
			ImageManager.render("BLOB",	 x * width, y * height, 0);
		else {
			double rad = Math.toRadians(world.getRotation());
			double sin = Math.sin(rad);
			double cos = Math.cos(rad);
			
			int xx = (x - 5) * Tile.WIDTH;
			int yy = (y - 5) * Tile.HEIGHT;

			int xr = (int) (xx * cos - yy * sin);
			int yr = (int) (xx * sin + yy * cos);
			xr += Tile.WIDTH * 5;
			yr += Tile.HEIGHT * 5;
			light.moveTo(xr, yr);
			
			ImageManager.render("BLOB",	 xr, yr, world.getRotationInRadians());
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
		light.moveTo(x * width + width / 2, y * height + height / 2);
	}

	public void setY(int y) {
		this.y = y;
		light.moveTo(x * width + width / 2, y * height + height / 2);
	}
}
