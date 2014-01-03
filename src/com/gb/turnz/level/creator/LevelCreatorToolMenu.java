package com.gb.turnz.level.creator;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Image;
import com.gb.turnz.graphics.Image.ImageLocation;
import com.gb.turnz.graphics.ImageManager;
import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.Tile;
import com.gb.turnz.level.World;
import com.gb.turnz.menu.MainMenu;
import com.gb.turnz.menu.Menu;
import com.gb.turnz.menu.MenuObject;

public class LevelCreatorToolMenu extends Menu {

	private int blockId = 1;

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
		addObject(new MenuObject.Button(130, 10, "Export") {
			public void onClick() {
				World world = Game.getWorld();
				int width = world.getWidth();
				int height = world.getHeight();
				int[] pixels = new int[width * height];

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						pixels[x + y * width] = new Color(world.getTile(x, y).getId(), 0, 0).getRGB();
					}
				}

				BufferedImage i = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				i.setRGB(0, 0, width, height, pixels, 0, width);

				File f = null;
				FileDialog fileSaver = new FileDialog(Screen.getFrame(), "Save PNG", FileDialog.SAVE);
				fileSaver.setFile("*.lvl");
				fileSaver.setVisible(true);
				
				f = new File(fileSaver.getDirectory() + "/" + fileSaver.getFile());
				
				if(!f.getName().endsWith(".lvl")) {
					if(f.getName().contains(".")) {
						f = new File(fileSaver.getDirectory() + "/" + f.getName().substring(0, f.getName().length() - 3));
					}
					f = new File(f.getPath().concat(".png"));
				}

				try {
					ImageIO.write(i, "PNG", f);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Screen.getFrame(), "There was an error exporting your world!");
				}
			}
		});
		addObject(new MenuObject.Button(10, 10, "Import") {
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
		addObject(new MenuObject.Button(250, 10, "Quit") {
			public void onClick() {
				Game.setMenu(new MainMenu(Game.getInstance()));
			}
		});
	}

	public int getBlockId() {
		return blockId;
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
}
