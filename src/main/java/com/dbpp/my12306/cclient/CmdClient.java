package com.dbpp.my12306.cclient;

import com.beust.jcommander.JCommander;
import com.dbpp.my12306.cli.user.CmdCntuser;
import com.dbpp.my12306.cli.user.CmdGetuser;
import com.dbpp.my12306.cli.CmdMain;
import com.dbpp.my12306.cli.train.CmdPick;
import com.dbpp.my12306.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * The command client terminal using cli.
 * It will launch up when app starts.
 * If you don't want to start cmd window, or you are running it in non-graphical system,
 * please run the application with args '-nocmd' or '-incmd'
 */

@Component
public class CmdClient implements CommandLineRunner {

	private JCommander jc;

	private CmdMain cm;
	private CmdPick cmdPick;
	private CmdGetuser cmdGetuser;
	private CmdCntuser cmdCntuser;

	@Autowired
	private UserService userService;

	private final String banner = "my12306 Terminal V0.0.1";
	private boolean debug = false;
	public CmdFrame cmdFrame;

	public String result;


	public CmdClient() {
		setJc();
	}

	@Override
	public void run(String[] args) {
		// default: swing console
		if (args.length == 0) {
			startOuterConsole();
		}

		switch (args[0]) {
			case "-nocmd":
				return;
			case "-incmd":
				startInnerConsole();
			case "-outcmd":
			default:
				startOuterConsole();
		}
	}

	/**
	 * Start the command line listener at a JFrame window.
	 * This window can be restart and end by input 'start'/'end' in system console
	 */
	public void startOuterConsole() {
		// without this, the program can't start up.
		System.setProperty("java.awt.headless", "false");

		cmdFrame = new CmdFrame(banner, this);
		cmdFrame.start();

		String str;
		Scanner in = new Scanner(System.in);

		System.out.println("\nOuter shell start.");

		outer:
		while (true) {
			str = in.nextLine();
			switch (str) {
				case "start":
					if (cmdFrame == null) {
						cmdFrame = new CmdFrame(banner, this);
						cmdFrame.start();
						System.out.println("Shell started.");
					} else {
						System.out.println("Shell has already started.");
					}
					break;
				case "close":
					if (cmdFrame != null) {
						cmdFrame.end();
						cmdFrame = null;
						System.out.println("Shell closed.");
					} else {
						System.out.println("Shell has already closed.");
					}
					break;
				case "stop listening":
					System.out.println("End listening.");
					break outer;
				default:
					System.out.println("No such command.");
			}
		}
	}

	/**
	 * Start the command line listener at the system console.
	 * This console will use same console with spring boot output.
	 */
	public void startInnerConsole() {
		String str;
		Scanner in = new Scanner(System.in);
		System.out.println("\n" + banner);

		while(true) {
			str = in.nextLine();
			if (str.isBlank()) continue;
			if ("exit".equals(str)) break;
			execute(str.split(" "));
			System.out.println(result);
		}

		System.out.println("Bye!");
	}

	/**
	 * Set or reset the {@code JCommander} field
	 * to get ready for next processing.
	 */
	public void setJc() {
		jc = JCommander.newBuilder().build();
		jc.addObject((cm = new CmdMain()));
		jc.addCommand("pick", (cmdPick = new CmdPick()));
		jc.addCommand("getuser", (cmdGetuser = new CmdGetuser(userService)));
		jc.addCommand("cntuser", (cmdCntuser = new CmdCntuser(userService)));
		jc.setProgramName("");
	}

	/**
	 * Execute the command using the args.
	 * @param args the args for command
	 */
	public void execute(String[] args) {
		setJc();
		result = "";
		try {
			jc.parse(args);
		} catch (Exception e) {
			String msg = e.getMessage();
			if (msg.contains("Was passed main parameter"))
				result += "No such arg named '" + msg.split("'")[1]
						+ "' for this command.\n";
			else
				result += "Command error: " + e.getMessage() + "\n";
			if (debug) {
				result += e.toString() + "\n";
				e.printStackTrace();
			}
			return;
		}
		if (cm.help) {
			result = getUsage();
			return;
		}
		String pc = jc.getParsedCommand();
		if (pc == null) {       // is not a sub command
			if (cm.debug) {
				debug = true;
				result += "Set client to debug mode.\n";
			} else if (cm.stopDebug) {
				debug = false;
				result += "Stop debug mode.";
			}
			return;
		}
		// is sub command
		switch (jc.getParsedCommand()) {
			case "getuser":
				cmdGetuser.execute();
				result += cmdGetuser.getResult() + "\n";
				return;
			case "pick":
				cmdPick.execute();
				result += cmdPick.getResult() + "\n";
				return;
			case "cntuser":
				cmdCntuser.execute();
				result += cmdCntuser.getResult() + "\n";
				return;
			default:
				// never goes here
		}
	}

	public String getUsage() {
		StringBuilder sb = new StringBuilder();
		jc.getUsageFormatter().usage(sb);
		return sb.toString();
	}
}
