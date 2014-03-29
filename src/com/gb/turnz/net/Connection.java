package com.gb.turnz.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.gb.turnz.util.Constants;

public abstract class Connection implements Runnable {
	DataInputStream dis;
	DataOutputStream dos;
	
	protected abstract void setupConnection();
	
	protected boolean running = false;
	protected int port = Constants.DEFAULT_PORT; //Default is 2472
	
	public String read() {
		try {
			return dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void write(String data) {
		try {
			dos.writeUTF(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public DataOutputStream getDataOutputStream() {
		return dos;
	}
	
	public DataInputStream getDataInputStream() {
		return dis;
	}
	
	public int getPort() {
		return port;
	}
	
	public void close() {
		try {
			dos.close();
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeSockets();
	}
	
	public Connection start() {
		if(running)
			return null;
		running = true;
		Thread thr = new Thread(this, "CONNECTION");
		thr.start();
		return this;
	}
	
	public void run() {
		setupConnection();
		
		while(running) {
			String data = read();
		}
	}
	
	protected abstract void closeSockets();
}