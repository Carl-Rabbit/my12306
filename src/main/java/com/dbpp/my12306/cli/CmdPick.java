package com.dbpp.my12306.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.dbpp.my12306.utils.Date;

import java.util.ArrayList;
import java.util.Arrays;


@Parameters(commandNames = "pick",
		commandDescription = "Pick one trip from system.")
public class CmdPick {

	@Parameter(names = {"-code", "-c"},
			required = true,
			description = "The code of the train.")
	private String code;

	@Parameter(names = {"-date", "-d"},
			required = true,
			description = "The date of the route, in format YYYY-MM-DD.",
			converter = DateConverter.class)
	private Date date;

	private int status = 0;
	private byte hour;
	private byte minute;
	private ArrayList<String> stations;

	/**
	 * pick full info of one route.
	 * temporary version.
	 */
	public void pick() {
		status = 0;
		hour = 12;
		minute = 0;
		stations = new ArrayList<>();
		stations.add("Guangdong");
		stations.add("Shenzhen Bei");
		status = 1;
	}

	/**
	 * get the result of execution.
	 * @return the string of result
	 */
	public String getResult() {
		if (status == 0) return "No result";
		return "Train code: " + code
				+ "\nStations: " + Arrays.toString(stations.toArray());
	}


}
