package com.gb.turnz.level;

import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Light;
import com.gb.turnz.level.tile.Tile;

public class Blob {
	
	private int x, y;
	private int width, height;
	private World world;
	
	private Light light;
	private boolean reachedEnd = false;
	
	public Blob(int x, int y, World world) {
		this.x = x;
		this.y = y;
		this.width = Tile.WIDTH;
		this.height = Tile.HEIGHT;
		this.world = world;
		this.light = new Light(x, y, 50, 255);
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
			ImageManager.renderFromTileMap("tileMap", x * width, y * height, 33, 32, 0);
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
			
			ImageManager.renderFromTileMap("tileMap", xr, yr, 33, 32, 0, -rad);
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

	public Blob makeCopy() {
		return new Blob(x, y, world);
	}
	
	public void reachedEnd(boolean f) {
		reachedEnd = f;
	}
	
	public boolean reachedEnd() {
		return reachedEnd;
	}
}
