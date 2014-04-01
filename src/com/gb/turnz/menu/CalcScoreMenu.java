package com.gb.turnz.menu;

import java.util.HashMap;

import com.gb.turnz.base.Game;
import com.gb.turnz.level.MultiplayerLevel;

public class CalcScoreMenu extends Menu {
	
	private HashMap<String, Integer> scores = null;
	public CalcScoreMenu(Menu menu, HashMap<String, Integer> scores) {
		super(menu);
		this.scores = scores;
		init();
	}
	
	private void init() {
		
		new Thread(new Runnable() {
			public void run() {
				((MultiplayerLevel) Game.getLevel()).getConnection().write(getBest());
			}
		}).start();
	}
	
	public void tick() {
		super.tick();
		Game.setMenu(new MultiplayerScoreMenu(Game.getInstance(), scores, getBest().split("~")[1]));
	}
	
	private String getBest() {
		String bestPlayer = "";
		int highestVal = Integer.MIN_VALUE;
		for(String name : scores.keySet()) {
			int score = scores.get(name);
			if(score > highestVal) {
				bestPlayer = name;
				highestVal = score;
			}
		}
		
		String res = "results~" + bestPlayer + "~";
		for(String name : scores.keySet()) {
			res += name + "~" + scores.get(name) + "~";
		}
		res = res.substring(0, res.length() - 1);
		
		return res;
	}
}
