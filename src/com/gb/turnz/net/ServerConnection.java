package com.gb.turnz.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection extends Connection {

	private ServerSocket server;
	private Socket client;
	
	public ServerConnection() {
		setupConnection();
	}
	
	protected void setupConnection() {
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(100000);
			client = server.accept();
			
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void closeSockets() {
		try {
			server.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
