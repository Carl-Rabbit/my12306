package com.dbpp.my12306.cli;

import com.beust.jcommander.JCommander;

/**
 * The handler for command.
 */

public class Commander {
	private JCommander jc;
	private String programName;

	private CmdMain cm;
	private CmdPick cmdPick;

	private boolean debug = false;

	/**
	 * Run command once in command line.
	 * Execute is like: java Commander [cmd args]
	 * @param args the args from command line.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Empty args. The usage is follow.\n");
			args = new String[]{"-help"};
		}
		new Commander("Commander").execute(args);
	}

	public Commander(String programName) {
		this.programName = programName;
		setJc();
	}

	/**
	 * Set or reset the {@code JCommander} field
	 * to get ready for next processing.
	 */
	public void setJc() {
		jc = JCommander.newBuilder().build();
		jc.addObject((cm = new CmdMain()));
		jc.addCommand("pick", (cmdPick = new CmdPick()));
		jc.setProgramName(programName);
	}

	/**
	 * Execute the command using the args.
	 * @param args the args for command
	 */
	public void execute(String[] args) {
		setJc();
		try {
			jc.parse(args);
		} catch (Exception e) {
			String msg = e.getMessage();
			if (msg.contains("Was passed main parameter"))
				System.out.println("No such arg named '" + msg.split("'")[1]
						+ "' for this command.");
			else
				System.out.println(e.getMessage());
			System.out.println("If you have any double, please type --help.");
			if (debug) {
				e.printStackTrace();
			}
			return;
		}
		if (cm.help) {
			jc.usage();
			return;
		}
		String pc = jc.getParsedCommand();
		if (pc == null) {       // is not a sub command
			if (cm.debug) {
				debug = true;
				System.out.println("Set client to debug mode.");
			} else if (cm.stopDebug) {
				debug = false;
				System.out.println("Stop debug mode.");
			}
			return;
		}
		// is sub command
		switch (jc.getParsedCommand()) {
			case "pick":
				cmdPick.pick();
				System.out.println(cmdPick.getResult());
			default:
				// never goes here
		}
	}
}
