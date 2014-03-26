package com.gb.turnz.level.creator;

import java.awt.event.KeyEvent;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.level.tile.Tile;
import com.gb.turnz.level.tile.Tile.Tiles;
import com.gb.turnz.menu.Menu;

public class LevelCreatorEditorMenu extends Menu {

	private int blockId = 0;
	
	public LevelCreatorEditorMenu(LevelCreatorToolMenu menu) {
		super(menu);
		blockId = menu.getBlockId();
	}
	
	public int getBlockId() {
		return blockId;
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
			if(blockId == 3) {
				if(world.getBlobAt(tx, ty) == null && world.getTile(tx, ty).getId() == Tiles.AIR.getId())
					world.addBlobAt(tx, ty);
			} else {
				world.setTile(tx, ty, Tiles.getById(blockId));
			}
			world.checkConnections();
		} else if(Game.getMouse().buttonDown(3)) {
			int tx = Game.getMouse().x() / Tile.WIDTH;
			int ty = Game.getMouse().y() / Tile.HEIGHT;
			if(world.getTile(tx, ty).getId() != Tiles.AIR.getId()) {
				world.setTile(tx, ty, Tiles.AIR);
			}
			if(world.getBlobAt(tx, ty) != null) {
				world.removeBlobAt(tx, ty);
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
	}
}
