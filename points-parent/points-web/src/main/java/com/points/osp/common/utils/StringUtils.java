package com.points.osp.common.utils;

public class StringUtils {

	public static String abbr(String str, int len) {

		if (str != null && str.length() > 16) {
			return str.substring(0, len) + "...";
		} else {
			return str;
		}

	}

	public static String rabbr(String str, int len) {

		if (str != null && str.length() > 16) {
			return str.substring(0, len) + "...";
		} else {
			return str;
		}

	}

}
