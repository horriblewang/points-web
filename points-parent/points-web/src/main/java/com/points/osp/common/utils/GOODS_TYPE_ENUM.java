package com.points.osp.common.utils;

public enum GOODS_TYPE_ENUM {
	
	BANNER("1","�ƹ���ֶһ�"),
	
	COMMON("2","�ҵĻ��ֶһ�"),
	
	SELECTED("3","��ѡ��Ʒ�һ�");
	
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
