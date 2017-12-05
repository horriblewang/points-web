package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.dto.AwardsRecordDto;
import com.bailian.entity.AwardsRecord;
import com.bailian.persistence.AwardsRecordMapper;
import com.points.osp.common.service.AwardsService;

@Service
public class AwardsServiceImpl implements AwardsService {
	
	@Autowired
	private AwardsRecordMapper recordMapper;

	public List<AwardsRecordDto> getAwardsByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return recordMapper.selAwardsByPage(map);
	}

	public void userAwards(Long awardsId) {
		// TODO Auto-generated method stub
		AwardsRecord record = new AwardsRecord();
		record.setId(awardsId);
		record.setStatus("1");
		record.setUseTime(new Timestamp(System.currentTimeMillis()));
		recordMapper.updateByPrimaryKeySelective(record);
	}

	public int getAwardsCountByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return recordMapper.countAwards(map);
	}

}
