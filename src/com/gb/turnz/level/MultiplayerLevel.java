package com.gb.turnz.level;

import com.gb.turnz.net.Connection;
import com.gb.turnz.net.ServerConnection;

public class MultiplayerLevel extends Level {
	
	private Connection connection;
	
	public MultiplayerLevel() {
		
		
		
		super();
	}
	
	public void win() {
		if(connection instanceof ServerConnection) {
			
		} else {
			
		}
	}
	
	public void setConnection(Connection c) {
		connection = c;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	//((MultiplayerLevel) Game.getLevel()).setConnection(new ServerConnection());
}
