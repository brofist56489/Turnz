package com.gb.turnz.level.tile;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.level.World;

public class ConnectedTile extends Tile {

	protected ConnectedTile(int id) {
		super(id);
	}
	
	protected ConnectedTile(Tiles tileType) {
		super(tileType);
	}

	public void render(int x, int y, World world) {
		if(world.getRotation() == 0) {
			ImageManager.renderFromTileMap("tileMap", x, y, data1, WIDTH, 0);
		} else {
			ImageManager.renderFromTileMap("tileMap", x, y, data1, WIDTH, 0, Math.PI / 2 - world.getRotationInRadians());
		}
		
		if(data2 != 0)
			renderInnerCorners(x, y, world);
	}
	
	private void renderInnerCorners(int x, int y, World world) {
		int cornerId = 32;
		
		boolean ul = (data2 & 0x0001) == 0x0001;
		boolean ur = (data2 & 0x0010) == 0x0010;
		boolean dl = (data2 & 0x0100) == 0x0100;
		boolean dr = (data2 & 0x1000) == 0x1000;
		
		if(world.getRotation() == 0) {
			if(ul) ImageManager.renderFromTileMap("tileMap", x, y, cornerId, WIDTH, 0);
			if(ur) ImageManager.renderFromTileMap("tileMap", x, y, cornerId, WIDTH, 1);
			if(dl) ImageManager.renderFromTileMap("tileMap", x, y, cornerId, WIDTH, 2);
			if(dr) ImageManager.renderFromTileMap("tileMap", x, y, cornerId, WIDTH, 3);
		} else {
			if(ul) ImageManager.renderFromTileMap("tileMap", x, y, cornerId, WIDTH, 0, Math.PI / 2 - world.getRotationInRadians());
			if(ul) ImageManager.renderFromTileMap("tileMap", x, y, cornerId, WIDTH, 1, Math.PI / 2 - world.getRotationInRadians());
			if(ul) ImageManager.renderFromTileMap("tileMap", x, y, cornerId, WIDTH, 2, Math.PI / 2 - world.getRotationInRadians());
			if(ul) ImageManager.renderFromTileMap("tileMap", x, y, cornerId, WIDTH, 3, Math.PI / 2 - world.getRotationInRadians());
		}
	}

	public void checkConnection(int x, int y) {
		World world = Game.getLevel().getWorld();
		boolean u = (world.getTile(x, y - 1).getId() == id);
		boolean r = (world.getTile(x + 1, y).getId() == id);
		boolean l = (world.getTile(x - 1, y).getId() == id);
		boolean d = (world.getTile(x, y + 1).getId() == id);

		boolean ul = (world.getTile(x - 1, y - 1).getId() == id);
		boolean ur = (world.getTile(x + 1, y - 1).getId() == id);
		boolean dl = (world.getTile(x - 1, y + 1).getId() == id);
		boolean dr = (world.getTile(x + 1, y + 1).getId() == id);
		
		if(u && !r && !l && !d) {
			data1 = 1;
		} else if(r && !l && !d && !u) {
			data1 = 2;
		} else if(u && r && !l && !d) {
			data1 = 3;
		} else if(d && !u && !r && !l) {
			data1 = 4;
		} else if(u && d && !r & !l) {
			data1 = 5;
		} else if(r && d && !l && !u) {
			data1 = 6;
		} else if(u && r && d && !l) {
			data1 = 7;
		} else if(l && !r && !u && !d) {
			data1 = 8;
		} else if(u && l && !r && !d) {
			data1 = 9;
		} else if (l && r && !u && !d) {
			data1 = 10;
		} else if (u && r && l && !d) {
			data1 = 11;
		} else if (l && d && !r && !u) {
			data1 = 12;
		} else if (l && u && d && !r) {
			data1 = 13;
		} else if (r && l && d && !u) {
			data1 = 14;
		} else if (r && l && u && d) {
			data1 = 15;
		} else data1 = 0;
		
		data1 += textId;
		
		data2 = 0x0000;
		if(u && l && !ul) {
			data2 |= 0x0001;
		}
		if(u && r && !ur) {
			data2 |= 0x0010;
		}
		if(d && l && !dl) {
			data2 |= 0x0100;
		}
		if(d && r && !dr) {
			data2 |= 0x1000;
		}
	}
}
