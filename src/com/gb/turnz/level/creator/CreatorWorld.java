package com.gb.turnz.level.creator;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.gb.turnz.graphics.Screen;
import com.gb.turnz.level.World;
import com.gb.turnz.level.tile.Tile;

public class CreatorWorld extends World {
	
	public CreatorWorld() {
		tiles = new Tile[width][height];
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				tiles[x][y] = Tile.newTile(0);
			}
		}
	}
	
	public void saveToFile() {
		int[] pixels = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if(getBlobAt(x, y) != null) {
					pixels[x + y * width] = new Color(3, 0, 0).getRGB();
				} else pixels[x + y * width] = new Color(getTile(x, y).getId(), 0, 0).getRGB();
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
}
