package com.dbpp.my12306.cserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HandlerThread implements Runnable {
	private Server server;
	private Socket socket;
	private BufferedReader inFromClient;
	private PrintStream outToClient;

	public HandlerThread(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	public void run() {
		try {
			// read from client
			inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String inStr = inFromClient.readLine();
			outToClient = new PrintStream(socket.getOutputStream());

			process(inStr);

			outToClient.close();
			inFromClient.close();
		} catch (Exception e) {
			System.out.println("Server error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e) {
					socket = null;
					System.out.println("Server end error:" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * for each process, if execute fail or execute already completed, return true,
	 * then whole process will end and return;
	 * @param inStr the command string.
	 */
	public void process(String inStr) {
		/* parse */
		String[] strings = inStr.split("\\$");

		/* deal it */
		if (processHead(strings[0])) { return; }
		if (strings.length == 1) {
			// test connect
			outToClient.println("$CONNECT$");
			return;
		}

//		processBody(strings[1]);
	}

	public boolean processHead(String head) {
//		// username,pw,dbname$
//		String[] values = head.split(",");
//		if (values.length != 3 || values[0].length() != 1) {
//			outToClient.println("Error: connect failed, " + head);
//			return true;
//		}
//		Object[] ret = server.getConnHandler()
//				.check(values[0].charAt(0), values[1], values[2]);
//		if (ret == null) {
//			outToClient.println("Error: no user named " + values[0]);
//			return true;
//		}

		return false;
	}

//	public boolean processSlash(String str) {
//		switch (str) {
//			case "\\q":     // end this client
//				outToClient.println("\\q");
//				dbEngine.modeMap.put(role.name, 0);
//				return true;
//			case "\\lu":    // list users
//				outToClient.println(dbEngine.listUser());
//				return true;
//			case "\\l":    // list databases
//				outToClient.println(dbEngine.listDb());
//				return true;
//			case "\\dt" :   // list tables in this database
//				outToClient.println(dbEngine.listTable(dbModel));
//				return true;
//			case "\\lp":    // list the privilege of this user
//				outToClient.println("user " + role.name + " has privilege: " +
//						role.allPrivilege());
//				return true;
//		}
//		if (str.charAt(0) == '\\') {
//			String[] strings = str.split(" ");
//			switch (strings[0]) {
//				case "\\c":
//					if (strings.length == 3 && strings[1].equals("-")) {
//						// change user
////						newUser = dbEngine.userMap.get(strings[2]);
////						if (newUser == null) {
////							outToClient.println("Error: no user named " + strings[2]);
////							return true;
////						}
////						user = newUser;
////						outToClient.println("$CHANGEUSER_PW$");
////						outToClient.println(strings[2]);
//						return false;
//					} else if (strings.length == 2) {
//						// change database
//						if (dbEngine.dbMap.get(strings[1]) == null) {
//							outToClient.println("Error: no database named " + strings[1]);
//							return true;
//						}
//						outToClient.println("$CHANGEDB$");
//						outToClient.println(strings[1]);
//						return false;
//					} else if (strings.length == 1) {
//						outToClient.println("$CONNECT$");
//						return true;
//					}
//					outToClient.println("Error: cmd not valid.");
//					return true;
//				case "\\lp":
//					if (!(strings.length == 3 && strings[1].equals("-"))) {
//						outToClient.println("Error: cmd not valid.");
//						return true;
//					}
//					User target = dbEngine.userMap.get(strings[2]);
//					if (target == null) {
//						outToClient.println("Operation error: no such user named " +
//								strings[2]);
//						return true;
//					}
//					outToClient.println("user " + strings[2] + " has privilege: " +
//							target.allPrivilege());
//					return true;
//				case "\\dt":
//					if (!(strings.length == 3 && strings[1].equals("-"))) {
//						outToClient.println("Error: cmd not valid.");
//						return true;
//					}
//					DbModel dbModel = dbEngine.dbMap.get(strings[2]);
//					if (dbModel == null) {
//						outToClient.println("Operation error: no such database named " +
//								strings[2]);
//						return true;
//					}
//					outToClient.println(dbEngine.listTable(dbModel));
//					return true;
//				default:
//					outToClient.println("Error: cmd not valid.");
//					return true;
//			}
//		}
//		return false;
//	}
//
//	public boolean processBody(String body) {
//		if (body.isEmpty()) {
//			outToClient.println("$CONNECT$");
//			return true;
//		}
//
//		/* check transaction cmd */
//		int cnt;
//		switch(body) {
//			case "begin;":
//			case "BEGIN;":
//				if (dbEngine.modeMap.get(role.name) != 0) {
//					outToClient.println("Operation error: has begun a transaction.");
//					return true;
//				}
//				outToClient.println("begin transaction");
//				dbEngine.modeMap.put(role.name, 1);
//				return true;
//			case "rollback;":
//			case "ROLLBACK;":
//				if (dbEngine.modeMap.get(role.name) != 1) {
//					outToClient.println("Operation error: has not begun a transaction.");
//					return true;
//				}
//				ArrayList<SqlCmd> list = dbEngine.cmdListMap.get(role.name);
//				cnt = list.size();
//				list.clear();
//				dbEngine.modeMap.put(role.name, 0);
//				outToClient.println("rollback completed. cancel " + cnt + " cmd.");
//				return true;
//			case "commit;":
//			case "COMMIT;":
//				if (dbEngine.modeMap.get(role.name) != 1) {
//					outToClient.println("Operation error: has not begun a transaction.");
//					return true;
//				}
//				Object[] res = new Object[3];
//				if (dbEngine.commit(role, res)) {
//					outToClient.println("commit completed. total: " + res[2]);
//				} else {
//					outToClient.println("commit and run failed. rollback all. fail at cmd " +
//							res[1] + ": " + res[0]);
//				}
//				dbEngine.modeMap.put(role.name, 0);
//				return true;
//		}
//
//		/* check other cmd */
//
//		SqlCmd cmd = dbEngine.parser.parse(role, dbModel, body);
//
//		if (cmd == null) return false;
//		if (dbEngine.modeMap.get(role.name) == 1
//				&& (cmd.cmdType == CmdType.INSERT
//				|| cmd.cmdType == CmdType.DELETE)) {
//			// in transaction
//			dbEngine.cmdListMap.get(role.name).add(cmd);
//			outToClient.println("add into transaction list");
//			return true;
//		}
//		cmd.run();
//		switch (cmd.cmdType) {
//			case SELECT:
//				Table t = (Table)cmd.res[1];
//				outToClient.println("$SELECT$");
//				outToClient.println(t.tempDataMap.size());
//				for (String s : t.attrName) {
//					outToClient.print(s + " ");
//				}
//				outToClient.println();
//				outToClient.print(t.dataToStringTemp());
//				return true;
//			case CREATE:
//			case INSERT:
//			case DELETE:
//			case DROP:
//			case PRIVILEGE:
//				outToClient.println(cmd.res[0]);
//				return true;
//			default:
//				outToClient.println("completed");
//				return true;
//		}
//	}
}
