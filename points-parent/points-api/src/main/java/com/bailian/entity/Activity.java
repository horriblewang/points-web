package com.bailian.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Activity {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.ACT_ID
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private Long actId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.ACT_NAME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private String actName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.ACT_DESC
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private String actDesc;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.ACT_PIC
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private String actPic;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.STATUS
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.START_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private Timestamp startTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.END_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private Timestamp endTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.CREATE_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private Timestamp createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.UPDATE_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private Timestamp updateTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.HOT_SALE
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private String hotSale;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_ACTIVITY.PRIORITY
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	private Long priority;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.ACT_ID
	 * @return  the value of T_ACTIVITY.ACT_ID
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public Long getActId() {
		return actId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.ACT_ID
	 * @param actId  the value for T_ACTIVITY.ACT_ID
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setActId(Long actId) {
		this.actId = actId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.ACT_NAME
	 * @return  the value of T_ACTIVITY.ACT_NAME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public String getActName() {
		return actName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.ACT_NAME
	 * @param actName  the value for T_ACTIVITY.ACT_NAME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setActName(String actName) {
		this.actName = actName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.ACT_DESC
	 * @return  the value of T_ACTIVITY.ACT_DESC
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public String getActDesc() {
		return actDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.ACT_DESC
	 * @param actDesc  the value for T_ACTIVITY.ACT_DESC
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setActDesc(String actDesc) {
		this.actDesc = actDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.ACT_PIC
	 * @return  the value of T_ACTIVITY.ACT_PIC
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public String getActPic() {
		return actPic;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.ACT_PIC
	 * @param actPic  the value for T_ACTIVITY.ACT_PIC
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setActPic(String actPic) {
		this.actPic = actPic;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.STATUS
	 * @return  the value of T_ACTIVITY.STATUS
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.STATUS
	 * @param status  the value for T_ACTIVITY.STATUS
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.START_TIME
	 * @return  the value of T_ACTIVITY.START_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.START_TIME
	 * @param startTime  the value for T_ACTIVITY.START_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.END_TIME
	 * @return  the value of T_ACTIVITY.END_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.END_TIME
	 * @param endTime  the value for T_ACTIVITY.END_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.CREATE_TIME
	 * @return  the value of T_ACTIVITY.CREATE_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.CREATE_TIME
	 * @param createTime  the value for T_ACTIVITY.CREATE_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.UPDATE_TIME
	 * @return  the value of T_ACTIVITY.UPDATE_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.UPDATE_TIME
	 * @param updateTime  the value for T_ACTIVITY.UPDATE_TIME
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.HOT_SALE
	 * @return  the value of T_ACTIVITY.HOT_SALE
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public String getHotSale() {
		return hotSale;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.HOT_SALE
	 * @param hotSale  the value for T_ACTIVITY.HOT_SALE
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setHotSale(String hotSale) {
		this.hotSale = hotSale;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_ACTIVITY.PRIORITY
	 * @return  the value of T_ACTIVITY.PRIORITY
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public Long getPriority() {
		return priority;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_ACTIVITY.PRIORITY
	 * @param priority  the value for T_ACTIVITY.PRIORITY
	 * @mbggenerated  Mon May 22 20:17:18 CST 2017
	 */
	public void setPriority(Long priority) {
		this.priority = priority;
	}
}