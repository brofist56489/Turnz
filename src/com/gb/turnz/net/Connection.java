package com.gb.turnz.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.gb.turnz.base.Game;
import com.gb.turnz.level.World;
import com.gb.turnz.menu.MainMenu;
import com.gb.turnz.util.Constants;
import com.gb.turnz.util.Logger;

public abstract class Connection implements Runnable {
	DataInputStream dis;
	DataOutputStream dos;

	@FunctionalInterface
	public static interface ConnRunCallback {
		public abstract void call(Connection conn);
	}

	protected abstract void setupConnection();

	protected boolean running = false;
	protected int port = Constants.DEFAULT_PORT; // Default is 2472

	protected ConnRunCallback callback;

	public String read() {
		try {
			return dis.readUTF();
		} catch (IOException e) {
			close();
			Game.setMenu(new MainMenu(Game.getInstance()));
			running = false;
		}
		return "";
	}

	public void write(String data) {
		try {
			dos.writeUTF(data);
		} catch (IOException e) {
			close();
			Game.setMenu(new MainMenu(Game.getInstance()));
			running = false;
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
		if (running)
			return null;
		running = true;
		Thread thr = new Thread(this, "CONNECTION");
		setCallback((conn) -> {
		});
		thr.start();
		return this;
	}

	public void setCallback(ConnRunCallback conn) {
		this.callback = conn;
	}

	public void run() {
		setupConnection();

		while (running) {
			callback.call(this);
		}

		Game.getLogger().log("CONNECTION CLOSED", Logger.WARNING);
	}

	public void sendWorld(World world) {
		write(world.stringify());
	}

	public World receiveWorld() {
		World world = new World().destringify(read());
		return world;
	}

	protected abstract void closeSockets();
}
