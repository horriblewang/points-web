package com.bailian.utils;

public class EncryptUtils {
	
	// 解密
	public static String decrypt(String password){
		
		StringBuffer sb = new StringBuffer(password);
		String rev = sb.reverse().toString();
		String kh = rev.substring(0, rev.length() - 5);
		String pwd = rev.substring(rev.length() - 5, rev.length());
		int ikh  = Integer.parseInt(kh) ^ 11111111;
		String dkh = "" + ikh;	
		int ipwd = Integer.parseInt(pwd) ^ 29;
		String dpwd = "" + ipwd;
		int i = dkh.length();
		if(i < 8){
			StringBuilder temp = new StringBuilder();
			for(int p = 0; p < 8-i; p++){
				temp.append("0");
			}
			dkh = temp + dkh;
		}
		int j = dpwd.length();
		if(j < 5){
			StringBuilder temp = new StringBuilder();
			for(int p = 0; p < 5-j; p++){
				temp.append("0");
			}
			dpwd = temp + dpwd;
		}
		
		StringBuffer out = new StringBuffer(dkh + dpwd);
		out = out.reverse();
				
		return out.substring(0, 6);
	}
	
	//加密
	public static String encrypt(String password){
		StringBuffer  buffer = new  StringBuffer(password);
		String rev = buffer.reverse().toString();
		String kh = rev.substring(0, rev.length() - 5);
		String pwd = rev.substring(rev.length() - 5, rev.length());
		int ikh  = Integer.parseInt(kh) ^ 11111111;
		String dkh = "" + ikh;	
		int ipwd = Integer.parseInt(pwd) ^ 29;
		String dpwd = "" + ipwd;
		int i = dkh.length();
		if(i < 8){
			StringBuilder temp = new StringBuilder();
			for(int p = 0; p < 8-i; p++){
				temp.append("0");
			}
			dkh = temp + dkh;
		}
		int j = dpwd.length();
		if(j < 5){
			StringBuilder temp = new StringBuilder();
			for(int p = 0; p < 5-j; p++){
				temp.append("0");
			}
			dpwd = temp + dpwd;
		}
		StringBuffer outBuffer = new StringBuffer(dkh + dpwd);
		String out = outBuffer.reverse().toString();
		return out;
	}
	
//	public static void main(String[] args) {
//		//加密
//		System.out.println(EncryptUtils.decrypt("6332250111111"));
//		//解密
//		System.out.println(EncryptUtils.encrypt("563226"));
//	}

}
