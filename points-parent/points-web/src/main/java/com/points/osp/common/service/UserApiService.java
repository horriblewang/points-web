package com.points.osp.common.service;

import java.io.IOException;
import java.util.Map;

import com.bailian.entity.User;

public interface UserApiService {
	
	/**
	 * 用户登陆
	 * @param user
	 * @return
	 */
	public Map<String, Object> loginIn(User user);
	
	
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public Map<String, Object> regist(User user);
	
	
	/**
	 * 用户修改密码
	 * @param user
	 * @return
	 */
	public Map<String, Object> modifyPass(User user);



	/**
	 *  发送短信验证码
	 * @param mobile
	 */
	public String sendSmsVerifyCode(String mobile) throws IOException;

}
