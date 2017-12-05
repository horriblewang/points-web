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
 * ��ѡ����
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
		//��ѡ����
		String selType = (String)map.get("selType");
		if(StringUtils.isNotBlank(selType)){
			createCriteria.andSelTypeEqualTo(selType);
		}
		//����ɸѡ
		String selName = (String)map.get("selName");
		if(StringUtils.isNotBlank(selName)){
			createCriteria.andSelNameLike("%" + selName +"%");
		}
		//״̬
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
		selected.setStatus("1");//��Ч
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
		record.setStatus("1");//��Ч
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
		
		createCriteria.andStatusEqualTo("1");//��Ч��״̬
		//��ѡ����
		String selType = (String)map.get("goodsType");
		if(StringUtils.isNotBlank(selType)){
			createCriteria.andSelTypeEqualTo(selType);
		}
		//����ɸѡ
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
			resMap.put("msg", "��Ա��Ϣ�����ڣ����¼��");
			return resMap;
		}
		
		Selected selected = selectedMapper.selectByPrimaryKey(Long.valueOf(selId));
		if(selected == null){
			resMap.put("code", "0");
			resMap.put("msg", "�һ���Ʒ�����ڣ�");
			return resMap;
		}
		
		Long sumPoints = Long.valueOf(selected.getSelPrice());
		//У������Ƿ����
		if(sumPoints*num  > member.getPoints()){
			resMap.put("code", "0");
			resMap.put("msg", "���ֲ��㣬�޷��һ�����ѡ��������Ʒ��");
			return resMap;
		}
		//ȥ���ͻ�����
		memberInfoMapper.addMemPointsByMobile(member.getMobile(), -1 * sumPoints*num);
				
		//�������ҵĶһ�
		GoodsRecord exgoods = new GoodsRecord(); 
		exgoods.setCreateTime(time);
		exgoods.setGoodsId(selected.getSelId());//�ƹ�����Ƕһ�������ID
		exgoods.setGoodsName(selected.getSelName());
		exgoods.setGoodsNum(num);
		exgoods.setGoodsPoints(sumPoints);
		exgoods.setMemberId(Long.valueOf(memberId));
		exgoods.setGoodsType(GOODS_TYPE_ENUM.SELECTED.getType());
		exgoods.setGoodsPic(selected.getSelPics().split(";")[0]);
		
		//�һ��ɹ�
	    goodsRecordMapper.insert(exgoods);
			
		
		//�һ���ͬʱ������һ�������������Զ����ӻ���
		OrderInfo record = new OrderInfo();
		record.setCreateTime(time);
		record.setMemberId(Long.valueOf(memberId));
		record.setPoints(sumPoints*num);
		record.setRuleId(Long.valueOf(ruleId));//�̶�����
		orderInfoMapper.insert(record);
		
		//������ϸ
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
