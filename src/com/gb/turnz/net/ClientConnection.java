package com.gb.turnz.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Connection {

	private Socket server;

	private String ip;

	private int port;

	public ClientConnection(String ipwp) {
		if (ipwp.contains(":")) {
			String[] adds = ipwp.split(":");
			ip = adds[0];
			port = Integer.parseInt(adds[1]);
		} else {
			ip = ipwp;
			port = Integer.parseInt("2472");
		}
		setupConnection();
		
	}

	protected void setupConnection() {
		try {
			server = new Socket(ip, port);
		} catch (IOException e) {
			//TODO: Can't connect menu
			e.printStackTrace();
		}

		try {
			dis = new DataInputStream(server.getInputStream());
			dos = new DataOutputStream(server.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeSockets() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
