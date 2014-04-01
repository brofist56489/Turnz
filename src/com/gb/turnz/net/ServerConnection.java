package com.gb.turnz.net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import com.gb.turnz.util.Constants;

public class ServerConnection extends Connection {

	@FunctionalInterface
	public static interface ConnectionCallback {
		public abstract void call(ServerConnection conn);
	}

	private ServerSocket server;
	private Socket client;

	private ConnectionCallback setupCallback;

	public ServerConnection(ConnectionCallback setupCallback) {
		this.setupCallback = setupCallback;
	}

	protected void setupConnection() {
		try {
			server = new ServerSocket(Constants.PORT);
			server.setSoTimeout(1000000);
			client = server.accept();

			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			System.out.println("CONNECTION ESTABLISHED");

			setupCallback.call(this);
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

	public static String getExternalIp() {
		try {
			return new BufferedReader(new InputStreamReader(new URL("http://agentgatech.appspot.com").openStream())).readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "NULL";
	}
}
