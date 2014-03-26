package com.gb.turnz.level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.gb.turnz.graphics.Image;
import com.gb.turnz.level.tile.ConnectedTile;
import com.gb.turnz.level.tile.Tile;
import com.gb.turnz.level.tile.Tile.Tiles;

public class World {
	protected final int width = 11;
	protected final int height = 11;
	protected Tile[][] tiles;
	protected List<Blob> blobs = new ArrayList<Blob>();

	protected boolean canRotate = true;
	protected int rotation = 0;
	protected int rotateDir = -1;

	public World() {
		tiles = new Tile[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = Tile.newTile((x % 2));
			}
		}
	}
	
	public World(String world) {
		loadFromImage(new Image(world));
	}

	public World(Tile[][] t, List<Blob> b) {
		tiles = t;
		blobs = b;
	}

	public World loadFromImage(Image img) {
		if (img.getPixels() == null)
			return null;
		tiles = new Tile[width][height];
		int[] imgPixels = img.getPixels();
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int id = (imgPixels[x + y * width] >> 16) & 0xff;
				if (id == 3) {
					addBlob(new Blob(x, y, this));
					tiles[x][y] = Tile.newTile(0);
				} else {
					tiles[x][y] = Tile.newTile(id);
				}
			}
		}
		return this;
	}

	public void checkConnections() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (tiles[x][y] instanceof ConnectedTile)
					((ConnectedTile) tiles[x][y]).checkConnection(x, y);
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.newTile(4);
		return tiles[x][y];
	}

	public void setTile(int x, int y, Tiles tileType) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		tiles[x][y] = Tile.newTile(tileType);
	}

	private Tile[][] baseRotate() {
		Tile[][] newTiles = new Tile[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				newTiles[x][y] = tiles[y][x];
			}
		}
		return newTiles;
	}

	public void rotateLeft() {
		if (!canRotate)
			return;
		rotation = 0;
		rotateDir = -1;
		Tile[][] newTiles = baseRotate();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = newTiles[x][height - 1 - y];
			}
		}
		for (Blob b : blobs) {
			int tx = b.getY();
			int ty = b.getX();

			b.setX(tx);
			b.setY(height - 1 - ty);
		}
		checkConnections();
	}

	public void rotateRight() {
		if (!canRotate)
			return;
		rotation = 0;
		rotateDir = -1;
		Tile[][] newTiles = baseRotate();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = newTiles[width - 1 - x][y];
			}
		}
		for (Blob b : blobs) {
			int tx = b.getY();
			int ty = b.getX();

			b.setX(width - 1 - tx);
			b.setY(ty);
		}
		checkConnections();
	}

	/**
	 * @param dir - direction, 0 - left, 1 - right
	 */
	public void initializeRotation(int dir) {
		rotateDir = dir;
	}

	public void tick() {
		ableToRotate(true);
		for (int i = 0; i < blobs.size(); i++) {
			blobs.get(i).tick();
		}
		if (rotateDir != -1) {
			rotation += (rotateDir == 0) ? -10 : 10;
			if (rotation <= -90) {
				rotateLeft();
			}
			if (rotation >= 90) {
				rotateRight();
			}

		}
	}

	public void render() {
		double rad = Math.toRadians(rotation);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (rotation == 0) {
					tiles[x][y].render(x * Tile.WIDTH, y * Tile.HEIGHT, this);
				} else {
					int xx = (x - 5) * Tile.WIDTH;
					int yy = (y - 5) * Tile.HEIGHT;

					int xr = (int) (xx * cos - yy * sin);
					int yr = (int) (xx * sin + yy * cos);
					xr += Tile.WIDTH * 5;
					yr += Tile.HEIGHT * 5;

					tiles[x][y].render(xr, yr, this);
				}
			}
		}

		for (int i = 0; i < blobs.size(); i++) {
			blobs.get(i).render();
		}
	}

	public void addBlob(Blob b) {
		blobs.add(b);
	}

	public int getRotation() {
		return rotation;
	}

	public double getRotationInRadians() {
		return Math.toRadians(rotation);
	}

	public void ableToRotate(boolean able) {
		canRotate = able;
	}

	public boolean ableToRotate() {
		return canRotate;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public World makeCopy() {
		Tile[][] t = tiles.clone();
		List<Blob> b = new ArrayList<Blob>(blobs);

		return new World(t, b);
	}

	public static String[] fileNames(File[] files) {
		String[] names = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			names[i] = files[i].getName();
			System.out.println(names[i]);
		}
		return names;
	}
}