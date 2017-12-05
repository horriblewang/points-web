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
import com.points.osp.common.service.ExchangeService;

@Service
public class ExchangeServiceImpl implements ExchangeService {
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private ActivityDetailMapper detailMapper;

	public int countExchange(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		paraMap.put("hotSale", "0");//0积分兑换，1转盘抽奖
		ActivityExample example = new ActivityExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andHotSaleEqualTo("0");//
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

	public List<ActivityDto> selExchangeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("hotSale", "0");
		return activityMapper.selActivityByPage(map);
	}

	public Activity getExchangeById(Long actId) {
		// TODO Auto-generated method stub
		return activityMapper.selectByPrimaryKey(actId);
	}

	public List<ActivityDetailDto> getExchangeDetailById(Long actId) {
		// TODO Auto-generated method stub
		return detailMapper.getActDetailList(actId);
	}

	public void addExchange(Activity avtivity, String deail) {
		// TODO Auto-generated method stub
		avtivity.setStatus("1");
		avtivity.setHotSale("0");
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

	public void updateExchange(Activity avtivity, String deail) {
		// TODO Auto-generated method stub
		Timestamp time = new Timestamp(System.currentTimeMillis());
		avtivity.setUpdateTime(time);
		avtivity.setHotSale("0");//0积分兑换
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

	public void disableExchange(Long actId) {
		// TODO Auto-generated method stub
		Activity record = new Activity();
		record.setActId(actId);
		record.setStatus("0");
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		activityMapper.updateByPrimaryKeySelective(record);
		
	}

	public void enableExchange(Long actId) {
		// TODO Auto-generated method stub
		Activity record = new Activity();
		record.setActId(actId);
		record.setStatus("1");
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		activityMapper.updateByPrimaryKeySelective(record);
		
	}

	public Long checkExchangeTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("hotSale", "0");//积分兑换
		return activityMapper.checkActTime(map);
	}

	

}
