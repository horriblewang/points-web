package com.points.osp.common.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;

public class SpringContextHolder implements ApplicationContextAware,
		DisposableBean {
	private static ApplicationContext applicationContext = null;

	private static Logger logger = LoggerFactory
			.getLogger(SpringContextHolder.class);

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static String getRootRealPath() {
		String rootRealPath = "";
		try {
			rootRealPath = getApplicationContext().getResource("").getFile()
					.getAbsolutePath();
		} catch (IOException e) {
			logger.error("获取系统根目录失败", e);
		}
		return rootRealPath;
	}

	public static String getResourceRootRealPath() {
		String rootRealPath = "";
		try {
			rootRealPath = new DefaultResourceLoader().getResource("")
					.getFile().getAbsolutePath();
		} catch (IOException e) {
			logger.error("获取资源根目录失败", e);
		}
		return rootRealPath;
	}

	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	public static void clearHolder() {
		if (logger.isDebugEnabled()) {
			logger.debug("清除SpringContextHolder中的ApplicationContext:"
					+ applicationContext);
		}
		applicationContext = null;
	}


	public void destroy() throws Exception {
		clearHolder();
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = arg0;
	}

}