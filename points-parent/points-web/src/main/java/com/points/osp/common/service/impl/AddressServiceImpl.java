package com.points.osp.common.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bailian.dto.AddressInfoDto;
import com.bailian.entity.AddressInfo;
import com.bailian.entity.AddressInfoExample;
import com.bailian.persistence.AddressInfoMapper;
import com.points.osp.common.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressInfoMapper addressInfoMapper;

	public List<AddressInfo> getList(Long memberId) {
		// TODO Auto-generated method stub
		return addressInfoMapper.getAddList(memberId);
	}

	public void saveOrUpdate(AddressInfo addressInfo) {
		// TODO Auto-generated method stub
		Timestamp time = new Timestamp(System.currentTimeMillis());
		if (addressInfo.getAddId() != null) {
			addressInfo.setUpdateTime(time);
			addressInfoMapper.updateByPrimaryKey(addressInfo);
		} else {
			addressInfo.setCreateTime(time);
			addressInfo.setUpdateTime(time);
			addressInfoMapper.insert(addressInfo);
		}
		//…Ë÷√ƒ¨»œµÿ÷∑
		if(addressInfo.getIsDefault() == 1l){
			addressInfoMapper.setDefault(addressInfo);
		}

	}

	public List<AddressInfoDto> getListByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub

		return addressInfoMapper.selAddByPage(map);
	}

	public int countAddress(Map<String, String> map) {
		// TODO Auto-generated method stub
		AddressInfoExample example = new AddressInfoExample();
		if (map.containsKey("memberId")) {
			Long memberId = Long.valueOf(map.get("memberId"));
			example.createCriteria().andMemberIdEqualTo(memberId);
		}
		int count = addressInfoMapper.countByExample(example);

		return count;
	}

	public void setDefaultAdd(AddressInfo addressInfo) {
		// TODO Auto-generated method stub
		addressInfoMapper.updateByPrimaryKeySelective(addressInfo);
		addressInfoMapper.setDefault(addressInfo);
	}

	public void delAddress(Long addId) {
		// TODO Auto-generated method stub
		addressInfoMapper.deleteByPrimaryKey(addId);
	}

}
