package com.points.osp.common.service;

import java.util.List;

import com.bailian.entity.MemberInfo;

public interface RecomApiService {
	
	
	/**
	 * ��ȡ�Ƽ����û�
	 * @param memberId
	 * @return
	 */
	public List<MemberInfo> getRecommendMem(Long memberId);

}
