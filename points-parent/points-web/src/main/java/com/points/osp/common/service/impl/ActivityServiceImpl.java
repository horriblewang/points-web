package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.dto.ActivityDetailDto;
import com.bailian.dto.ActivityDto;
import com.bailian.entity.Activity;
import com.bailian.entity.ActivityDetail;
import com.bailian.entity.ActivityDetailExample;
import com.bailian.entity.ActivityExample;
import com.bailian.entity.ActivityExample.Criteria;
import com.bailian.persistence.ActivityDetailMapper;
import com.bailian.persistence.ActivityMapper;
import com.points.osp.common.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private ActivityDetailMapper detailMapper;

	public Integer countActivity(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		paraMap.put("hotSale", "1");//0兑换,1抽奖 ，
		ActivityExample example = new ActivityExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andHotSaleEqualTo("1");
		if(paraMap.containsKey("actName")){
			String actName = (String)paraMap.get("actName");
			Criteria.andActNameLike("%" + actName + "%");
		}
		if(paraMap.containsKey("processStatus")){
			String processStatus = (String)paraMap.get("processStatus");
			if(StringUtils.isNotBlank(processStatus)){
				Timestamp time = new Timestamp(System.currentTimeMillis());
				if("01".equals(processStatus)){//待运行
					Criteria.andStartTimeGreaterThan(time);
				}else if("02".equals(processStatus)){//运行中
					Criteria.andStartTimeLessThan(time);
					Criteria.andEndTimeGreaterThan(time);
				}else if("03".equals(processStatus)){//已过期
					Criteria.andEndTimeLessThan(time);
				}
			}
		}
		return activityMapper.countByExample(example);
	}

	public List<ActivityDto> getActivityByPage(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return activityMapper.selActivityByPage(paraMap);
	}

	public Activity getActivityById(Long actId) {
		// TODO Auto-generated method stub
		return activityMapper.selectByPrimaryKey(actId);
	}

	public void addActivity(Activity avtivity, String deail) {
		// TODO Auto-generated method stub
		avtivity.setStatus("1");
		avtivity.setHotSale("1");//1转盘抽奖
		Timestamp time = new Timestamp(System.currentTimeMillis());
		avtivity.setCreateTime(time);
		avtivity.setUpdateTime(time);
		activityMapper.insert(avtivity);
		if (StringUtils.isNotBlank(deail)) {
			JSONArray jsonObject = JSONArray.fromObject(deail);
			List<ActivityDetail> goods = JSONArray.toList(jsonObject, new ActivityDetail(),new JsonConfig());
			for(ActivityDetail detail : goods){
				detail.setCreateTime(time);
				detail.setActId(avtivity.getActId());
			}
			for(ActivityDetail good:goods){
				detailMapper.insertSelective(good);
			}
			//detailMapper.batchInsertDetail(goods);
		}
	}

	public void updateActivity(Activity avtivity, String deail) {
		// TODO Auto-generated method stub
		Timestamp time = new Timestamp(System.currentTimeMillis());
		avtivity.setUpdateTime(time);
		avtivity.setHotSale("1");//1转盘抽奖
		activityMapper.updateByPrimaryKeySelective(avtivity);
		ActivityDetailExample example = new ActivityDetailExample();
		example.createCriteria().andActIdEqualTo(avtivity.getActId());
		detailMapper.deleteByExample(example);
		if (StringUtils.isNotBlank(deail)) {
			JSONArray jsonObject = JSONArray.fromObject(deail);
			List<ActivityDetail> goods = JSONArray.toList(jsonObject, new ActivityDetail(),new JsonConfig());
			for(ActivityDetail detail : goods){
				detail.setCreateTime(time);
				detail.setActId(avtivity.getActId());
			}
			for(ActivityDetail good:goods){
				detailMapper.insertSelective(good);
			}
			//detailMapper.batchInsertDetail(goods);		
		}
	}

	public List<ActivityDetailDto> getActivityDetailById(Long actId) {
		// TODO Auto-generated method stub
		return detailMapper.getActDetailList(actId);
	}

	public void disableAct(Long actId) {
		// TODO Auto-generated method stub
		Activity record = new Activity();
		record.setActId(actId);
		record.setStatus("0");
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		activityMapper.updateByPrimaryKeySelective(record);
	}

	public void enableAct(Long actId) {
		// TODO Auto-generated method stub
		Activity record = new Activity();
		record.setActId(actId);
		record.setStatus("1");
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		activityMapper.updateByPrimaryKeySelective(record);
		
	}

	public Long checkActTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("hotSale", "1");//转盘抽奖
		return activityMapper.checkActTime(map);
	}

	

}
