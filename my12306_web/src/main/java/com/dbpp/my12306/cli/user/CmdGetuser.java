package com.dbpp.my12306.cli.user;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.service.UserService;

@Parameters(commandNames = "getuser",
		commandDescription = "Get a user info from database by id or name.")
public class CmdGetuser {
	@Parameter(names = {"-id"},
			description = "The user id.")
	private long id;

	@Parameter(names = {"-name", "-n"},
			description = "The user name.")
	private String name;

	private int status = 0;
	private User user;

	private UserService userService;

	public CmdGetuser(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Get the user from database
	 */
	public void execute() {
		status = 0;
		if (id != 0 && name != null) {
			name = name.replaceAll("'", "");
			user = userService.getById(id);
			if (!user.getUserName().equals(name)) {
				status = -1;        // no user has this id and name at same time.
				return;
			}
		}
		if (id != 0) {
			user = userService.getById(id);
		} else if (name != null){
			name = name.replaceAll("'", "");
			user = userService.getByName(name);
		} else {
			status = -2;
			return;
		}

		status = 1;
	}

	/**
	 * get the result of execution.
	 * @return the string of result
	 */
	public String getResult() {
		switch (status) {
			case 0: return "Not executed yet.";
			case 1: return (user == null) ? "No such user." : user.toString();
			case -1: return "No such user where id=" + id + " and name='" + name + "'";
			case -2: return "Required at least one argument (id or name).";
		}
		// never go here
		return "";
	}
}
