package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bailian.dto.ActivityDetailDto;
import com.bailian.entity.Activity;
import com.bailian.entity.AwardsRecord;
import com.bailian.entity.Goods;
import com.bailian.entity.GoodsRecord;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.OrderDetail;
import com.bailian.entity.OrderInfo;
import com.bailian.persistence.ActivityDetailMapper;
import com.bailian.persistence.ActivityMapper;
import com.bailian.persistence.AwardsRecordMapper;
import com.bailian.persistence.GoodsMapper;
import com.bailian.persistence.GoodsRecordMapper;
import com.bailian.persistence.MemberInfoMapper;
import com.bailian.persistence.OrderDetailMapper;
import com.bailian.persistence.OrderInfoMapper;
import com.bailian.utils.DateUtils;
import com.google.common.collect.Maps;
import com.ibm.icu.text.SimpleDateFormat;
import com.points.osp.common.service.ActivityApiService;
import com.points.osp.common.utils.ACT_ENUM;
import com.points.osp.common.utils.Constant;
import com.points.osp.common.utils.GOODS_TYPE_ENUM;
import com.points.osp.common.utils.RedisUtil;

@Service
public class ActivityApiServiceImpl implements ActivityApiService {
	
	//控制抽奖基数
	private static int SHAKE_BASE = 10000;
	
	@Value("${points.default.rule}")
	private String ruleId;
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private ActivityDetailMapper detailMapper;
	
	@Autowired
	private AwardsRecordMapper awardsMapper;
	
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private GoodsRecordMapper goodsRecordMapper;
	
	@Autowired
	private RedisUtil redisUtil;
	
	
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;

	public Map<String, Object> getLuckyDraw() {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = Maps.newHashMap();
		resMap.put("code", "1");
		Activity activity = activityMapper.getRunningAct(ACT_ENUM.LUCKY_DRAW.getType());
		if(activity != null){
			resMap.put("act", activity);
			List<ActivityDetailDto> details = detailMapper.getActDetailList(activity.getActId());
			//判断总概率小于100，加上未中奖商品
			long sum = 0l;
			for(ActivityDetailDto dto : details){
				sum += dto.getPercent();
			}
			if(sum < 100l){
				ActivityDetailDto noReward = new ActivityDetailDto();
				noReward.setActId(activity.getActId());
				noReward.setDetailId(0l);
				noReward.setGoodsId(0l);
				noReward.setGoodsName("未中奖");
				details.add(noReward);
			}
			resMap.put("prizes", details);
			resMap.put("total", details.size());
		}else{
			resMap.put("code", "0");	
			resMap.put("code", "没有有效的抽奖活动！");	
			return resMap;
		}
		return resMap;
	}

	public Map<String, Object> shakeForGift(Long actId,Long memberId) {
		// TODO Auto-generated method stub
		Map<String, Object> resp = Maps.newHashMap();
		Activity activity = activityMapper.getRunningAct(ACT_ENUM.LUCKY_DRAW.getType());
		if(actId.longValue() != activity.getActId().longValue()){
			resp.put("code", "0");
			resp.put("msg", "抽奖活动已过期，请刷新重试！");
			return resp;
		}
		String shakeTime = redisUtil.get(Constant.SHAKE_TIME_KEY + memberId, "0");
		int userTime = Integer.valueOf(shakeTime);
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Date expireDate = DateUtils.parseDate(sdf.format(nowDate));
		int expireSec = (int) ((expireDate.getTime() - nowDate.getTime())/1000);
		redisUtil.set(Constant.SHAKE_TIME_KEY + memberId, String.valueOf(++userTime), expireSec);
		List<ActivityDetailDto> details = detailMapper.getActDetailList(actId);

		Random random = new Random();
		int seedNum = random.nextInt(SHAKE_BASE) + 1;
		int addScope = 0;
		boolean flag = false;
		for(ActivityDetailDto dto : details){
			long num = dto.getPercent() * SHAKE_BASE / 100;
			if(seedNum <= addScope + num){
				Timestamp time = new Timestamp(System.currentTimeMillis());
				flag = true;
				Goods goods = goodsMapper.selectByPrimaryKey(dto.getGoodsId());
				String goodsType = goods.getPcPicUrl();
				AwardsRecord record = new AwardsRecord();
				record.setMemberId(memberId);
				record.setActId(actId);
				record.setGoodsId(dto.getGoodsId());
				record.setCreateTime(time);
				if("2".equals(goodsType)){
					record.setUseTime(time);
					record.setStatus("1");	
					memberInfoMapper.LoginSendPoints(memberId, goods.getGoodsPoints());
				}else{
					record.setStatus("0");	
				}
				awardsMapper.insert(record);
				resp.put("code", "1");
				resp.put("goodsId", dto.getGoodsId());
				resp.put("memberId", memberId);
				return resp;
			}
			addScope += num;
		}
		if(!flag){
			resp.put("code", "1");
			resp.put("goodsId", 0l);//0代表没有中奖
			resp.put("memberId", memberId);
		}
		return resp;
	}

