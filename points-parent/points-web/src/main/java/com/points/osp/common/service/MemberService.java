package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.GoodsRecordDto;
import com.bailian.entity.GoodsRecord;
import com.bailian.entity.MemberInfo;

public interface MemberService {
	
	/**
	 * 分頁查詢
	 * @return
	 */
	public List<MemberInfo> getMemberInfosByPage(Map<String, Object> paraMap);
	
	
	/**
	 * 查詢數量
	 * @return
	 */
	public Integer countMemberInfo(Map<String, Object> paraMap);
	
	/**
	 * 会员增加积分
	 * @param paraMap
	 */
	public void addMemPoints(Map<String, Object> paraMap);
	
	
	/**
	 * 查询会元
	 * @param paraMap
	 */
	public MemberInfo getMember(Long memberId);
	
	/**
	 * 礼品兑换
	 * @param goodsRecords
	 * @param memberId
	 */
	public void exchangeGoods(List<GoodsRecord> goodsRecords,String memberId)  throws Exception;
	
	
	/**
	 * 查询会元
	 * @param paraMap
	 */
	public Integer countExchange(Map<String, Object> paraMap);
	
	
	/**
	 * 查询会元
	 * @param paraMap
	 */
	public List<GoodsRecordDto> exchangeList(Map<String, Object> paraMap);


	/**
	 * 
	 * @param paraMap
	 */
	public String transMemPoints(Map<String, Object> paraMap);
	
	
	/**
	 * 获取推荐的用户
	 * @param memberId
	 * @return
	 */
	public List<MemberInfo> getRecommendMem(Long memberId);

}
