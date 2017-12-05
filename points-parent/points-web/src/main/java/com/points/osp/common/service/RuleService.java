package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.entity.RuleInfo;

public interface RuleService {
	
	
	/**
	 * 分页查询
	 * @param paraMap
	 * @return
	 */
	public int countRule(Map<String, Object> paraMap);
	
	/**
	 * 分页查询
	 * @param paraMap
	 * @return
	 */
	public List<RuleInfo> getRuleByPage(Map<String, Object> paraMap);
	
	
	/**
	 * 新增规则
	 * @param ruleInfo
	 */
	public void addRuleInfo(RuleInfo ruleInfo);
	
	
	/**
	 * 新增规则
	 * @param ruleInfo
	 */
	public void updateRuleInfo(RuleInfo ruleInfo);

	
	/**
	 * 查询规则
	 * @param ruleId
	 * @return
	 */
	public RuleInfo getRuleInfo(Long ruleId);
	
	
	/**
	 * 获取所有有效规则
	 * @return
	 */
	public List<RuleInfo> getAllEffectInfos();
}
