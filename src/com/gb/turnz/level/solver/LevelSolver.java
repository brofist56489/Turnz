package com.gb.turnz.level.solver;

import java.awt.Point;

import com.gb.turnz.level.Blob;
import com.gb.turnz.level.World;
import com.gb.turnz.level.tile.Tile;
import com.gb.turnz.level.tile.Tile.Tiles;

public class LevelSolver {
	private World world;
	private Blob blob;
	private Point goal;
	
	public LevelSolver(World world, Blob blob) {
		this.world = world.makeCopy();
		this.blob = blob.makeCopy();
		
		for(int y=0; y<world.getHeight(); y++) {
			for(int x=0; x<world.getWidth(); x++) {
				Tile t = world.getTile(x, y);
				if(t.getId() == Tiles.FINISH.getId()) {
					goal = new Point(x, y);
					break;
				}
			}
		}
	}
	
	public CorrectTurns getPath() {
		
		return null;
	}
}
