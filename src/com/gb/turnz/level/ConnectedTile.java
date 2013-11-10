package com.gb.turnz.level;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.ImageManager;

public class ConnectedTile extends Tile {

	protected ConnectedTile(int id) {
		super(id);
	}
	
	protected ConnectedTile(Tiles tileType) {
		super(tileType);
	}

	public void render(int x, int y, World world) {
		if(world.getRotation() == 0)
			ImageManager.renderFromTileMap("tileMap", x, y, data, WIDTH, 0);
		else
			ImageManager.renderFromTileMap("tileMap", x, y, data, WIDTH, 0, Math.PI / 2 - world.getRotationInRadians());
	}

	public void checkConnection(int x, int y) {
		World world = Game.getWorld();
		boolean u = (world.getTile(x, y - 1).getId() == id);
		boolean r = (world.getTile(x + 1, y).getId() == id);
		boolean l = (world.getTile(x - 1, y).getId() == id);
		boolean d = (world.getTile(x, y + 1).getId() == id);
		
		if(u && !r && !l && !d) {
			data = 1;
		} else if(r && !l && !d && !u) {
			data = 2;
		} else if(u && r && !l && !d) {
			data = 3;
		} else if(d && !u && !r && !l) {
			data = 4;
		} else if(u && d && !r & !l) {
			data = 5;
		} else if(r && d && !l && !u) {
			data = 6;
		} else if(u && r && d && !l) {
			data = 7;
		} else if(l && !r && !u && !d) {
			data = 8;
		} else if(u && l && !r && !d) {
			data = 9;
		} else if (l && r && !u && !d) {
			data = 10;
		} else if (u && r && l && !d) {
			data = 11;
		} else if (l && d && !r && !u) {
			data = 12;
		} else if (l && u && d && !r) {
			data = 13;
		} else if (r && l && d && !u) {
			data = 14;
		} else if (r && l && u && d) {
			data = 15;
		} else data = 0;
		
		data += textId;
	}
}
