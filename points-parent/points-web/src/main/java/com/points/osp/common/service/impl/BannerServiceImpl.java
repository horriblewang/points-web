package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bailian.entity.Banner;
import com.bailian.entity.BannerExample;
import com.bailian.entity.BannerExample.Criteria;
import com.bailian.entity.GoodsRecord;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.OrderDetail;
import com.bailian.entity.OrderInfo;
import com.bailian.persistence.BannerMapper;
import com.bailian.persistence.GoodsRecordMapper;
import com.bailian.persistence.MemberInfoMapper;
import com.bailian.persistence.OrderDetailMapper;
import com.bailian.persistence.OrderInfoMapper;
import com.points.osp.common.service.BannerService;
import com.points.osp.common.utils.GOODS_TYPE_ENUM;

@Service
public class BannerServiceImpl implements BannerService {
	
	@Value("${points.default.rule}")
	private String ruleId;
	
	
	@Autowired
	private BannerMapper bannerMapper;
	
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	
	@Autowired
	private GoodsRecordMapper goodsRecordMapper;
	
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;

	public int countBanner(Map<String, Object> map) {
		// TODO Auto-generated method stub
		BannerExample example = new BannerExample(); 
		Criteria createCriteria = example.createCriteria();
		if(map.containsKey("banName")){
			createCriteria.andBanNameLike("%" + (String)map.get("banName") +"%");
		}
		String status = (String)map.get("status");
		if(StringUtils.isNotBlank(status)){
			createCriteria.andStatusEqualTo(status);
		}
		return bannerMapper.countByExample(example);
	}

	public List<Banner> selBannerByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bannerMapper.selByPage(map);
	}

	public Banner getBannerById(Long banId) {
		// TODO Auto-generated method stub
		return bannerMapper.selectByPrimaryKey(banId);
	}

	public void addBanner(Banner banner) {
		// TODO Auto-generated method stub
		banner.setStatus("1");//��Ч
		banner.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bannerMapper.insertSelective(banner);
	}

	public void updateBanner(Banner banner) {
		// TODO Auto-generated method stub
		bannerMapper.updateByPrimaryKeySelective(banner);
	}

	public void enableBan(Long banId) {
		// TODO Auto-generated method stub
		Banner record = new Banner();
		record.setBanId(banId);
		record.setStatus("1");
		bannerMapper.updateByPrimaryKeySelective(record);
	}

	public void disableBan(Long banId) {
		// TODO Auto-generated method stub
		Banner record = new Banner();
		record.setBanId(banId);
		record.setStatus("0");
		bannerMapper.updateByPrimaryKeySelective(record);
	}

	public List<Banner> getBannerForApp() {
		// TODO Auto-generated method stub
		BannerExample example = new BannerExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andStatusEqualTo("1");
		example.setOrderByClause("ORDER_SEC ASC");
		return bannerMapper.selectByExample(example);
	}

	@Override
	public Map<String, Object> tradeBanner(String memberId, String banId) {
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
		
		Banner banner = bannerMapper.selectByPrimaryKey(Long.valueOf(banId));
		if(banner == null){
			resMap.put("code", "0");
			resMap.put("msg", "�һ���Ʒ�����ڣ�");
			return resMap;
		}
		
		Long sumPoints = Long.valueOf(banner.getReserve1());
		//У������Ƿ����
		if(sumPoints  > member.getPoints()){
			resMap.put("code", "0");
			resMap.put("msg", "���ֲ��㣬�޷��һ�����ѡ��������Ʒ��");
			return resMap;
		}
		//ȥ���ͻ�����
		memberInfoMapper.addMemPointsByMobile(member.getMobile(), -1 * sumPoints);
				
		//�������ҵĶһ�
		GoodsRecord exgoods = new GoodsRecord(); 
		exgoods.setCreateTime(time);
		exgoods.setGoodsId(banner.getBanId());//�ƹ�����Ƕһ�������ID
		exgoods.setGoodsName(banner.getBanName());
		exgoods.setGoodsNum(1l);
		exgoods.setGoodsPoints(sumPoints);
		exgoods.setMemberId(Long.valueOf(memberId));
		exgoods.setGoodsType(GOODS_TYPE_ENUM.BANNER.getType());
		exgoods.setGoodsPic(banner.getBanUrl());
		
		//�һ��ɹ�
	    goodsRecordMapper.insert(exgoods);
			
		
		//�һ���ͬʱ������һ�������������Զ����ӻ���
		OrderInfo record = new OrderInfo();
		record.setCreateTime(time);
		record.setMemberId(Long.valueOf(memberId));
		record.setPoints(sumPoints);
		record.setRuleId(Long.valueOf(ruleId));//�̶�����
		orderInfoMapper.insert(record);
		
		//������ϸ
		OrderDetail detailRecord = new OrderDetail();
		detailRecord.setCreateTime(time);
		detailRecord.setGoodsId(banner.getBanId());
		detailRecord.setGoodsNum(1l);
		detailRecord.setGoodsName(banner.getBanName());
		detailRecord.setGoodsPoints(sumPoints);
		detailRecord.setOrderId(record.getOrderId());
		orderDetailMapper.insert(detailRecord);
		
		resMap.put("orderInfo", record);
		return resMap;
	
	}

}
