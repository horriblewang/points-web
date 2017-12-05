package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.AwardsRecordDto;


public interface AwardsService {
	
	
	/**
	 * ��ҳ��ѯ�н���¼
	 * @param map
	 */
	public int getAwardsCountByPage(Map<String,Object> map);
	
	/**
	 * ��ҳ��ѯ�н���¼
	 * @param map
	 */
	public List<AwardsRecordDto> getAwardsByPage(Map<String,Object> map);
	
	
	/**
	 * ��ȡ��Ʒ
	 * @param awardsId
	 */
	public void userAwards(Long awardsId);

}
