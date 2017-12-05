package com.points.osp.common.utils;

import com.bailian.utils.MD5;

public class TestSftp {

	public static void main(String[] args) {
		/*// TODO Auto-generated method stub
		SFTPUtils sftpUtils = new SFTPUtils("192.25.105.178",22,"root","Test123456789");
		try {
			sftpUtils.connect();
			String directory = "/home/ftpuser/www/images";
			String uploadFile = "C:\\PIC\\xhr25_1366.jpg";
			sftpUtils.upload(directory, uploadFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sftpUtils.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		System.out.println(MD5.getMD5("000000".getBytes()));

	}

}
