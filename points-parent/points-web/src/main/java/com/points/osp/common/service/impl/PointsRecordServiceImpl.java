package com.points.osp.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.entity.PointsRecord;
import com.bailian.entity.PointsRecordExample;
import com.bailian.entity.PointsRecordExample.Criteria;
import com.bailian.persistence.PointsRecordMapper;
import com.points.osp.common.service.PointsRecordService;

@Service
public class PointsRecordServiceImpl implements PointsRecordService {


	
	@Autowired
	private PointsRecordMapper pointsRecordMapper;

	@Override
	public int countPointsRecord(Map<String, Object> map) {
		// TODO Auto-generated method stub
		PointsRecordExample example = new PointsRecordExample();
		Criteria Criteria = example.createCriteria();
		if(map.containsKey("fromMem")){
			Criteria.andFromMemEqualTo((String)map.get("fromMem"));
		}
		if(map.containsKey("toMem")){
			Criteria.andFromMemEqualTo((String)map.get("toMem"));
		}
		return pointsRecordMapper.countByExample(example);
	}

	@Override
	public List<PointsRecord> getRcordByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pointsRecordMapper.selPointsRecordByPage(map);
	}

	@Override
	public void addPointsRecord(PointsRecord record) {
		// TODO Auto-generated method stub
		pointsRecordMapper.insert(record);
	}




}
