package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.dto.GoodsRecordDto;
import com.bailian.entity.GoodsRecord;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.MemberInfoExample;
import com.bailian.entity.MemberInfoExample.Criteria;
import com.bailian.entity.PointsRecord;
import com.bailian.persistence.GoodsRecordMapper;
import com.bailian.persistence.MemberInfoMapper;
import com.bailian.persistence.PointsRecordMapper;
import com.points.osp.common.service.MemberService;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	
	
	@Autowired
	private GoodsRecordMapper goodsRecordMapper;
	
	@Autowired
	private PointsRecordMapper pointsRecordMapper;

	public List<MemberInfo> getMemberInfosByPage(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return memberInfoMapper.selMemberByPage(paraMap);
	}

	public Integer countMemberInfo(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		MemberInfoExample example = new MemberInfoExample();
		Criteria Criteria = example.createCriteria();
		String mobile = "";
		String referTo = "";
		if(paraMap.containsKey("mobile")){
			mobile = paraMap.get("mobile").toString();
		}
		if(paraMap.containsKey("referTo")){
			referTo = paraMap.get("referTo").toString();
		}
		if(StringUtils.isNotBlank(mobile)){
			Criteria.andMobileEqualTo(mobile);
		}
		if(StringUtils.isNotBlank(referTo)){
			Criteria.andReferToEqualTo(referTo);
		}
		return memberInfoMapper.countByExample(example);
	}

	public void addMemPoints(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		String memberId = paraMap.get("memberId").toString();
		String points = paraMap.get("points").toString();
		MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(Long.valueOf(memberId));
		Long cuPoint = memberInfo.getPoints();
		memberInfo.setPoints(Long.valueOf(points) + cuPoint);
		memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
	}

	public MemberInfo getMember(Long memberId) {
		// TODO Auto-generated method stub
		return memberInfoMapper.selectByPrimaryKey(memberId);
	}

	public void exchangeGoods(List<GoodsRecord> goodsRecords, String memberId) throws Exception {
		// TODO Auto-generated method stub
		Long points = 0l;
		for(GoodsRecord goodsRecord : goodsRecords){
			points += goodsRecord.getGoodsPoints() * goodsRecord.getGoodsNum();
			goodsRecord.setMemberId(Long.valueOf(memberId));
			goodsRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
			goodsRecordMapper.insert(goodsRecord);
		}
		
		MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(Long.valueOf(memberId));
		if(points > memberInfo.getPoints()){
			throw new Exception("积分不足，无法兑换！");
		}else{
			memberInfo.setPoints(memberInfo.getPoints() - points);
		}
		
		memberInfoMapper.updateByPrimaryKey(memberInfo);
		
	}

	public Integer countExchange(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		
		return goodsRecordMapper.countExchange(paraMap);
	}

	public List<GoodsRecordDto> exchangeList(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return goodsRecordMapper.selExchangeList(paraMap);
	}

	public String transMemPoints(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		String memberId  = (String)paraMap.get("memberId");
		String points  = (String)paraMap.get("points");
		String mobile  = (String)paraMap.get("mobile");
		MemberInfo outMember = memberInfoMapper.selectByPrimaryKey(Long.valueOf(memberId));
		MemberInfo inMember = memberInfoMapper.selectByMobile(mobile);
		if(inMember == null){
			return "转移用户不存在！";	
		}
		if(outMember.getPoints() >= Long.valueOf(points)){
			memberInfoMapper.addMemPointsByMobile(mobile, Long.valueOf(points));//inMember这个积分加
			memberInfoMapper.addMemPointsByMobile(outMember.getMobile(), -1*Long.valueOf(points));//这个用户减去
		}else{
			return "积分不足，无法转移！";	
		}
		//积分转让记录
		PointsRecord record = new PointsRecord();
		record.setFromMem(outMember.getMobile());
		record.setToMem(mobile);
		record.setPoints(points);
		record.setCreateTime(new Timestamp(System.currentTimeMillis()));
		pointsRecordMapper.insert(record);
		
		return null;
	}

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
