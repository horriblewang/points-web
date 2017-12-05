package com.bailian.dto;

import java.sql.Timestamp;

public class OrderInfoDto {

	private Long orderId;

	private Long points;

	private Long memberId;

	private String mobile;

	private Timestamp createTime;
	
	private String ruleName;
	   
    private Long dayPoints;
   
    private Long firstRate;
   
    private Long secRate;

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Long getDayPoints() {
		return dayPoints;
	}

	public void setDayPoints(Long dayPoints) {
		this.dayPoints = dayPoints;
	}

	public Long getFirstRate() {
		return firstRate;
	}

	public void setFirstRate(Long firstRate) {
		this.firstRate = firstRate;
	}

	public Long getSecRate() {
		return secRate;
	}

	public void setSecRate(Long secRate) {
		this.secRate = secRate;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
