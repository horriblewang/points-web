package com.points.osp.common.service;

import java.util.Map;

import com.bailian.entity.User;

public interface SystemService {
	
	public User doLogin(Map<String,String> auth);
	
	/**
	 * 获取当前登陆用户
	 * @return
	 */
	public User getCurrentUser();

	/**
	 * 校验密码
	 * @param oldPassword
	 * @param password
	 * @return
	 */
	public boolean validatePassword(String oldPassword, String password);

	/**
	 * 更新用户密码
	 * @param userId
	 * @param newPassword
	 */
	public void updatePasswordById(Long userId, String newPassword);

	/**
	 * 根据手机号码查询
	 * @param mobile
	 * @return
	 */
	public User getLoginUser(String mobile);
}
