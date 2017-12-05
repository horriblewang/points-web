package com.bailian.dto;

import java.util.List;

public class SiteStore {
	private List<String> userBizTypes;
	private List<String> userChannelTypes;
	private List<String> userStoreIds;
	private List<String> userStoreCodes;
	
	public List<String> getUserBizTypes() {
		return userBizTypes;
	}

	public void setUserBizTypes(List<String> userBizTypes) {
		this.userBizTypes = userBizTypes;
	}

	public List<String> getUserChannelTypes() {
		return userChannelTypes;
	}

	public void setUserChannelTypes(List<String> userChannelTypes) {
		this.userChannelTypes = userChannelTypes;
	}

	public List<String> getUserStoreIds() {
		return userStoreIds;
	}

	public void setUserStoreIds(List<String> userStoreIds) {
		this.userStoreIds = userStoreIds;
	}

	public List<String> getUserStoreCodes() {
		return userStoreCodes;
	}

	public void setUserStoreCodes(List<String> userStoreCodes) {
		this.userStoreCodes = userStoreCodes;
	}
	
	private String storeId;

	private String storeCode;

	private String storeName;

	private String storeType;
	
	private String buName;
	
	private Long settleAccountId;
	
	private Integer pageNo;
	
	private Integer pageSize;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getBuName() {
		return buName;
	}

	public void setBuName(String buName) {
		this.buName = buName;
	}

	public Long getSettleAccountId() {
		return settleAccountId;
	}

	public void setSettleAccountId(Long settleAccountId) {
		this.settleAccountId = settleAccountId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
