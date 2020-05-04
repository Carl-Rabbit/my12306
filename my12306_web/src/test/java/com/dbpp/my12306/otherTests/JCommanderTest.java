package com.dbpp.my12306.otherTests;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class JCommanderTest {
	@Parameter(names={"--length", "-l"})
	int length;
	@Parameter(names={"--pattern", "-p"})
	int pattern;

	public static void main(String[] argv) {
		JCommanderTest main = new JCommanderTest();
		JCommander.newBuilder()
				.addObject(main)
				.build()
				.parse(argv);
		main.run();
	}

	public void run() {
		System.out.printf("%d %d", length, pattern);
	}
}
