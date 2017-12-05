package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.entity.RuleInfo;
import com.bailian.entity.RuleInfoExample;
import com.bailian.persistence.RuleInfoMapper;
import com.points.osp.common.service.RuleService;

@Service
public class RuleServiceImpl implements RuleService {
	
	@Autowired
	private RuleInfoMapper ruleInfoMapper;

	public int countRule(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		RuleInfoExample example = new RuleInfoExample();
		String ruleName = "";
		if(paraMap.containsKey("ruleName")){
			ruleName = (String)paraMap.get("ruleName");
		}
		if(StringUtils.isNotBlank(ruleName)){
			example.createCriteria().andRuleNameLike("%" + ruleName + "%");
		}
		return ruleInfoMapper.countByExample(example);
	}

	public List<RuleInfo> getRuleByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ruleInfoMapper.selRuleByPage(map);
	}

	public void addRuleInfo(RuleInfo ruleInfo) {
		// TODO Auto-generated method stub
		Timestamp time = new Timestamp(System.currentTimeMillis());
		ruleInfo.setCreateTime(time);
		ruleInfo.setUpdateTime(time);
		ruleInfo.setStatus("1");//ÓÐÐ§
		ruleInfoMapper.insert(ruleInfo);
	}

	public RuleInfo getRuleInfo(Long ruleId) {
		// TODO Auto-generated method stub
		return ruleInfoMapper.selectByPrimaryKey(ruleId);
	}

	public void updateRuleInfo(RuleInfo ruleInfo) {
		// TODO Auto-generated method stub
		Timestamp time = new Timestamp(System.currentTimeMillis());
		ruleInfo.setUpdateTime(time);
		ruleInfoMapper.updateByPrimaryKeySelective(ruleInfo);
	}

	public List<RuleInfo> getAllEffectInfos() {
		// TODO Auto-generated method stub
		RuleInfoExample example = new RuleInfoExample();
		example.createCriteria().andStatusEqualTo("1");
		return ruleInfoMapper.selectByExample(example);
	}

}
