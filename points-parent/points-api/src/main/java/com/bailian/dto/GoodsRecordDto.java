package com.bailian.dto;

import java.util.Date;

public class GoodsRecordDto {
	
	 
    private Long memberId;

    
    private String memberName;

    
    private String mobile;
	
	private Long goodsId;

    
    private String goodsName;

    
    private Long goodsPoints;
    
    
    private Long goodsNum;

    
    private Date createTime;
    
    private String appPicUrl;


	public Long getMemberId() {
		return memberId;
	}


	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Long getGoodsId() {
		return goodsId;
	}


	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}


	public String getGoodsName() {
		return goodsName;
	}


	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public Long getGoodsPoints() {
		return goodsPoints;
	}


	public void setGoodsPoints(Long goodsPoints) {
		this.goodsPoints = goodsPoints;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Long getGoodsNum() {
		return goodsNum;
	}


	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}


	public String getAppPicUrl() {
		return appPicUrl;
	}


	public void setAppPicUrl(String appPicUrl) {
		this.appPicUrl = appPicUrl;
	}

}
