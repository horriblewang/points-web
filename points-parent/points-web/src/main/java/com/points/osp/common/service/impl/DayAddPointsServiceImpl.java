package com.points.osp.common.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.dto.DayMemPointsDto;
import com.bailian.persistence.DayAddPointsMapper;
import com.points.osp.common.service.DayAddPointsService;

/**
 * 每日加积分定时任务
 * @author wbwangsh
 *
 */
@Service("dayPointsService")
public class DayAddPointsServiceImpl implements DayAddPointsService {
	
	private Logger log = LoggerFactory.getLogger(DayAddPointsServiceImpl.class);
	
	@Autowired
	private DayAddPointsMapper dayAddPointsMapper;

	public void addPoints() {
		// TODO Auto-generated method stub
		log.info("start add points" + System.currentTimeMillis());
		List<DayMemPointsDto> mems = dayAddPointsMapper.getDayAddMemberInfo();
		if(CollectionUtils.isNotEmpty(mems)){
			dayAddPointsMapper.updateMemPoints(mems);
		}
		log.info("end add points" + System.currentTimeMillis());
	}

}
