package com.gb.turnz.level.creator;

import java.awt.event.KeyEvent;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.tile.Tile;
import com.gb.turnz.level.tile.Tile.Tiles;
import com.gb.turnz.menu.Menu;
import com.gb.turnz.menu.MenuObject;

public class LevelCreatorEditorMenu extends Menu {

	private int blockId = 0;
	
	public LevelCreatorEditorMenu(LevelCreatorToolMenu menu) {
		super(menu);
		blockId = menu.getBlockId();
		showBorder = false;
		
		init();
	}
	
	public int getBlockId() {
		return blockId;
	}
	
	private void init() {
		int yv = Screen.HEIGHT - 32;
		addObject(new TileChoice(0, yv, Tiles.BLUE_WALL));
		addObject(new TileChoice(40, yv, Tiles.FINISH));
		addObject(new TileChoice(80, yv, Tiles.BLOB));
		addObject(new TileChoice(120, yv, Tiles.BLUE_DISK));
		addObject(new TileChoice(160, yv, Tiles.RED_DISK));
	}
	
	public void tick() {
		super.tick();
		CreatorWorld world = ((CreatorWorld)Game.getLevel().getWorld());
		if(Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_ESCAPE)) {
			Game.setMenu(new LevelCreatorToolMenu(this));
		}
		
		if(Game.getMouse().buttonDown(1)) {
			int tx = Game.getMouse().x() / Tile.WIDTH;
			int ty = Game.getMouse().y() / Tile.HEIGHT;
			world.setTile(tx, ty, Tiles.getById(blockId));
			world.checkConnections();
		} else if(Game.getMouse().buttonDown(3)) {
			int tx = Game.getMouse().x() / Tile.WIDTH;
			int ty = Game.getMouse().y() / Tile.HEIGHT;
			if(world.getTile(tx, ty).getId() != Tiles.AIR.getId()) {
				world.setTile(tx, ty, Tiles.AIR);
			}
			world.checkConnections();
		}
	}
	
	public void render() {
		int width = Game.getLevel().getWorld().getWidth();
		int height = Game.getLevel().getWorld().getHeight();
		
		Game.getLevel().getWorld().render();
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				ImageManager.render("CREATOR_RED_GRID", x * Tile.WIDTH, y * Tile.HEIGHT, 1);
			}
		}
		
		super.render();
	}
	
	public void setBlockId(int block) {
		this.blockId = block;
	}
	
	private static class TileChoice extends MenuObject {

		private Tiles tile;
		private boolean hovered;
		public TileChoice(int x, int y, Tiles t) {
			super(x, y, 32, 32);
			this.tile = t;
			hovered = false;
		}
		
		public void onClick() {
			LevelCreatorEditorMenu menu = (LevelCreatorEditorMenu)this.menu;
			menu.setBlockId(this.tile.getId());
		}

		public void onHover() {
			if(!isSelectedTile()) {
				hovered = true;
			}
		}

		public void onNotHover() {
			hovered = false;
		}
		
		private boolean isSelectedTile() {
			LevelCreatorEditorMenu menu = (LevelCreatorEditorMenu)this.menu;
			return menu.getBlockId() == this.tile.getId();
		}

		public void render() {
			if(isSelectedTile()) {
				Screen.renderRect(x - 2, y - 2, width + 4, height + 4, 0x00ff00);
			} else if(hovered) {
				Screen.renderRect(x - 2, y - 2, width + 4, height + 4, 0xff0000);
			}
			ImageManager.renderFromTileMap("tileMap", x, y, tile.getTextureId(), 32, 0);
		}
	}
}
