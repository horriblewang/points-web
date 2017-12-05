package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.entity.FeedBack;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.MemberInfoExample;
import com.bailian.entity.MemberInfoExample.Criteria;
import com.bailian.persistence.FeedBackMapper;
import com.bailian.persistence.MemberInfoMapper;
import com.google.common.collect.Maps;
import com.points.osp.common.service.MemApiService;
import com.points.osp.common.utils.Constant;
import com.points.osp.common.utils.RedisUtil;


@Service("memApiService")
public class MemApiServiceImpl implements MemApiService {
	
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	
	@Autowired
	private FeedBackMapper feedBackMapper;
	
	@Autowired
	private RedisUtil redisUtil;
	

	public MemberInfo addMemberInfo(MemberInfo memberInfo) {
		// TODO Auto-generated method stub
		 memberInfo.setPoints(0l);
		 memberInfoMapper.insertSelective(memberInfo);
		 return memberInfo;
	}

	public MemberInfo searchMem(MemberInfo memberInfo) {
		// TODO Auto-generated method stub
		MemberInfoExample example = new MemberInfoExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andMobileEqualTo(memberInfo.getMobile());
		List<MemberInfo> List = memberInfoMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(List)){
			return List.get(0);
		}
		return null;
	}

	public void feedBack(FeedBack feedBack) {
		// TODO Auto-generated method stub
		feedBack.setCreateTime(new Timestamp(System.currentTimeMillis()));
		feedBackMapper.insert(feedBack);
	}

	public Map<String, Object> myBaseInfo(Long memberId) {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = Maps.newHashMap();
		resMap.put("code", "1");
		MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(memberId);
		if(memberInfo == null){
			resMap.put("code", "0");
			resMap.put("msg", "未查询到会员信息，请登录！");
			return resMap;
		}
		String times = redisUtil.get(Constant.SHAKE_TIME_KEY + memberInfo.getMemberId(), "0");
		resMap.put("referNum", memberInfo.getReferNum());//推荐人数
		resMap.put("memberId", memberInfo.getMemberId());
		resMap.put("mobile", memberInfo.getMobile());
		resMap.put("points", memberInfo.getPoints());
		long drawTimes = memberInfo.getReferNum() / 10;
		resMap.put("times", drawTimes - Integer.valueOf(times));
		return resMap;
	}

}
