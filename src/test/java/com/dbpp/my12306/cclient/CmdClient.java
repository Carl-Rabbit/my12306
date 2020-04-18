package com.dbpp.my12306.cclient;

import com.dbpp.my12306.cli.Commander;

import java.util.Scanner;

/**
 * The command client terminal using cli.
 */
public class CmdClient {
	public static void main(String[] args) {

		if (args.length > 0) {
			new Commander("CmdClient").execute(args);
			return;
		}

		Commander cmd = new Commander("");
		String str;
		Scanner in = new Scanner(System.in);
		System.out.println("my12306 Terminal V0.0.1");

		while(true) {
			System.out.print("> ");
			str = in.nextLine();
			if (str.isBlank()) continue;
			if ("quit".equals(str)) break;
			cmd.execute(str.split(" "));
		}

		System.out.println("Bye!");
	}
}
