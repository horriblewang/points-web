package com.bailian.dto;

public class BaseDto {

	/**
	 * 系统标识
	 */
	private String sysid;

	/**
	 * 签名字符串
	 */
	private String sign;

	/**
	 * token
	 */
	private String member_token;

	/**
	 * 请求时间戳
	 */
	private String timeStamp;

	/**
	 * 分页大小
	 */
	private Integer pageSize;

	/**
	 * 页面总数
	 */
	private Integer sumPage;

	/**
	 * 当前页数
	 */
	private Integer currentPage;

	/**
	 * 排序方式
	 */
	private String sortBy;
	
	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMember_token() {
		return member_token;
	}

	public void setMember_token(String member_token) {
		this.member_token = member_token;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getSumPage() {
		return sumPage;
	}

	public void setSumPage(Integer sumPage) {
		this.sumPage = sumPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	@Override
	public String toString() {
		return "BasePara [" + (sysid != null ? "sysid=" + sysid + ", " : "") + (sign != null ? "sign=" + sign + ", " : "") + (member_token != null ? "member_token=" + member_token + ", " : "")
				+ (timeStamp != null ? "timeStamp=" + timeStamp + ", " : "") + (pageSize != null ? "pageSize=" + pageSize + ", " : "") + (sumPage != null ? "sumPage=" + sumPage + ", " : "")
				+ (currentPage != null ? "currentPage=" + currentPage + ", " : "") + (sortBy != null ? "sortBy=" + sortBy : "") + "]";
	}

}
