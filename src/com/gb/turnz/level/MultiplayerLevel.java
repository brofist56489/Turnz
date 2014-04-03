package com.gb.turnz.level;

import java.util.HashMap;

import com.gb.turnz.base.Game;
import com.gb.turnz.menu.CalcScoreMenu;
import com.gb.turnz.menu.MainMenu;
import com.gb.turnz.menu.MultiplayerScoreMenu;
import com.gb.turnz.net.Connection;
import com.gb.turnz.net.ServerConnection;

public class MultiplayerLevel extends Level {
	
	private Connection connection;
	
	private HashMap<String, Integer> scores = new HashMap<String, Integer>();
	private boolean recvClientScore = false;
	
	public MultiplayerLevel() {
		super();
		recvClientScore = false;
	}
	
	public void win() {
		if(connection instanceof ServerConnection) {
			scores.put(System.getProperty("user.name"), getScore());
			if(recvClientScore) {
				Game.setMenu(new CalcScoreMenu(new MainMenu(Game.getInstance()), scores));
			}
		} else {
			String data = "score~" + System.getProperty("user.name") + "~" + getScore();
			connection.write(data);
			connection.setCallback((conn) -> {
				String scores = conn.read();
				if(scores != null && !scores.equals("") && scores.startsWith("results")) {
					HashMap<String, Integer> scrs = new HashMap<String, Integer>();
					
					String[] d = scores.split("~");
					String topPlayer = d[1];
					
					for(int i=2; i < d.length; i+=2) {
						String name = d[i];
						int score = Integer.parseInt(d[i+1]);
						scrs.put(name, score);
					}
					
					conn.setCallback((conne) -> {});					
					Game.setMenu(new MultiplayerScoreMenu(Game.getInstance(), scrs, topPlayer));
				}
			});
		}
	}
	
	public void clearScores() {
		scores = new HashMap<String, Integer>();
	}
	
	public void setOtherScore(String name, int score) {
		recvClientScore = true;
		connection.setCallback((conn) -> {});
		scores.put(name, score);
	}
	
	public void setConnection(Connection c) {
		connection = c;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	//((MultiplayerLevel) Game.getLevel()).setConnection(new ServerConnection());
}
