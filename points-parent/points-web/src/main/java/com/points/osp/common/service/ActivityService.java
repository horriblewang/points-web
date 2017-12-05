package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.ActivityDetailDto;
import com.bailian.dto.ActivityDto;
import com.bailian.entity.Activity;
import com.bailian.entity.ActivityDetail;

public interface ActivityService {
	
	
	public Integer countActivity(Map<String, Object> paraMap);
	
	public List<ActivityDto> getActivityByPage(Map<String, Object> paraMap);

	/**
	 * 获取单个活动
	 * @param goodsId
	 * @return
	 */
	public Activity getActivityById(Long actId);
	
	
	/**
	 * 获取活动详情
	 * @param goodsId
	 * @return
	 */
	public List<ActivityDetailDto> getActivityDetailById(Long actId);
	
	
    /**
     * 新增活动
     * @param Avtivity
     * @return
     */
	public void addActivity(Activity avtivity,String deail);

	 /**
     * 修改活动
     * @param Avtivity
     * @return
     */
	public void updateActivity(Activity avtivity,String deail);

	
	/**
	 * 活动禁用
	 * @param actId
	 */
	public void disableAct(Long actId);
	
	
	
	/**
	 * 活动启用
	 * @param actId
	 */
	public void enableAct(Long actId);
	
	
	
	/**
	 * 活动启用
	 * @param actId
	 */
	public Long checkActTime(Map<String, Object> map);
	
	

}
