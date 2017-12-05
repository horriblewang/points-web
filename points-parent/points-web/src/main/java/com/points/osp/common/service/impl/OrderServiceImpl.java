package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.dto.OrderInfoDto;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.OrderDetail;
import com.bailian.entity.OrderDetailExample;
import com.bailian.entity.OrderInfo;
import com.bailian.entity.OrderInfoExample;
import com.bailian.entity.RuleInfo;
import com.bailian.persistence.MemberInfoMapper;
import com.bailian.persistence.OrderDetailMapper;
import com.bailian.persistence.OrderInfoMapper;
import com.bailian.persistence.RuleInfoMapper;
import com.points.osp.common.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private OrderDetailMapper orderDetailMapper;

	@Autowired
	private RuleInfoMapper ruleInfoMapper;

	@Autowired
	private MemberInfoMapper memberInfoMapper;

	public int countOrder(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return orderInfoMapper.countOrderInfo(paraMap);
	}

	public List<OrderInfoDto> getOrderByPage(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return orderInfoMapper.selOrderInfoByPage(paraMap);
	}

	public void addOrderInfo(OrderInfo orderInfo, String orderDetail) {
		// TODO Auto-generated method stubs
		Timestamp time = new Timestamp(System.currentTimeMillis());
		JSONArray jsonArray = JSONArray.fromObject(orderDetail);
		orderInfo.setCreateTime(time);
		orderInfoMapper.insert(orderInfo);
		List<OrderDetail> details = JSONArray.toList(jsonArray,
				new OrderDetail(), new JsonConfig());
		for (OrderDetail dtoDetail : details) {
			dtoDetail.setOrderId(orderInfo.getOrderId());
			dtoDetail.setCreateTime(time);
			orderDetailMapper.insert(dtoDetail);
		}
	}

	public List<OrderDetail> getOrderDetail(Long orderId) {
		// TODO Auto-generated method stub
		OrderDetailExample example = new OrderDetailExample();
		example.createCriteria().andOrderIdEqualTo(orderId);
		return orderDetailMapper.selectByExample(example);
	}

	public void addReferPoints(OrderInfo orderInfo) {
		// TODO Auto-generated method stub
		Long memId = orderInfo.getMemberId();
		// 会员本身
		MemberInfo mem = memberInfoMapper.selectByPrimaryKey(memId);
		// 邀请人奖励积分
		if (mem.getReferTo() != null) {
			String mobile = mem.getReferTo();
			MemberInfo secMem = memberInfoMapper.selectByMobile(mobile);
			RuleInfo rule = ruleInfoMapper.selectByPrimaryKey(orderInfo
					.getRuleId());
			if (secMem != null && rule != null) {
				// 一级推荐奖励
				Long firstRate = rule.getFirstRate();
				long addPoints = orderInfo.getPoints() * firstRate / 100l;
				memberInfoMapper.addMemPointsByMobile(mobile, addPoints);

				// 二级推荐奖励
				String secMobile = secMem.getReferTo();
				if (secMobile != null) {
					Long secRate = rule.getSecRate();
					long secAddPoints = orderInfo.getPoints() * secRate / 100l;
					memberInfoMapper.addMemPointsByMobile(secMobile,
							secAddPoints);
				}
			}

		}

	}

}
