package com.points.osp.common.utils;

public enum ACT_ENUM {
	
	LUCKY_DRAW("1","ת�̳齱"),
	
	EXG_GOODS("0","�һ��");
	
	private String type;
	
	private String name;
	
	ACT_ENUM(String type,String name){
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
