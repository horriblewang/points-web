package com.points.osp.common.service;

import java.util.Map;

import com.bailian.entity.FeedBack;
import com.bailian.entity.MemberInfo;

public interface MemApiService {
	
	
	/**
	 * ������Ա
	 * @param memberInfo
	 * @return
	 */
	public MemberInfo addMemberInfo(MemberInfo memberInfo);
	
	
	
	/**
	 * ��ѯ��Ա
	 * @param memberInfo
	 * @return
	 */
	public MemberInfo searchMem(MemberInfo memberInfo);
	
	
	/**
	 * �汾����
	 * @param feedBack
	 */
	public void feedBack(FeedBack feedBack);
	
	
	/**
	 * �ҵĻ�����Ϣ
	 * @param memberId
	 * @return
	 */
	public Map<String, Object> myBaseInfo(Long memberId);

}
