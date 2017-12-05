package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.entity.RuleInfo;

public interface RuleService {
	
	
	/**
	 * ��ҳ��ѯ
	 * @param paraMap
	 * @return
	 */
	public int countRule(Map<String, Object> paraMap);
	
	/**
	 * ��ҳ��ѯ
	 * @param paraMap
	 * @return
	 */
	public List<RuleInfo> getRuleByPage(Map<String, Object> paraMap);
	
	
	/**
	 * ��������
	 * @param ruleInfo
	 */
	public void addRuleInfo(RuleInfo ruleInfo);
	
	
	/**
	 * ��������
	 * @param ruleInfo
	 */
	public void updateRuleInfo(RuleInfo ruleInfo);

	
	/**
	 * ��ѯ����
	 * @param ruleId
	 * @return
	 */
	public RuleInfo getRuleInfo(Long ruleId);
	
	
	/**
	 * ��ȡ������Ч����
	 * @return
	 */
	public List<RuleInfo> getAllEffectInfos();
}
