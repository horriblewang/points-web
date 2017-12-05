package com.points.osp.common.config;

import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;

public class Global {

	private static final AppConfig APP_CFG = (AppConfig) SpringContextHolder
			.getBean(AppConfig.class);

	public static final String USER_SESSION_KEY = "LOGIN_USER_KEY";

	private static Map<String, String> map = Maps.newHashMap();

	public static String getConfig(String key) {
		String value = (String) map.get(key);
		if (value == null) {
			value = APP_CFG.getProperty(key);
			map.put(key, value);
		}
		return value;
	}

	public static String getAdminPath() {
		return getConfig("adminPath");
	}

	public static String getFrontPath() {
		return getConfig("frontPath");
	}

	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}

	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return Boolean.valueOf(("true".equals(dm)) || ("1".equals(dm)));
	}

	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return Boolean.valueOf(("true".equals(dm)) || ("1".equals(dm)));
	}

	public static String getCkBaseDir() {
		String dir = getConfig("userfiles.basedir");
		Assert.hasText(dir, "配置文件里没有配置userfiles.basedir属性");
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		return dir;
	}

	public static String getResourceRootPath() {
		return getConfig("resource.path.prefix");
	}
}
