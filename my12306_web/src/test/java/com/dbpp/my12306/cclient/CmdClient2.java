package com.dbpp.my12306.cclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CmdClient2 extends Thread{
	public static void main(String[] args){
		CmdClient2 client = new CmdClient2();
		client.start();
	}

	public static final int port = 6666;
	public static final String host = "192.168.1.104";

	public String userName = "mocker";
	public String password;
	public String dbName = "mocker_db";

	public String head;

	public Socket socket;
	public BufferedReader inFromServer;
	public PrintStream outToServer;
	public boolean hold;

	public CmdClient2(String userName, String password, String dbName) {
		this.userName = userName;
		this.password = password;
		this.dbName = dbName;
		generateHead();

		// test connect
		if (!testConnect()) {
			System.out.println("Fail to connect. " +
					"Please check your message or the link. Exit.");
			System.exit(0);
		}
	}

	public CmdClient2() {
		Scanner in = new Scanner(System.in);

		System.out.print("Database [" + dbName + "]:");
		String str = in.nextLine();
		if (!str.isEmpty()) this.dbName = str;

		System.out.print("Username [" + userName + "]:");
		str = in.nextLine();
		if (!str.isEmpty()) this.userName = str;

		System.out.print("The password for " + userName + ":");
		str = in.nextLine();
		if (!str.isEmpty()) this.password = str;
		generateHead();

		// test connect
		if (!testConnect()) {
			System.out.println("Fail to connect. " +
					"Please check your message or the link. Exit.");
			System.exit(0);
		}
	}

	public void generateHead() {
		this.head = userName + "," + password + "," + dbName + "$";
	}

	public void run() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		hold = true;

		while (hold) {
			socket = null;
			try {
				socket = new Socket(host, port);

				// read from server
				inFromServer = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				// sent to server
				outToServer = new PrintStream(socket.getOutputStream());

				String str;
				do {
					System.out.print(userName + "=# ");
					str = input.readLine().trim();
				} while (!check(str));

				long time = System.currentTimeMillis();

				outToServer.println(head + str);
				String ret = inFromServer.readLine();
				processResult(ret);

				time = System.currentTimeMillis() - time;
				System.out.println("completed in " + time + " ms");

				inFromServer.close();
				outToServer.close();
			} catch (Exception e) {
				System.out.println("Client error:" + e.getMessage());
//				e.printStackTrace();
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						socket = null;
						System.out.println("Client end error:" + e.getMessage());
//						e.printStackTrace();
					}
				}
			}
		}
	}

	public boolean testConnect() {
		socket = null;
		boolean res;
		try {
			socket = new Socket(host, port);

			// read from server
			inFromServer = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			// sent to server
			outToServer = new PrintStream(socket.getOutputStream());

			outToServer.println(head);

			String ret = inFromServer.readLine();
			res = "$CONNECT$".equals(ret);

			inFromServer.close();
			outToServer.close();
		} catch (Exception e) {
			System.out.println("Client error:" + e.getMessage());
			return false;
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					socket = null;
					System.out.println("Client end error:" + e.getMessage());
				}
			}
		}
		return res;
	}

	public void end() {
		hold = false;
	}

	public boolean check(String str) {
		if ("".equals(str)) return false;
		if(str.charAt(0) != '\\'
				&& str.charAt(str.length()-1) != ';') {
			System.out.println("Syntax error\n");
			return false;
		}
		return true;
	}

	public void processResult(String ret) throws IOException, InterruptedException {
		if (ret == null) {
			System.out.println("No response");
			return;
		}
		switch (ret) {
			case "\\q":
				Thread.sleep(500);
				System.out.println("bye");
				outToServer.close();
				inFromServer.close();
				System.exit(0);
			case "$CONNECT$":
				System.out.println("connected to " + dbName + " use " + userName);
				return;
			case "$SELECT$":
				String str = inFromServer.readLine();
				if (str.isEmpty()) {
					// 0 col table
					System.out.println("This table has 0 column.");
					return;
				}
				int n = Integer.parseInt(str);
				var sbd = new StringBuilder();
				sbd.append(inFromServer.readLine()).append("\n-----\n");
				for (int i = 0; i < n; i++) {
					sbd.append(inFromServer.readLine()).append("\n");
				}
				System.out.println(sbd.toString());
				return;
			case "$CHANGEDB$":
				dbName = inFromServer.readLine();
				generateHead();
				System.out.println("Now you are connect to " + dbName);
				return;
//			case "$CHANGEUSER_PW$":
//				var in = new Scanner(System.in);
//				String str;
//				do {
//					System.out.print("The password for " + userName + ":");
//					str = in.next();
//				} while (str.isEmpty());
//				this.password = str;
//				generateHead();
//
//				outToServer.println("\\pw ");
//				outToServer.println(this.password);
//			case "$CHANGEUSER$":

			default:
				System.out.println(ret);
		}
	}
}
