package com.points.osp.common.utils;

public enum GOODS_TYPE_ENUM {
	
	BANNER("1","推广积分兑换"),
	
	COMMON("2","我的积分兑换"),
	
	SELECTED("3","精选商品兑换");
	
	private String type;
	
	private String name;
	
	GOODS_TYPE_ENUM(String type,String name){
		this.setType(type);
		this.setName(name);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
