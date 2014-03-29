package com.gb.turnz.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.gb.turnz.graphics.Image;
import com.gb.turnz.level.World;
import com.gb.turnz.util.Constants;

public class ClientConnection extends Connection {

	private Socket server;

	private String ip;

	private int port = Constants.DEFAULT_PORT;

	public ClientConnection(String ip, int port) {
		if (port != -1) {
			this.port = port;
		}
		this.ip = ip;
	}

	protected void setupConnection() {
		try {
			server = new Socket(ip, port);
		} catch (IOException e) {
			// TODO: Can't connect menu
			System.out.println("Connection Timeout!");
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

	public World receiveWorld() {
		World world = new World();
		try {
			world.loadFromImage(new Image(ImageIO.read(dis)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return world;
	}
}
