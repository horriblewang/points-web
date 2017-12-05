package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.ActivityDetailDto;
import com.bailian.dto.ActivityDto;
import com.bailian.entity.Activity;

/**
 * �һ������
 * @author wbwangsh
 *
 */
public interface ExchangeService {
	
	/**
	 * ����
	 * @param map
	 * @return
	 */
	public int countExchange(Map<String, Object> map);
	
	/**
	 * ��ҳ
	 * @param map
	 * @return
	 */
	public List<ActivityDto> selExchangeList(Map<String, Object> map);
	
	
	
	
	/**
	 * ��ȡ�����
	 * @param goodsId
	 * @return
	 */
	public Activity getExchangeById(Long actId);
	
	
	/**
	 * ��ȡ�����
	 * @param goodsId
	 * @return
	 */
	public List<ActivityDetailDto> getExchangeDetailById(Long actId);
	
	
    /**
     * �����
     * @param Avtivity
     * @return
     */
	public void addExchange(Activity avtivity,String deail);

	 /**
     * �޸Ļ
     * @param Avtivity
     * @return
     */
	public void updateExchange(Activity avtivity,String deail);

	
	/**
	 * �����
	 * @param actId
	 */
	public void disableExchange(Long actId);
	
	
	
	/**
	 * �����
	 * @param actId
	 */
	public void enableExchange(Long actId);
	
	
	
	/**
	 * �����
	 * @param actId
	 */
	public Long checkExchangeTime(Map<String, Object> map);

}
