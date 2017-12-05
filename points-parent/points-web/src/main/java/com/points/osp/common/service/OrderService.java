package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.OrderInfoDto;
import com.bailian.entity.OrderDetail;
import com.bailian.entity.OrderInfo;

public interface OrderService {
	
	
	/**
	 * 分页查询
	 * @param paraMap
	 * @return
	 */
	public int countOrder(Map<String, Object> paraMap);
	
	/**
	 * 分页查询
	 * @param paraMap
	 * @return
	 */
	public List<OrderInfoDto> getOrderByPage(Map<String, Object> paraMap);
	
	
	/**
	 * 新增订单
	 * @param orderInfo
	 * @param orderDetail
	 */
	public void addOrderInfo(OrderInfo orderInfo,String orderDetail);

	/**
	 * 查询订单明细
	 * @param orderId
	 * @return
	 */
	public List<OrderDetail> getOrderDetail(Long orderId);

	/**
	 * 增加推荐奖励
	 * @param orderInfo
	 */
	public void addReferPoints(OrderInfo orderInfo);

}
