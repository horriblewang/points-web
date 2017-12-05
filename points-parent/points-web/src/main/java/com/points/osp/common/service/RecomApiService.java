package com.points.osp.common.service;

import java.util.List;

import com.bailian.entity.MemberInfo;

public interface RecomApiService {
	
	
	/**
	 * 获取推荐的用户
	 * @param memberId
	 * @return
	 */
	public List<MemberInfo> getRecommendMem(Long memberId);

}
