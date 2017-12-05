package com.points.osp.common.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bailian.entity.MemberInfo;
import com.bailian.entity.User;
import com.bailian.entity.UserExample;
import com.bailian.entity.UserExample.Criteria;
import com.bailian.persistence.MemberInfoMapper;
import com.bailian.persistence.UserMapper;
import com.bailian.utils.DateUtils;
import com.bailian.utils.MD5;
import com.points.osp.common.service.UserApiService;
import com.points.osp.common.utils.CodeUtils;
import com.points.osp.common.utils.Constant;
import com.points.osp.common.utils.MobileMessageSend;
import com.points.osp.common.utils.RedisUtil;
import com.points.osp.common.utils.RlMessageSendUtil;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@Service
public class UserApiServiceImpl implements UserApiService {

	// 失败次数验证
	private static int AUTH_LIMIT = 3;
	
	@Value("${login.send.points}")
	private String senPoints;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MemberInfoMapper memberInfoMapper;

	@Autowired
	private RedisUtil redisUtil;

	public Map<String, Object> loginIn(User user) {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("code", "1");
		UserExample example = new UserExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andMobileEqualTo(user.getMobile());
		// 用户验证
		List<User> list = userMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			resMap.put("code", "0");
			resMap.put("msg", "登陆失败，用户不存在。");
			return resMap;
		}
		// 失败次数验证
		User dbuser = list.get(0);
		if (dbuser.getAuthLimit() > AUTH_LIMIT) {
			resMap.put("code", "0");
			resMap.put("msg", "登陆失败，超过登陆失败次数，请重置密码。");
			return resMap;
		}
		// 密码验证
		if (!dbuser.getPassWord().equals(MD5.getMD5(user.getPassWord().getBytes()))) {
			dbuser.setAuthLimit(dbuser.getAuthLimit() + 1);
			dbuser.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			userMapper.updateByPrimaryKey(dbuser);
			resMap.put("code", "0");
			resMap.put("msg", "登陆失败，密码错误。");
			return resMap;
		} else {
			resMap.put("memberId", dbuser.getUserId());
			dbuser.setAuthLimit(0l);
			dbuser.setLoginTime(new Timestamp(System.currentTimeMillis()));
			dbuser.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			userMapper.updateByPrimaryKey(dbuser);
			// 登陆成功判断是否送积分
			String now = DateUtils.formatDate(new Date(), DateUtils.YYYYMMDD);
			String lastLoginTime = DateUtils.formatDate(dbuser.getLoginTime(), DateUtils.YYYYMMDD);
			if (!now.equals(lastLoginTime)) {
				memberInfoMapper.LoginSendPoints(dbuser.getUserId(),Long.valueOf(senPoints));
			}
		}
		return resMap;
	}

	public Map<String, Object> regist(User user) {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("code", "1");
		UserExample example = new UserExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andMobileEqualTo(user.getMobile());
		// 用户验证
		List<User> list = userMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			resMap.put("code", "0");
			resMap.put("msg", "该 用户已经存在，请登陆！");
			return resMap;
		}
		// 登陆用户表
		user.setAuthLimit(0l);
		Timestamp date = new Timestamp(System.currentTimeMillis());
		user.setCreateTime(date);
		user.setLoginTime(date);
		user.setUpdateTime(date);
		user.setPassWord(MD5.getMD5(user.getPassWord().getBytes()));
		userMapper.insert(user);

		// 插入会员表-注册送50积分
		MemberInfo record = new MemberInfo();
		record.setMemberId(user.getUserId());
		record.setMemberName(user.getUserName());
		record.setMobile(user.getMobile());
		record.setCreateTime(date);
		record.setUpdateTime(date);
		record.setPoints(0l);
		record.setReferNum(0l);
		if (StringUtils.isNotBlank(user.getReferTo())) {// 推荐人信息
			record.setReferTo(user.getReferTo());
			memberInfoMapper.addReferToNum(user.getReferTo());// 推荐人数+1
		}
		memberInfoMapper.insert(record);
		resMap.put("memberInfo", record);
		return resMap;
	}

	public Map<String, Object> modifyPass(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public String sendSmsVerifyCode(String mobile) throws IOException {
		String ckCode = redisUtil.get(Constant.VERIFY_CODE_KEY + mobile, "");
		if(StringUtils.isNotBlank(ckCode)){
			return ckCode;
		}
		String code = CodeUtils.generateVerifyCode(4);
		RlMessageSendUtil.sendSMS(mobile, code);
		//验证码十分钟有效
		redisUtil.set(Constant.VERIFY_CODE_KEY + mobile, code,300);
		return code;
	}

}
