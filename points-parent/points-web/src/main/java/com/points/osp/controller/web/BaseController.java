package com.points.osp.controller.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String MESSAGES = "messages";
	public static final String CONTENT = "content";
	static Logger loger = LoggerFactory.getLogger(BaseController.class);

	private static Gson GSON = new GsonBuilder()
			.enableComplexMapKeySerialization()
			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public String ajax(HttpServletResponse response, String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			loger.error("IOException:", e);
		}
		return null;
	}

	public String ajaxText(HttpServletResponse response, String text) {
		return ajax(response, text, "text/plain");
	}

	public String ajaxHtml(HttpServletResponse response, String html) {
		return ajax(response, html, "text/html");
	}

	public String ajaxXml(HttpServletResponse response, String xml) {
		return ajax(response, xml, "text/xml");
	}

	public String ajaxJson(HttpServletResponse response, String jsonString) {
		return ajax(response, jsonString, "text/html");
	}

	/**
	 * Ô¶³Ì¿çÓòjsopµ÷ÓÃ
	 * @param request
	 * @param response
	 * @param jsonMap
	 * @return
	 */
	public String ajaxJsonP(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> jsonMap) {
		String callBack = request.getParameter("callback");
		if(callBack == null){
			callBack = "";
		}
		return ajax(response,callBack + "(" + GSON.toJson(jsonMap)+ ")", "text/html");
	}

	public String ajaxJson(HttpServletResponse response,
			Map<String, Object> jsonMap) {
		return ajax(response, "jsonpCallback" + "(" + GSON.toJson(jsonMap)
				+ ")", "text/html");
	}

	public String ajaxJsonWarnMessage(HttpServletResponse response,
			String message) {
		Map jsonMap = new HashMap();
		jsonMap.put("status", "warn");
		jsonMap.put("message", message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	public String ajaxJsonWarnMessages(HttpServletResponse response,
			List<String> messages) {
		Map jsonMap = new HashMap();
		jsonMap.put("status", "warn");
		jsonMap.put("messages", messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	public String ajaxJsonSuccessMessage(HttpServletResponse response,
			String message) {
		Map jsonMap = new HashMap();
		jsonMap.put("status", "success");
		jsonMap.put("message", message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	public String ajaxJsonSuccessMessages(HttpServletResponse response,
			List<String> messages) {
		Map jsonMap = new HashMap();
		jsonMap.put("status", "success");
		jsonMap.put("messages", messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	public String ajaxJsonErrorMessage(HttpServletResponse response,
			String message) {
		Map jsonMap = new HashMap();
		jsonMap.put("status", "error");
		jsonMap.put("message", message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	public String ajaxJsonErrorMessages(HttpServletResponse response,
			List<String> messages) {
		Map jsonMap = new HashMap();
		jsonMap.put("status", "error");
		jsonMap.put("messages", messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	public void setResponseNoCache(HttpServletResponse response) {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0L);
	}

	public String getJson(Object jsonObject) {
		return GSON.toJson(jsonObject);
	}

	public String ajaxJsonCache(HttpServletResponse response,
			String jsonString, String cacheTime) {
		return ajaxCache(response, jsonString, "text/html", cacheTime);
	}

	public String ajaxCache(HttpServletResponse response, String content,
			String type, String cacheTime) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			setCache(response, cacheTime);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			loger.error(e.getMessage());
		}
		return null;
	}

	public void setCache(HttpServletResponse response, String cacheTime) {
		long now = System.currentTimeMillis();
		long cacheTimeLong = Long.parseLong(cacheTime);
		response.setDateHeader("Expires", now + cacheTimeLong);
		response.setDateHeader("Last-Modified", now - now % cacheTimeLong);
		response.setHeader("Cache-Control", "max-age=" + cacheTime);
		response.setHeader("Pragma", "Pragma");
	}

	protected boolean validateParas(BindingResult parametersBindingResult,
			Map<String, Object> map) {
		if (parametersBindingResult.hasErrors()) {
			List fes = parametersBindingResult.getFieldErrors();
			String checkMsg = ((FieldError) fes.get(0)).getDefaultMessage();
			map.put("success", Boolean.valueOf(false));
			map.put("errorCode", "11");
			map.put("msg", checkMsg);
			return false;
		}
		return true;
	}

}