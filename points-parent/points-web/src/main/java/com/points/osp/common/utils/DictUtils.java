package com.points.osp.common.utils;

import java.util.List;

public class DictUtils {
	
	/**
	 * 获取主图片
	 * @param key
	 * @return
	 */
	public static String getMainPic(String key) {
		return key.split(";")[0];
	}

	/**
	 * 数据字典列表
	 * 
	 * @param type
	 * @return
	 */
	public static List<Object> getDictList(String type) {
		return null;
	}

	/**
	 * 数据字典列表标签
	 * 
	 * @param value
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public static String getDictLabel(String value, String type,
			String defaultValue) {

		return "";
	}

}
