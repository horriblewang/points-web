package com.points.osp.common.service;

import java.util.Map;

public interface ActivityApiService {
	
	

	/**
	 * ��ѯ���еĶһ��
	 * @return
	 */
	public Map<String, Object> getExchangeAct();
	
	
	/**
	 * ��ѯ�����еĳ齱�
	 * @return
	 */
	public Map<String, Object> getLuckyDraw();
	
	
	
	
	/**
	 * �齱
	 * @param actId
	 * @param memberId 
	 * @return
	 */
	public Map<String, Object> shakeForGift(Long actId, Long memberId);
	
	
	

	/**
	 * �һ���Ʒ
	 * @param memberId
	 * @param goods
	 * @param goodsNum
	 * @return
	 */
	public Map<String, Object> exchangeGoods(String memberId, String goods,
			String goodsNum);

}
