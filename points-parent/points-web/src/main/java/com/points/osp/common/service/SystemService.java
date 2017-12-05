package com.points.osp.common.service;

import java.util.Map;

import com.bailian.entity.User;

public interface SystemService {
	
	public User doLogin(Map<String,String> auth);
	
	/**
	 * ��ȡ��ǰ��½�û�
	 * @return
	 */
	public User getCurrentUser();

	/**
	 * У������
	 * @param oldPassword
	 * @param password
	 * @return
	 */
	public boolean validatePassword(String oldPassword, String password);

	/**
	 * �����û�����
	 * @param userId
	 * @param newPassword
	 */
	public void updatePasswordById(Long userId, String newPassword);

	/**
	 * �����ֻ������ѯ
	 * @param mobile
	 * @return
	 */
	public User getLoginUser(String mobile);
}
