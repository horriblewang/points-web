package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.entity.FeedBack;

public interface FeedBackService {
	
	/**
	 * 查询数量
	 * @param map
	 * @return
	 */
	public int countFeedBack(Map<String, Object> map);
	
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<FeedBack> getFeedBackByPage(Map<String, Object> map);
	

}
