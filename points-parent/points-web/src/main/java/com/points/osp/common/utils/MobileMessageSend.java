package com.points.osp.common.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.points.osp.common.config.Global;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * ���ŷ��͹�����
 * @author Administrator
 *
 */
public class MobileMessageSend {
    private static final String SERVER_URL = Global.getConfig("SMS.SERVER_URL");;//������֤�������·��URL
    private static final String APP_KEY = Global.getConfig("SMS.APP_KEY");//�������ŷ�����˺�
    private static final String APP_SECRET = Global.getConfig("SMS.APP_SECRET");//�������ŷ������Կ
    private static final String NONCE="1qaz2wsx";//�����

    public static String sendMsg(String phone) throws IOException {
    	
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(SERVER_URL);

        String curTime=String.valueOf((new Date().getTime()/1000L));
        String checkSum=CheckSumBuilder.getCheckSum(APP_SECRET,NONCE,curTime);

        //���������header
        post.addHeader("AppKey",APP_KEY);
        post.addHeader("Nonce",NONCE);
        post.addHeader("CurTime",curTime);
        post.addHeader("CheckSum",checkSum);
        post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //�����������
        List<NameValuePair> nameValuePairs =new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("mobile",phone));

        post.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));

        //ִ������
        HttpResponse response=httpclient.execute(post);
        String responseEntity= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("mobile===>" + phone + responseEntity);
        JSONObject jsonObj = JSON.parseObject(responseEntity);
        String code = jsonObj.getString("code");
		//�ж��Ƿ��ͳɹ������ͳɹ�����true
        if (code.equals("200")){
            return jsonObj.getString("obj");
        }
        return "error";
    }
    
    public static void main(String[] args){
    	try {
			sendMsg("18616887071");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}