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
	 * ��ȡ�����
	 * @param goodsId
	 * @return
	 */
	public Activity getActivityById(Long actId);
	
	
	/**
	 * ��ȡ�����
	 * @param goodsId
	 * @return
	 */
	public List<ActivityDetailDto> getActivityDetailById(Long actId);
	
	
    /**
     * �����
     * @param Avtivity
     * @return
     */
	public void addActivity(Activity avtivity,String deail);

	 /**
     * �޸Ļ
     * @param Avtivity
     * @return
     */
	public void updateActivity(Activity avtivity,String deail);

	
	/**
	 * �����
	 * @param actId
	 */
	public void disableAct(Long actId);
	
	
	
	/**
	 * �����
	 * @param actId
	 */
	public void enableAct(Long actId);
	
	
	
	/**
	 * �����
	 * @param actId
	 */
	public Long checkActTime(Map<String, Object> map);
	
	

}
