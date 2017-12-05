package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.ActivityDetailDto;
import com.bailian.dto.ActivityDto;
import com.bailian.entity.Activity;

/**
 * 兑换活动配置
 * @author wbwangsh
 *
 */
public interface ExchangeService {
	
	/**
	 * 数量
	 * @param map
	 * @return
	 */
	public int countExchange(Map<String, Object> map);
	
	/**
	 * 分页
	 * @param map
	 * @return
	 */
	public List<ActivityDto> selExchangeList(Map<String, Object> map);
	
	
	
	
	/**
	 * 获取单个活动
	 * @param goodsId
	 * @return
	 */
	public Activity getExchangeById(Long actId);
	
	
	/**
	 * 获取活动详情
	 * @param goodsId
	 * @return
	 */
	public List<ActivityDetailDto> getExchangeDetailById(Long actId);
	
	
    /**
     * 新增活动
     * @param Avtivity
     * @return
     */
	public void addExchange(Activity avtivity,String deail);

	 /**
     * 修改活动
     * @param Avtivity
     * @return
     */
	public void updateExchange(Activity avtivity,String deail);

	
	/**
	 * 活动禁用
	 * @param actId
	 */
	public void disableExchange(Long actId);
	
	
	
	/**
	 * 活动启用
	 * @param actId
	 */
	public void enableExchange(Long actId);
	
	
	
	/**
	 * 活动启用
	 * @param actId
	 */
	public Long checkExchangeTime(Map<String, Object> map);

}
