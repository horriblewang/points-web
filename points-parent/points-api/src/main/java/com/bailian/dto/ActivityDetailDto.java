package com.bailian.dto;

import java.util.Date;

public class ActivityDetailDto {
	
	private Long memberId;
	
    
    private Long detailId;

    
    private Long actId;

    
    private Long goodsId;
    
    
    private String goodsName;

    
    private Integer orderSec;

    
    private Long percent;

    
    private Date createTime;
    
    
    private String appPicUrl;

    
    public Long getDetailId() {
        return detailId;
    }

    
    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    
    public Long getActId() {
        return actId;
    }

    
    public void setActId(Long actId) {
        this.actId = actId;
    }

    
    public Long getGoodsId() {
        return goodsId;
    }

   
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    
    public Integer getOrderSec() {
        return orderSec;
    }

    
    public void setOrderSec(Integer orderSec) {
        this.orderSec = orderSec;
    }

   
    public Long getPercent() {
        return percent;
    }

    
    public void setPercent(Long percent) {
        this.percent = percent;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


	public String getGoodsName() {
		return goodsName;
	}


	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public Long getMemberId() {
		return memberId;
	}


	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}


	public String getAppPicUrl() {
		return appPicUrl;
	}


	public void setAppPicUrl(String appPicUrl) {
		this.appPicUrl = appPicUrl;
	}
}