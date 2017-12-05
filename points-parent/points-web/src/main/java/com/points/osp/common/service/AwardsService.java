package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.AwardsRecordDto;


public interface AwardsService {
	
	
	/**
	 * 分页查询中奖记录
	 * @param map
	 */
	public int getAwardsCountByPage(Map<String,Object> map);
	
	/**
	 * 分页查询中奖记录
	 * @param map
	 */
	public List<AwardsRecordDto> getAwardsByPage(Map<String,Object> map);
	
	
	/**
	 * 领取礼品
	 * @param awardsId
	 */
	public void userAwards(Long awardsId);

}
