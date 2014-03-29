package com.gb.turnz.base;

import com.gb.turnz.graphics.Screen;

public class Program {
	public static void main(String[] args) {
		 Game game = new Game();
		 Screen.makeJFrame(game);
		 game.start();

//		Scanner input = new Scanner(System.in);
//
//		System.out.println("Client or server?");
//		String type = input.nextLine();
//
//		Connection connection;
//
//		if (type.startsWith("c")) {
//			System.out.println("Ip: ");
//			String ip = input.nextLine();
//			connection = new ClientConnection(ip);
//			while (true) {
//				System.out.println("Message: ");
//				String msg = input.nextLine();
//				connection.write(msg);
//				if (msg.equals("close")) {
//					connection.close();
//				}
//				System.out.println(connection.read());
//			}
//		} else if (type.startsWith("s")) {
//			System.out.println(ServerConnection.getExternalIp());
//			connection = new ServerConnection();
//			while (true) {
//				String recemsg = connection.read();
//				if (recemsg.equals("close")) {
//					connection.close();
//				}
//				System.out.println(recemsg);
//				System.out.println("Message: ");
//				String msg = input.nextLine();
//				connection.write(msg);
//				if (msg.equals("close")) {
//					connection.close();
//					break;
//				}
//			}
//		}
//		input.close();
	}
}
