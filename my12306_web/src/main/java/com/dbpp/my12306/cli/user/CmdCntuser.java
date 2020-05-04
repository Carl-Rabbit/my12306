package com.dbpp.my12306.cli.user;

import com.beust.jcommander.Parameters;
import com.dbpp.my12306.service.UserService;

@Parameters(commandNames = "cntuser",
		commandDescription = "Get the number of users in database.")
public class CmdCntuser {

	private int status = 0;
	private long count;

	private UserService userService;

	public CmdCntuser(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Get the user from database
	 */
	public void execute() {
		status = 0;
		count = userService.count();
		status = 1;
	}

	/**
	 * get the result of execution.
	 * @return the string of result
	 */
	public String getResult() {
		return (status == 0) ? "Not executed yet." : String.valueOf(count);
	}
}