	public Map<String, Object> getExchangeAct() {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = Maps.newHashMap();
		resMap.put("code", "1");
		Activity activity = activityMapper.getRunningAct(ACT_ENUM.EXG_GOODS.getType());
		if(activity != null){
			resMap.put("act", activity);
			List<ActivityDetailDto> details = detailMapper.getActDetailList(activity.getActId());
			resMap.put("prizes", details);
			resMap.put("total", details.size());
		}else{
			resMap.put("code", "0");	
			resMap.put("code", "没有有效的兑换活动！");	
			return resMap;
		}
		return resMap;
	}

	public Map<String, Object> exchangeGoods(String memberId, String goods,
			String goodsNum) {
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
		String[] goodsIds = goods.split(",");
		String[] goodsNums = goodsNum.split(",");
		List<GoodsRecord> list = new ArrayList<GoodsRecord>();
		List<OrderDetail> detaillist = new ArrayList<OrderDetail>();
		Long sumPoints = 0l;
		for(int i = 0;i < goodsIds.length;i++){
			Goods record = goodsMapper.selectByPrimaryKey(Long.valueOf(goodsIds[i]));
			GoodsRecord exgoods = new GoodsRecord(); 
			exgoods.setCreateTime(time);
			exgoods.setGoodsId(record.getGoodsId());
			exgoods.setGoodsName(record.getGoodsName());
			exgoods.setGoodsNum(Long.valueOf(goodsNums[i]));
			exgoods.setGoodsPoints(record.getGoodsPoints());
			exgoods.setMemberId(Long.valueOf(memberId));
			exgoods.setGoodsType(GOODS_TYPE_ENUM.COMMON.getType());
			exgoods.setGoodsPic(record.getAppPicUrl());
			list.add(exgoods);
			
			//订单明细
			OrderDetail detailRecord = new OrderDetail();
			detailRecord.setCreateTime(time);
			detailRecord.setGoodsId(record.getGoodsId());
			detailRecord.setGoodsNum(Long.valueOf(goodsNums[i]));
			detailRecord.setGoodsName(record.getGoodsName());
			detailRecord.setGoodsPoints(record.getGoodsPoints());
			detaillist.add(detailRecord);
			
			sumPoints += record.getGoodsPoints() * Long.valueOf(goodsNums[i]);
		}
		//校验积分是否充足
		if(sumPoints > member.getPoints()){
			resMap.put("code", "0");
			resMap.put("msg", "积分不足，无法兑换，请选择其他礼品！");
			return resMap;
		}
		//去除客户积分
		memberInfoMapper.addMemPointsByMobile(member.getMobile(), -1 * sumPoints);
		//兑换成功
		for(GoodsRecord goodsRecord : list){
			goodsRecordMapper.insert(goodsRecord);
		}
		
		//兑换的同时，生产一个订单，方便自动增加积分
		OrderInfo record = new OrderInfo();
		record.setCreateTime(time);
		record.setMemberId(Long.valueOf(memberId));
		record.setPoints(sumPoints);
		record.setRuleId(Long.valueOf(ruleId));
		orderInfoMapper.insert(record);
		//订单明细信息
		for(OrderDetail orderDetail : detaillist){
			orderDetail.setOrderId(record.getOrderId());
			orderDetailMapper.insert(orderDetail);
		}
		resMap.put("orderInfo", record);
		return resMap;
	}

}
