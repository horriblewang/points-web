package com.points.osp.common.service;

import java.io.IOException;
import java.util.Map;

import com.bailian.entity.User;

public interface UserApiService {
	
	/**
	 * �û���½
	 * @param user
	 * @return
	 */
	public Map<String, Object> loginIn(User user);
	
	
	
	/**
	 * �û�ע��
	 * @param user
	 * @return
	 */
	public Map<String, Object> regist(User user);
	
	
	/**
	 * �û��޸�����
	 * @param user
	 * @return
	 */
	public Map<String, Object> modifyPass(User user);



	/**
	 *  ���Ͷ�����֤��
	 * @param mobile
	 */
	public String sendSmsVerifyCode(String mobile) throws IOException;

}
