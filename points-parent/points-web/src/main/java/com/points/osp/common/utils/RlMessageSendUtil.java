package com.points.osp.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.common.collect.Maps;
import com.points.osp.common.config.Global;

/**
 * 荣联云短信发送
 * @author wbwangsh
 *
 */
public class RlMessageSendUtil {

	private static Logger log = LoggerFactory.getLogger(RlMessageSendUtil.class);

	private static String ACCOUNT_SID = Global.getConfig("ronglian.account.sid");
	
	private static String AUTH_TOKEN = Global.getConfig("ronglian.account.auth.token");
	
	private static String APP_ID = Global.getConfig("ronglian.account.appid");

	private static String TEMPLATE_ID = Global.getConfig("ronglian.account.templateid");

	private static String VALID_TIME = Global.getConfig("ronglian.account.validtime");

	public static void sendSMS(String mobile, String code) {

		Map<String, Object> result = Maps.newHashMap();

		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

		restAPI.init("app.cloopen.com", "8883");

		restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);

		restAPI.setAppId(APP_ID);

		result = restAPI.sendTemplateSMS(mobile, TEMPLATE_ID, new String[] {code, VALID_TIME });

		log.info("RlMessageSendUtil result=" + result);
		if ("000000".equals(result.get("statusCode"))) {
			// 正常返回输出data包体信息（map）
			Map<String, Object> data = (Map<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				Object object = data.get(key);
				log.info(key + " = " + object);
			}
		} else {
			// 异常返回输出错误码和错误信息
			log.error("错误码=" + result.get("statusCode") + " 错误信息= "+ result.get("statusMsg"));
		}

	}

}
