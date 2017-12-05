package com.bailian.persistence;

import com.bailian.dto.AddressInfoDto;
import com.bailian.entity.AddressInfo;
import com.bailian.entity.AddressInfoExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AddressInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    int countByExample(AddressInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    int deleteByExample(AddressInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    int deleteByPrimaryKey(Long addId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    int insert(AddressInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    int insertSelective(AddressInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    List<AddressInfo> selectByExample(AddressInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    AddressInfo selectByPrimaryKey(Long addId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    int updateByExampleSelective(@Param("record") AddressInfo record, @Param("example") AddressInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    int updateByExample(@Param("record") AddressInfo record, @Param("example") AddressInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    int updateByPrimaryKeySelective(AddressInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_address_info
     *
     * @mbggenerated Thu Jun 22 21:57:40 CST 2017
     */
    int updateByPrimaryKey(AddressInfo record);

    /**
     * ��ѯ��ַ
     * @param map
     * @return
     */
	List<AddressInfo> getAddList(Long memberId);

	/**
	 * ��ѯ��ҳ
	 * @param map
	 * @return
	 */
	List<AddressInfoDto> selAddByPage(Map<String, Object> map);

	/**
	 * ����Ĭ�ϵ�ַ
	 * @param addressInfo
	 */
	void setDefault(@Param("record")AddressInfo addressInfo);
}