package com.bailian.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultUtil {
	public static Map<String, Object> crePageSucResult(Page page) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode",
				ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		resultMap.put("obj", page);
		return resultMap;
	}

	public static Map<String, Object> creObjSucResult(Object obj) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode",
				ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		resultMap.put("obj", obj);
		return resultMap;
	}

	public static Map<String, Object> creComSucResult() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode",ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		return resultMap;
	}

	public static Map<String, Object> creListSucResult(List list) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode",
				ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("list", list);
		resultMap.put("obj", listMap);
		return resultMap;
	}

	public static Map<String, Object> creComErrorResult(String message) {
		if ((message == null) || (message.equals(""))) {
			return creComErrorResult();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap
				.put("resCode",
						ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR
								.getErrorCode());
		resultMap.put("msg", message);
		return resultMap;
	}

	public static Map<String, Object> creComEmptyResult() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap
				.put("resCode",
						ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR
								.getErrorCode());
		resultMap.put("msg",
				ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
		return resultMap;
	}

	public static Map<String, Object> creComErrorResult(String errorCode,
			String message) {
		if ((message == null) || (message.equals(""))) {
			return creComErrorResult();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode", errorCode);
		resultMap.put("msg", message);
		return resultMap;
	}

	public static Map<String, Object> creComErrorResult() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap
				.put("resCode",
						ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR
								.getErrorCode());
		resultMap.put("msg",
				ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
		return resultMap;
	}

	public static <T> Map<String, Object> creListErrorResult(List<T> list) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode",
				ComErrorCodeConstants.ErrorCode.APPLICATION_OPER_ERROR
						.getErrorCode());
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("list", list);
		resultMap.put("obj", listMap);
		return resultMap;
	}

	public static Map<String, Object> creComSucResult(boolean flag) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (flag)
			resultMap.put("resCode",
					ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS
							.getErrorCode());
		else {
			resultMap.put("resCode",
					ComErrorCodeConstants.ErrorCode.UPDATE_STATUS_ERROR
							.getErrorCode());
		}
		return resultMap;
	}
}