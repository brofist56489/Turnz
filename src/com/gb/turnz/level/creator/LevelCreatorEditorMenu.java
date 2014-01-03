package com.gb.turnz.level.creator;

import java.awt.event.KeyEvent;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.level.Tile;
import com.gb.turnz.level.Tile.Tiles;
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
		if(Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_ESCAPE)) {
			Game.setMenu(new LevelCreatorToolMenu(this));
		}
		
		if(Game.getMouse().buttonDown(1)) {
			int tx = Game.getMouse().x() / Tile.WIDTH;
			int ty = Game.getMouse().y() / Tile.HEIGHT;
			
			Game.getWorld().setTile(tx, ty, Tiles.getById(blockId));
			Game.getWorld().checkConnections();
		}
	}
	
	public void render() {
		int width = Game.getWorld().getWidth();
		int height = Game.getWorld().getHeight();
		
		Game.getWorld().render();
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				ImageManager.render("CREATOR_RED_GRID", x * Tile.WIDTH, y * Tile.HEIGHT, 1);
			}
		}
	}
}
