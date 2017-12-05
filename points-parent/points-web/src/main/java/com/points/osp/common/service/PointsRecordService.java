package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.entity.PointsRecord;

public interface PointsRecordService {
	
	/**
	 * 积分转让记录数量
	 * @param map
	 * @return
	 */
	public int countPointsRecord(Map<String, Object> map);
	
	
	
	/**
	 * 积分转让分页
	 * @param map
	 * @return
	 */
	List<PointsRecord> getRcordByPage(Map<String, Object> map);
	
	
	/**
	 * 新增转分记录
	 */
	public void addPointsRecord(PointsRecord record);

}
