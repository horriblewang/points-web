package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.GoodsRecordDto;
import com.bailian.entity.GoodsRecord;
import com.bailian.entity.MemberInfo;

public interface MemberService {
	
	/**
	 * ��퓲�ԃ
	 * @return
	 */
	public List<MemberInfo> getMemberInfosByPage(Map<String, Object> paraMap);
	
	
	/**
	 * ��ԃ����
	 * @return
	 */
	public Integer countMemberInfo(Map<String, Object> paraMap);
	
	/**
	 * ��Ա���ӻ���
	 * @param paraMap
	 */
	public void addMemPoints(Map<String, Object> paraMap);
	
	
	/**
	 * ��ѯ��Ԫ
	 * @param paraMap
	 */
	public MemberInfo getMember(Long memberId);
	
	/**
	 * ��Ʒ�һ�
	 * @param goodsRecords
	 * @param memberId
	 */
	public void exchangeGoods(List<GoodsRecord> goodsRecords,String memberId)  throws Exception;
	
	
	/**
	 * ��ѯ��Ԫ
	 * @param paraMap
	 */
	public Integer countExchange(Map<String, Object> paraMap);
	
	
	/**
	 * ��ѯ��Ԫ
	 * @param paraMap
	 */
	public List<GoodsRecordDto> exchangeList(Map<String, Object> paraMap);


	/**
	 * 
	 * @param paraMap
	 */
	public String transMemPoints(Map<String, Object> paraMap);
	
	
	/**
	 * ��ȡ�Ƽ����û�
	 * @param memberId
	 * @return
	 */
	public List<MemberInfo> getRecommendMem(Long memberId);

}
