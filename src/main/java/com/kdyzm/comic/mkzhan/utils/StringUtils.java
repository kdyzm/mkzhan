package com.kdyzm.comic.mkzhan.utils;

public class StringUtils {
	public static String getZeroPrifixFileName(int index) {
		String indexTemp = index + "";
		String regex = "(\\d+)";
		indexTemp = indexTemp.replaceAll(regex, "000$1");
		regex = "0*(\\d{4})";
		indexTemp = indexTemp.replaceAll(regex, "$1");
		return indexTemp;
	}
}
