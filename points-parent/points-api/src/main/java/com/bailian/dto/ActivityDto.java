package com.bailian.dto;

import java.util.Date;

public class ActivityDto {
	
	private String processStatus;
    
    private Long actId;

    
    private String actName;

    
    private String actDesc;

    
    private String actPic;

    
    private String status;

    
    private Date startTime;

    
    private Date endTime;

    
    private Date createTime;

    
    private Date updateTime;

    
    private String hotSale;

    
    private Long priority;

    
    public Long getActId() {
        return actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.ACT_ID
     *
     * @param actId the value for T_ACTIVITY.ACT_ID
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setActId(Long actId) {
        this.actId = actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.ACT_NAME
     *
     * @return the value of T_ACTIVITY.ACT_NAME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public String getActName() {
        return actName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.ACT_NAME
     *
     * @param actName the value for T_ACTIVITY.ACT_NAME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setActName(String actName) {
        this.actName = actName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.ACT_DESC
     *
     * @return the value of T_ACTIVITY.ACT_DESC
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public String getActDesc() {
        return actDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.ACT_DESC
     *
     * @param actDesc the value for T_ACTIVITY.ACT_DESC
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.ACT_PIC
     *
     * @return the value of T_ACTIVITY.ACT_PIC
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public String getActPic() {
        return actPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.ACT_PIC
     *
     * @param actPic the value for T_ACTIVITY.ACT_PIC
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setActPic(String actPic) {
        this.actPic = actPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.STATUS
     *
     * @return the value of T_ACTIVITY.STATUS
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.STATUS
     *
     * @param status the value for T_ACTIVITY.STATUS
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.START_TIME
     *
     * @return the value of T_ACTIVITY.START_TIME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.START_TIME
     *
     * @param startTime the value for T_ACTIVITY.START_TIME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.END_TIME
     *
     * @return the value of T_ACTIVITY.END_TIME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.END_TIME
     *
     * @param endTime the value for T_ACTIVITY.END_TIME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.CREATE_TIME
     *
     * @return the value of T_ACTIVITY.CREATE_TIME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.CREATE_TIME
     *
     * @param createTime the value for T_ACTIVITY.CREATE_TIME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.UPDATE_TIME
     *
     * @return the value of T_ACTIVITY.UPDATE_TIME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.UPDATE_TIME
     *
     * @param updateTime the value for T_ACTIVITY.UPDATE_TIME
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.HOT_SALE
     *
     * @return the value of T_ACTIVITY.HOT_SALE
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public String getHotSale() {
        return hotSale;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.HOT_SALE
     *
     * @param hotSale the value for T_ACTIVITY.HOT_SALE
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setHotSale(String hotSale) {
        this.hotSale = hotSale;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ACTIVITY.PRIORITY
     *
     * @return the value of T_ACTIVITY.PRIORITY
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public Long getPriority() {
        return priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ACTIVITY.PRIORITY
     *
     * @param priority the value for T_ACTIVITY.PRIORITY
     *
     * @mbggenerated Sat May 20 20:41:20 CST 2017
     */
    public void setPriority(Long priority) {
        this.priority = priority;
    }

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
}