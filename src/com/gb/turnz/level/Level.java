package com.gb.turnz.level;

import java.util.HashMap;

import com.gb.turnz.base.Game;
import com.gb.turnz.level.creator.CreatorWorld;
import com.gb.turnz.menu.ScoreMenu;

public class Level {
	
	private static String[] levels = new String[] {
		"/worlds/world1.png",
		"/worlds/world2.png",
		"/worlds/world3.png",
		"/worlds/world4.png",
		"/worlds/explained.lvl",
		"/worlds/compare.lvl"
	};
	
	private int score;
	
	private World world;
	
	public Level() {
	}
	
	public void tick() {
		if(!(world == null))
			world.tick();
		boolean won = true;
		for (int i = 0; i < world.blobs.size(); i++) {
			if (!world.blobs.get(i).reachedEnd())
				won = false;
		}
		if (won) {
			win();
		}
	}
	
	public void win() {
		HashMap<String, Integer> scores = new HashMap<String, Integer>();
		scores.put(System.getProperty("user.name"), score);
		Game.setMenu(new ScoreMenu(Game.getInstance(), scores));
	}
	
	public int getScore() {
		return score;
	}
	
	public void changeScore(int s) {
		score += s;
	}
	
	public void setScore(int s) {
		score = s;
	}
	
	public void setWorld(String world) {
		this.world = new World(world);
		this.world.checkConnections();
	}
	
	public void setWorld(World world) {
		this.world = world;
	}

	public void setToCreatorWorld() {
		world = new CreatorWorld();
	}
	
	public World getWorld() {
		return world;
	}
	
	public static String[] getLevels() {
		return levels;
	}
}
