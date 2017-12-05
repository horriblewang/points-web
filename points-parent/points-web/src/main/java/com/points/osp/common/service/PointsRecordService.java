package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.entity.PointsRecord;

public interface PointsRecordService {
	
	/**
	 * ����ת�ü�¼����
	 * @param map
	 * @return
	 */
	public int countPointsRecord(Map<String, Object> map);
	
	
	
	/**
	 * ����ת�÷�ҳ
	 * @param map
	 * @return
	 */
	List<PointsRecord> getRcordByPage(Map<String, Object> map);
	
	
	/**
	 * ����ת�ּ�¼
	 */
	public void addPointsRecord(PointsRecord record);

}
