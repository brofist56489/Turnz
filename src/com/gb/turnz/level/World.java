package com.gb.turnz.level;

import com.gb.turnz.base.Game;
import com.gb.turnz.level.Tile.Tiles;

public class World {
	private final int width = 11;
	private final int height = 11;
	private Tile[][] tiles;

	public World() {
		tiles = new Tile[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = new Tile((Game.getRandom().nextBoolean()) ? Tiles.AIR : Tiles.WALL);
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return null;
		return tiles[x][y];
	}

	public void setTile(int x, int y, Tiles tileType) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		tiles[x][y].setAs(tileType);
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
		Tile[][] newTiles = baseRotate();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = newTiles[x][height - 1 - y];
			}
		}
	}

	public void rotateRight() {
		Tile[][] newTiles = baseRotate();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = newTiles[width - 1 - x][y];
			}
		}
	}

	public void render() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y].render(x, y, this);
			}
		}
	}
}