package com.dbpp.my12306.utils;

public class Price {
	public static double[] getPrice(String trainKind, Integer mileage){
		/*
		[aw, az, bw, bz, cw, cz]
		 */
		double[] res = new double[6];
		switch (trainKind.charAt(0)) {
			case 'C':
			case 'D':
			case 'G':
			case 'P':
			case 'S':
			case 'Y':
				// high railway type

				break;
			default:
				// normal type

		}
		return res;
	}
}
