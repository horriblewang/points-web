package com.bailian.persistence;

import com.bailian.dto.OrderInfoDto;
import com.bailian.entity.OrderInfo;
import com.bailian.entity.OrderInfoExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OrderInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    int countByExample(OrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    int deleteByExample(OrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    int deleteByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    int insert(OrderInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    int insertSelective(OrderInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    List<OrderInfo> selectByExample(OrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    OrderInfo selectByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    int updateByExampleSelective(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    int updateByExample(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    int updateByPrimaryKeySelective(OrderInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_info
     *
     * @mbggenerated Fri Jun 02 22:22:04 CST 2017
     */
    int updateByPrimaryKey(OrderInfo record);
    
    
    /**
     * ��ѯ����
     * @param paraMap
     * @return
     */
    int countOrderInfo(Map<String, Object> paraMap);

    /**
     * ��ҳ��ѯ
     * @param paraMap
     * @return
     */
	List<OrderInfoDto> selOrderInfoByPage(Map<String, Object> paraMap);
}