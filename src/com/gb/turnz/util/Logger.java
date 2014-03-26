package com.gb.turnz.util;

public class Logger {
	public static final InfoLevel INFO = new InfoLevel("INFO", 0);
	public static final InfoLevel WARNING = new InfoLevel("WARNING", 1);
	public static final InfoLevel SEVERE = new InfoLevel("SEVERE", 2);
	public static final InfoLevel ALL = new InfoLevel("ALL", Integer.MAX_VALUE);
	public static final InfoLevel NONE = new InfoLevel("NONE", Integer.MIN_VALUE);

	@FunctionalInterface
	private interface Log<A, B> {
		public abstract void log(A a, B b);
	}
	
	InfoLevel min = ALL;
	
	public Logger() {
		
	}
	
	public Logger(InfoLevel min) {
		this.min = min;
	}
	
	public Logger(InfoLevel max, InfoLevel min) {
		this.min = min;
	}

	Log<String, InfoLevel> logger = (String s, InfoLevel il) -> {
		if(min.equals(ALL) || (il.getLevel() >= min.getLevel()))
			System.out.println("[" + il.getString() + "] " + s);
	};

	public void log(String s, InfoLevel l) {
		logger.log(s, l);
	}
	
	public void log(String s) {
		logger.log(s, INFO);
	}
	
	public void setLevel(InfoLevel l) {
		min = l;
	}

	public static class InfoLevel {

		private String s = "";
		private int i = 0;

		public InfoLevel(String s, int i) {
			this.s = s;
			this.i = i;
		}
		
		public String getString() {
			return s;
		}
		
		public int getLevel() {
			return i;
		}
	}
}
