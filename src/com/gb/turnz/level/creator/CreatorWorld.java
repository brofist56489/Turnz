package com.gb.turnz.level.creator;

import com.gb.turnz.level.Blob;
import com.gb.turnz.level.Tile;
import com.gb.turnz.level.World;

public class CreatorWorld extends World {
	
	public CreatorWorld() {
		tiles = new Tile[width][height];
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				tiles[x][y] = Tile.newTile(0);
			}
		}
	}
	
	public void removeBlobAt(int x, int y) {
		for(int i=0; i<blobs.size(); i++) {
			Blob b = blobs.get(i);
			if(b.getX() == x && b.getY() == y) {
				blobs.remove(i);
				break;
			}
		}
	}
	
	public Blob getBlobAt(int x, int y) {
		for(int i=0; i<blobs.size(); i++) {
			Blob b = blobs.get(i);
			if(b.getX() == x && b.getY() == y) {
				return b;
			}
		}
		return null;
	}
	
	public void addBlobAt(int x, int y) {
		addBlob(new Blob(x, y, this));
	}
}
