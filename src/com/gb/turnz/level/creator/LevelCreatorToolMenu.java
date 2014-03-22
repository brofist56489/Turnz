package com.gb.turnz.level.creator;

import java.awt.FileDialog;
import java.awt.event.KeyEvent;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.graphics.Image;
import com.gb.turnz.graphics.Image.ImageLocation;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.World;
import com.gb.turnz.level.tile.Tile;
import com.gb.turnz.level.tile.Tile.Tiles;
import com.gb.turnz.menu.MainMenu;
import com.gb.turnz.menu.Menu;
import com.gb.turnz.menu.MenuObject;

public class LevelCreatorToolMenu extends Menu {

	private int blockId = 0;

	public LevelCreatorToolMenu() {
		super(Game.getInstance());
		Game.setWorld(new CreatorWorld());
		init();
	}

	public LevelCreatorToolMenu(LevelCreatorEditorMenu menu) {
		super(menu);
		blockId = menu.getBlockId();
		init();
	}

	public void init() {
		addObject(new MenuObject.Button(178, 250, "Export") {
			public void onClick() {
				CreatorWorld world = (CreatorWorld)Game.getWorld();
				world.saveToFile();
			}
		});
		addObject(new MenuObject.Button(158 - 96, 250, "Import") {
			public void onClick() {
				World world = Game.getWorld();
				FileDialog fileLoader = new FileDialog(Screen.getFrame(), "Load Level", FileDialog.LOAD);
				fileLoader.setFile("*.lvl");
				fileLoader.setVisible(true);
				
				String path = fileLoader.getDirectory() + "/" + fileLoader.getFile();
				
				Image image = new Image(path, ImageLocation.EXTERNAL);
				world.loadFromImage(image);
				world.checkConnections();
			}
		});
		addObject(new MenuObject.Button(Font.getScreenCenterX("Quit"), 352 - 40, "Quit") {
			public void onClick() {
				Game.setMenu(new MainMenu(Game.getInstance()));
			}
		});
		addObject(new TileChoice(10, 10, Tiles.BLUE_WALL));
		addObject(new TileChoice(50, 10, Tiles.FINISH));
		addObject(new TileChoice(90, 10, Tiles.BLOB));
	}

	public int getBlockId() {
		return blockId;
	}
	
	public void setBlockId(int id) {
		this.blockId = id;
	}
	
	public void tick() {
		super.tick();
		
		if(Game.getKeyboard().isKeyDownOnce(KeyEvent.VK_ESCAPE)) {
			Game.setMenu(new LevelCreatorEditorMenu(this));
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
		Screen.fade(128);
		super.render();
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
			LevelCreatorToolMenu menu = (LevelCreatorToolMenu)this.menu;
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
			LevelCreatorToolMenu menu = (LevelCreatorToolMenu)this.menu;
			return menu.getBlockId() == this.tile.getId();
		}

		public void render() {
			if(isSelectedTile()) {
				Screen.renderRect(x - 2, y - 2, width + 4, height + 4, 0x00ff00);
			} else if(hovered) {
				Screen.renderRect(x - 2, y - 2, width + 4, height + 4, 0xff0000);
			}
			if(tile.getId() == 3)
				ImageManager.render("BLOB", x, y, 0);
			else
				ImageManager.renderFromTileMap("tileMap", x, y, tile.getTextureId(), 32, 0);
		}
	}
}
