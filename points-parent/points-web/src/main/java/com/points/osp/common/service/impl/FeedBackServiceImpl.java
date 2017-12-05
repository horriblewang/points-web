package com.points.osp.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.entity.FeedBack;
import com.bailian.entity.FeedBackExample;
import com.bailian.persistence.FeedBackMapper;
import com.points.osp.common.service.FeedBackService;

@Service
public class FeedBackServiceImpl implements FeedBackService {
	
	@Autowired
	private FeedBackMapper feedBackMapper;

	public int countFeedBack(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String remark = "";
		if(map.containsKey("remark")){
			remark = (String)map.get("remark");
		}
		FeedBackExample example = new FeedBackExample();
		if(StringUtils.isNotBlank(remark)){
			example.createCriteria().andRemarkLike("%" + remark + "%");	
		}
		return feedBackMapper.countByExample(example);
	}

	public List<FeedBack> getFeedBackByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return feedBackMapper.selFeedBackByPage(map);
	}

}
