package com.gb.turnz.net;

import java.util.StringJoiner;

public abstract class Packet {
	protected static String DELUMITER = "~";
	
	String recvData;
	String[] data;
	
	public Packet(String d) {
		this.recvData = d;
		data = recvData.split(DELUMITER);
		
		setId();
	}
	
	protected int id = -1;
	protected abstract void setId();
	
	public void send(Connection conn) {
		conn.write(this.toString());
	}
	
	public static class BeginPacket extends Packet {
		public BeginPacket(String d) {
			super(d);
		}
		
		public BeginPacket() {
			super("");
			
			setId();
		}
		
		public String toString() {
			return new StringJoiner(DELUMITER)
				.add(id+"")
				.toString();
		}
		
		protected void setId()
		{ id = 0; }
	}
}
