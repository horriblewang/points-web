package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bailian.entity.Banner;
import com.bailian.entity.Goods;
import com.bailian.entity.GoodsRecord;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.OrderDetail;
import com.bailian.entity.OrderInfo;
import com.bailian.entity.Selected;
import com.bailian.entity.SelectedExample;
import com.bailian.entity.SelectedExample.Criteria;
import com.bailian.persistence.BannerMapper;
import com.bailian.persistence.GoodsRecordMapper;
import com.bailian.persistence.MemberInfoMapper;
import com.bailian.persistence.OrderDetailMapper;
import com.bailian.persistence.OrderInfoMapper;
import com.bailian.persistence.SelectedMapper;
import com.points.osp.common.service.SelectedService;
import com.points.osp.common.utils.GOODS_TYPE_ENUM;

/**
 * 精选配置
 * @author wbwangsh
 *
 */
@Service
public class SelectedServiceImpl implements SelectedService {
	
	@Value("${points.default.rule}")
	private String ruleId;
	
	@Autowired
	private SelectedMapper selectedMapper;

	@Autowired
	private MemberInfoMapper memberInfoMapper;
	
	@Autowired
	private BannerMapper bannerMapper;
	
	@Autowired
	private GoodsRecordMapper goodsRecordMapper;
	
	
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	
	public int countSelected(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SelectedExample example = new SelectedExample(); 
		Criteria createCriteria = example.createCriteria();
		//精选分类
		String selType = (String)map.get("selType");
		if(StringUtils.isNotBlank(selType)){
			createCriteria.andSelTypeEqualTo(selType);
		}
		//名字筛选
		String selName = (String)map.get("selName");
		if(StringUtils.isNotBlank(selName)){
			createCriteria.andSelNameLike("%" + selName +"%");
		}
		//状态
		String status = (String)map.get("status");
		if(StringUtils.isNotBlank(status)){
			createCriteria.andStatusEqualTo(status);
		}
		return selectedMapper.countByExample(example);
	}

	public List<Selected> selSelectedByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return selectedMapper.getSelectedByPage(map);
	}

	public Selected getSelectedById(Long banId) {
		// TODO Auto-generated method stub
		return selectedMapper.selectByPrimaryKey(banId);
	}

	public void addSelected(Selected selected) {
		// TODO Auto-generated method stub
		selected.setStatus("1");//有效
		selected.setCreateTime(new Timestamp(System.currentTimeMillis()));
		selectedMapper.insertSelective(selected);
	}

	public void updateSelected(Selected selected) {
		// TODO Auto-generated method stub
		selectedMapper.updateByPrimaryKeySelective(selected);
	}

	public void enableSelected(Long selId) {
		// TODO Auto-generated method stub
		Selected record = new Selected();
		record.setSelId(selId);
		record.setStatus("1");//有效
		selectedMapper.updateByPrimaryKeySelective(record);
	}

	public void disableSelected(Long selId) {
		// TODO Auto-generated method stub
		Selected record = new Selected();
		record.setSelId(selId);
		record.setStatus("0");
		selectedMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Selected> getSelectedForApp(Map<String, String> map) {
		// TODO Auto-generated method stub
		SelectedExample example = new SelectedExample(); 
		Criteria createCriteria = example.createCriteria();
		
		createCriteria.andStatusEqualTo("1");//有效的状态
		//精选分类
		String selType = (String)map.get("goodsType");
		if(StringUtils.isNotBlank(selType)){
			createCriteria.andSelTypeEqualTo(selType);
		}
		//名字筛选
		String selName = (String)map.get("goodsName");
		if(StringUtils.isNotBlank(selName)){
			createCriteria.andSelNameLike("%" + selName +"%");
		}
		example.setOrderByClause("ORDER_SEC ASC");
		
		return selectedMapper.selectByExample(example);
	}

	@Override
	public Map<String, Object> tradeSelected(String memberId, String selId, Long num) {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		resMap.put("code", "1");
		MemberInfo member = memberInfoMapper.selectByPrimaryKey(Long.valueOf(memberId));
		if(member == null){
			resMap.put("code", "0");
			resMap.put("msg", "会员信息不存在，请登录！");
			return resMap;
		}
		
		Selected selected = selectedMapper.selectByPrimaryKey(Long.valueOf(selId));
		if(selected == null){
			resMap.put("code", "0");
			resMap.put("msg", "兑换商品不存在！");
			return resMap;
		}
		
		Long sumPoints = Long.valueOf(selected.getSelPrice());
		//校验积分是否充足
		if(sumPoints*num  > member.getPoints()){
			resMap.put("code", "0");
			resMap.put("msg", "积分不足，无法兑换，请选择其他礼品！");
			return resMap;
		}
		//去除客户积分
		memberInfoMapper.addMemPointsByMobile(member.getMobile(), -1 * sumPoints*num);
				
		//增加至我的兑换
		GoodsRecord exgoods = new GoodsRecord(); 
		exgoods.setCreateTime(time);
		exgoods.setGoodsId(selected.getSelId());//推广或者是兑换的主键ID
		exgoods.setGoodsName(selected.getSelName());
		exgoods.setGoodsNum(num);
		exgoods.setGoodsPoints(sumPoints);
		exgoods.setMemberId(Long.valueOf(memberId));
		exgoods.setGoodsType(GOODS_TYPE_ENUM.SELECTED.getType());
		exgoods.setGoodsPic(selected.getSelPics().split(";")[0]);
		
		//兑换成功
	    goodsRecordMapper.insert(exgoods);
			
		
		//兑换的同时，生产一个订单，方便自动增加积分
		OrderInfo record = new OrderInfo();
		record.setCreateTime(time);
		record.setMemberId(Long.valueOf(memberId));
		record.setPoints(sumPoints*num);
		record.setRuleId(Long.valueOf(ruleId));//固定规则
		orderInfoMapper.insert(record);
		
		//订单明细
		OrderDetail detailRecord = new OrderDetail();
		detailRecord.setCreateTime(time);
		detailRecord.setGoodsId(selected.getSelId());
		detailRecord.setGoodsNum(num);
		detailRecord.setGoodsName(selected.getSelName());
		detailRecord.setGoodsPoints(sumPoints);
		detailRecord.setOrderId(record.getOrderId());
		orderDetailMapper.insert(detailRecord);
		
		resMap.put("orderInfo", record);
		return resMap;
	}



}
