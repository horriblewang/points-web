package com.bailian.utils;

public class ComErrorCodeConstants
{
  public static enum ErrorCode
  {
    SYSTEM_SUCCESS("00100000", "succes"), 
    SYSTEM_ERROR("00100001", "网络连接异常,请稍后重试!"), 
    PARA_NORULE_ERROR("00100002", "请求参数格式不符合规则"), 
    VALIDATE_ERROR("00100003", "校验有误"), 
    DATA_OPER_ERROR("00100004", "数据操作异常"), 
    APPLICATION_OPER_ERROR("00100006", "系统业务异常"), 
    ACCOUNT_EXIST_ERROR("00100007", "账号信息存在异常"), 
    DATA_EMPTY_ERROR("00100005", "查询结果为空"), 
    UPDATE_STATUS_ERROR("00100008", "修改优惠券状态失败"), 
    ENCODE_FAIL_ERROR("00100009", "解码失败!");

    private String errorCode;
    private String memo;

    private ErrorCode() {  }

    private ErrorCode(String errorCode, String memo) { this.errorCode = errorCode;
      this.memo = memo; }

    public String getErrorCode()
    {
      return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
      this.errorCode = errorCode;
    }

    public String getMemo() {
      return this.memo;
    }

    public void setMemo(String memo) {
      this.memo = memo;
    }
  }
}