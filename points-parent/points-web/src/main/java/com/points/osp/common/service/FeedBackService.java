package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.entity.FeedBack;

public interface FeedBackService {
	
	/**
	 * ��ѯ����
	 * @param map
	 * @return
	 */
	public int countFeedBack(Map<String, Object> map);
	
	
	/**
	 * ��ҳ��ѯ
	 * @param map
	 * @return
	 */
	public List<FeedBack> getFeedBackByPage(Map<String, Object> map);
	

}
