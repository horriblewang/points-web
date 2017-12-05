package com.points.osp.common.service;

import java.util.Map;

import com.bailian.entity.FeedBack;
import com.bailian.entity.MemberInfo;

public interface MemApiService {
	
	
	/**
	 * 新增会员
	 * @param memberInfo
	 * @return
	 */
	public MemberInfo addMemberInfo(MemberInfo memberInfo);
	
	
	
	/**
	 * 查询会员
	 * @param memberInfo
	 * @return
	 */
	public MemberInfo searchMem(MemberInfo memberInfo);
	
	
	/**
	 * 版本反馈
	 * @param feedBack
	 */
	public void feedBack(FeedBack feedBack);
	
	
	/**
	 * 我的基本信息
	 * @param memberId
	 * @return
	 */
	public Map<String, Object> myBaseInfo(Long memberId);

}
