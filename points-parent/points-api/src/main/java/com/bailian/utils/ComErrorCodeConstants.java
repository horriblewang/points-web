package com.bailian.utils;

public class ComErrorCodeConstants
{
  public static enum ErrorCode
  {
    SYSTEM_SUCCESS("00100000", "succes"), 
    SYSTEM_ERROR("00100001", "���������쳣,���Ժ�����!"), 
    PARA_NORULE_ERROR("00100002", "���������ʽ�����Ϲ���"), 
    VALIDATE_ERROR("00100003", "У������"), 
    DATA_OPER_ERROR("00100004", "���ݲ����쳣"), 
    APPLICATION_OPER_ERROR("00100006", "ϵͳҵ���쳣"), 
    ACCOUNT_EXIST_ERROR("00100007", "�˺���Ϣ�����쳣"), 
    DATA_EMPTY_ERROR("00100005", "��ѯ���Ϊ��"), 
    UPDATE_STATUS_ERROR("00100008", "�޸��Ż�ȯ״̬ʧ��"), 
    ENCODE_FAIL_ERROR("00100009", "����ʧ��!");

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