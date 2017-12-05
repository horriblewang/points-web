package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.dto.AddressInfoDto;
import com.bailian.entity.AddressInfo;

public interface AddressService {
	
	
	/**
	 * ��ҳ��ѯ
	 * @param queryMap
	 * @return
	 */
	public List<AddressInfoDto> getListByPage(Map<String, Object> queryMap);
	
	
	/**
	 * ��ҳ��ѯ����
	 * @param map
	 * @return
	 */
	public int countAddress(Map<String, String> map);
	
	/**
	 * ��ѯ��ַ�б�
	 * @param map
	 * @return
	 */
	public List<AddressInfo> getList(Long memberId);
	
	
	/**
	 * ���������޸�
	 * @param addressInfo
	 */
	public void saveOrUpdate(AddressInfo addressInfo);
	
	
	/**
	 * ����Ĭ�ϵ�ַ
	 * @param addressInfo
	 */
	public void setDefaultAdd(AddressInfo addressInfo);


	/**
	 * ɾ����ַ
	 * @param addId
	 */
	public void delAddress(Long addId);

}
