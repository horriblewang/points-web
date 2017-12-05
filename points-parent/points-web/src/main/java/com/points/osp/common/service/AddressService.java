package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.AddressInfoDto;
import com.bailian.entity.AddressInfo;

public interface AddressService {
	
	
	/**
	 * 分页查询
	 * @param queryMap
	 * @return
	 */
	public List<AddressInfoDto> getListByPage(Map<String, Object> queryMap);
	
	
	/**
	 * 分页查询数量
	 * @param map
	 * @return
	 */
	public int countAddress(Map<String, String> map);
	
	/**
	 * 查询地址列表
	 * @param map
	 * @return
	 */
	public List<AddressInfo> getList(Long memberId);
	
	
	/**
	 * 新增或者修改
	 * @param addressInfo
	 */
	public void saveOrUpdate(AddressInfo addressInfo);
	
	
	/**
	 * 设置默认地址
	 * @param addressInfo
	 */
	public void setDefaultAdd(AddressInfo addressInfo);


	/**
	 * 删除地址
	 * @param addId
	 */
	public void delAddress(Long addId);

}
