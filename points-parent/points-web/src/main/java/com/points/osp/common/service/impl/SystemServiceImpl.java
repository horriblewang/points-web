package com.points.osp.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.entity.User;
import com.bailian.entity.UserExample;
import com.bailian.entity.UserExample.Criteria;
import com.bailian.persistence.UserMapper;
import com.bailian.utils.MD5;
import com.points.osp.common.service.SystemService;
@Service("systemService")
public class SystemServiceImpl implements SystemService {
	
	@Autowired
	private UserMapper userMapper;

	public User doLogin(Map<String, String> auth) {
		// TODO Auto-generated method stub
		UserExample example = new UserExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andPassWordEqualTo(auth.get("nonce"));
		Criteria.andUserNameEqualTo(auth.get("username"));
		List<User> list = userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	public User getCurrentUser() {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(1l);
	}

	public boolean validatePassword(String oldPassword, String password) {
		// TODO Auto-generated method stub
		
		return MD5.getMD5(oldPassword.getBytes()).equals(password);
	}

	public void updatePasswordById(Long userId, String newPassword) {
		// TODO Auto-generated method stub
		User record = new User(); 
		record.setUserId(userId);
		record.setPassWord(MD5.getMD5(newPassword.getBytes()));
		userMapper.updateByPrimaryKeySelective(record);
	}

	public User getLoginUser(String mobile) {
		// TODO Auto-generated method stub
		UserExample example = new UserExample();
		example.createCriteria().andMobileEqualTo(mobile);
		List<User> users = userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(users)){
			return users.get(0);
		}
		return null;
	}

}
