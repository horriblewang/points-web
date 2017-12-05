package com.points.osp.common.service;

import java.util.Map;

public interface ActivityApiService {
	
	

	/**
	 * 查询运行的兑换活动
	 * @return
	 */
	public Map<String, Object> getExchangeAct();
	
	
	/**
	 * 查询运行中的抽奖活动
	 * @return
	 */
	public Map<String, Object> getLuckyDraw();
	
	
	
	
	/**
	 * 抽奖
	 * @param actId
	 * @param memberId 
	 * @return
	 */
	public Map<String, Object> shakeForGift(Long actId, Long memberId);
	
	
	

	/**
	 * 兑换商品
	 * @param memberId
	 * @param goods
	 * @param goodsNum
	 * @return
	 */
	public Map<String, Object> exchangeGoods(String memberId, String goods,
			String goodsNum);

}
