package com.points.osp.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.entity.MemberInfo;
import com.bailian.entity.MemberInfoExample;
import com.bailian.persistence.MemberInfoMapper;
import com.points.osp.common.service.RecomApiService;

@Service
public class RecomApiServiceImpl implements RecomApiService {
	
	@Autowired
	private MemberInfoMapper memberInfoMapper;

	public List<MemberInfo> getRecommendMem(Long memberId) {
		// TODO Auto-generated method stub
		MemberInfo mem = memberInfoMapper.selectByPrimaryKey(memberId);
		if(mem == null){
			return null;
		}
		MemberInfoExample example = new MemberInfoExample();
		example.createCriteria().andReferToEqualTo(mem.getMobile());
		return memberInfoMapper.selectByExample(example);
	}

}
