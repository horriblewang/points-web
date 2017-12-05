package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.OrderInfoDto;
import com.bailian.entity.OrderDetail;
import com.bailian.entity.OrderInfo;

public interface OrderService {
	
	
	/**
	 * ��ҳ��ѯ
	 * @param paraMap
	 * @return
	 */
	public int countOrder(Map<String, Object> paraMap);
	
	/**
	 * ��ҳ��ѯ
	 * @param paraMap
	 * @return
	 */
	public List<OrderInfoDto> getOrderByPage(Map<String, Object> paraMap);
	
	
	/**
	 * ��������
	 * @param orderInfo
	 * @param orderDetail
	 */
	public void addOrderInfo(OrderInfo orderInfo,String orderDetail);

	/**
	 * ��ѯ������ϸ
	 * @param orderId
	 * @return
	 */
	public List<OrderDetail> getOrderDetail(Long orderId);

	/**
	 * �����Ƽ�����
	 * @param orderInfo
	 */
	public void addReferPoints(OrderInfo orderInfo);

}
