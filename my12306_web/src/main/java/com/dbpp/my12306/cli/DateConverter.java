package com.dbpp.my12306.cli;

import com.beust.jcommander.IStringConverter;
import com.dbpp.my12306.utils.Date;

public class DateConverter implements IStringConverter<Date> {

	@Override
	public Date convert(String s) {
		return new Date(s);
	}
}
