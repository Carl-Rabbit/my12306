package com.dbpp.my12306.cli;

import com.beust.jcommander.Parameter;

/**
 * the main params of command
 * the process logic is in Class {@code Commander}
 */

public class CmdMain {

	@Parameter(names = {"-help", "-h"}, help = true)
	public boolean help;

	@Parameter(names = {"-debug", "-d"}, description = "Run this and later commands in debug mode.")
	public boolean debug;

	@Parameter(names = {"-debugstop", "-ds"} ,description = "Stop run commands in debug mode.")
	public boolean stopDebug;
}
