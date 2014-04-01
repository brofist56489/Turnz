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
		Game.getLevel().setToCreatorWorld();
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
				CreatorWorld world = (CreatorWorld)Game.getLevel().getWorld();
				world.saveToFile();
			}
		});
		addObject(new MenuObject.Button(158 - 96, 250, "Import") {
			public void onClick() {
				World world = Game.getLevel().getWorld();
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
		int width = Game.getLevel().getWorld().getWidth();
		int height = Game.getLevel().getWorld().getHeight();
		
		Game.getLevel().getWorld().render();
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				ImageManager.render("CREATOR_RED_GRID", x * Tile.WIDTH, y * Tile.HEIGHT, 1);
			}
		}
		
		int yv = Screen.HEIGHT - 32;
		ImageManager.renderFromTileMap("tileMap", 0, yv, 0, 32, 0);
		ImageManager.renderFromTileMap("tileMap", 40, yv, Tiles.FINISH.getTextureId(), 32, 0);
		ImageManager.renderFromTileMap("tileMap", 80, yv, Tiles.BLOB.getTextureId(), 32, 0);
		ImageManager.renderFromTileMap("tileMap", 120, yv, Tiles.BLUE_DISK.getTextureId(), 32, 0);
		ImageManager.renderFromTileMap("tileMap", 160, yv, Tiles.RED_DISK.getTextureId(), 32, 0);
		
		Screen.fade(128);
		super.render();
	}
}
