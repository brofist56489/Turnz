package com.gb.turnz.menu;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Font;
import com.gb.turnz.level.MultiplayerLevel;
import com.gb.turnz.menu.MenuObject.ButtonGroup;
import com.gb.turnz.net.ClientConnection;

public class JoinMenu extends Menu {

	public JoinMenu(Game game) {
		super(game);
		init();
	}

	public JoinMenu(Menu menu) {
		super(menu);
		init();
	}
	
	private void init() {
		addObject(new MenuObject.Text("IP: ", 10, 50));
		addObject(new MenuObject.Text("Port: ", 10, 100));
		
		MenuObject.TextBox ip, port;
		ButtonGroup group = new ButtonGroup();
		ip = new MenuObject.TextBox(Font.getWidth() * 4 + 20, 50, 15);
		port = new MenuObject.TextBox(Font.getWidth() * 6 + 20, 100, 6);
		
		addObject(new MenuObject.Button(Font.getScreenCenterX("Connect"), 300, "Connect") {
			public void onClick() {
				((MultiplayerLevel) Game.getLevel()).setConnection(new ClientConnection(ip.getText(), Integer.parseInt(port.getText())).start());
				
			}
		});
		
		group.add(ip);
		group.add(port);
		addObject(ip);
		addObject(port);
	}

}
