package com.bailian.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 鏃ユ湡宸ュ叿绫�
 * @author wangwenbo
 * 
 */
public class DateUtils {
	/**
	 * 骞存湀鏃ユ椂鍒嗙鏍煎紡鐨勬棩鏈�
	 */
	public static final String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM_DD_HHMM = "yyyy-MM-dd HH:mm";

	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYY_MM = "yyyy-MM";

	public static final String YYYYMMDD = "yyyyMMdd";
	
	public static final String YYYYMMDDHH = "yyyyMMdd HH";
	
	public static final String YYYYMMDDHHMMSS_SSS = "yyyyMMddHHmmssSSS";
	
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";


	/**
	 * 鏃ユ湡鏍煎紡鍖栦负瀛楃涓�
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 椤甸潰涓婄殑ID鍜岃鍒欏崟鍙风敓鎴�
	 * @param type
	 * @return
	 */
	public static String getSequenceNo(String type){
		 SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.YYYYMMDDHHMMSS_SSS);
		 return type + sdf.format(new Date());
	}

	/**
	 * 瀛楃涓茶浆鎹负Date瀵硅薄
	 * 
	 * @param date
	 *            鏀寔"yyyy-MM"銆�yyyy-MM-dd"銆�yyyy-MM-dd HH"銆�yyyy-MM-dd HH:mm"銆�
	 *            "yyyy-MM-dd HH:mm:ss"浜旂鏍煎紡鐨勬棩鏈�
	 * @return
	 */
	public static Date parseDate(String date) {
		if (date == null || date.trim().length() == 0) {
			return null;
		}
		date = date.trim();
		if (date.length() == YYYY_MM.length()) {
			return parseDate(date, YYYY_MM);
		} else if (date.length() == YYYYMMDD.length()) {
			return parseDate(date, YYYYMMDD);
		}else if (date.length() == YYYY_MM_DD.length()) {
			return parseDate(date, YYYY_MM_DD);
		}else if (date.length() == YYYYMMDDHH.length()) {
			return parseDate(date, YYYYMMDDHH);
		} else if (date.length() == YYYY_MM_DD_HH.length()) {
			return parseDate(date, YYYY_MM_DD_HH);
		} else if (date.length() == YYYY_MM_DD_HHMM.length()) {
			return parseDate(date, YYYY_MM_DD_HHMM);
		} else if (date.length() >= YYYY_MM_DD_HHMMSS.length()) {
			date = date.substring(0, YYYY_MM_DD_HHMMSS.length());
			return parseDate(date, YYYY_MM_DD_HHMMSS);
		}
		else {
			return null;
		}
	}

	/**
	 * 瀛楃涓茶浆鎹负Date瀵硅薄
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 鍦ㄥ綋鍓嶆椂闂翠笂
	 * 
	 * @param type
	 *            鏃ユ湡澧為噺绫诲瀷锛屽Calendar.YEAR绛�
	 * @parma increment 澧為噺
	 * @return 杩斿洖鏃ユ湡鐨剏yyy-MM-dd鏍煎紡
	 */
	public static String getDate(int type, int increment) {
		Calendar date = Calendar.getInstance();
		date.add(type, increment);
		return formatDate(date.getTime(),YYYY_MM_DD_HH);
	}

	/**
	 * 鑾峰彇褰撴湀绗竴澶�
	 * 
	 * @return
	 */
	public static String getFisrtDateOfMonth() {
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat ymdFormat = new SimpleDateFormat(YYYY_MM_DD);
		return ymdFormat.format(date.getTime());
	}

	/**
	 * 鑾峰彇褰撳勾绗竴澶�
	 * 
	 * @return
	 */
	public static String getFisrtDateOfYear() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat ymdFormat = new SimpleDateFormat(YYYY_MM_DD);
		return ymdFormat.format(date.getTime());
	}

	/**
	 * 鑾峰彇涓�勾鐨勬渶鍚庝竴澶�
	 * 
	 * @return
	 */
	public static String getLastDateOfYear() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.YEAR, 1);
		date.set(Calendar.MONTH, 0);
		date.set(Calendar.DATE, 1);
		date.add(Calendar.DATE, -1);
		SimpleDateFormat ymdFormat = new SimpleDateFormat(YYYY_MM_DD);
		return ymdFormat.format(date.getTime());
	}

	/**
	 * 璁＄畻涓や釜鏃ユ湡鐩稿樊鐨勫勾鏁�
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static int diffYear(Date firstDate, Date secondDate) {
		Calendar first = Calendar.getInstance();
		first.setTime(firstDate);
		Calendar second = Calendar.getInstance();
		second.setTime(secondDate);
		int diff = second.get(Calendar.YEAR) - first.get(Calendar.YEAR);
		first.add(Calendar.YEAR, diff);
		return first.before(second) ? diff : diff - 1;
	}
	
	/**
	 * 鍦╠ate鍩虹涓婂鍔燿ay澶�
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
		
	}
	
	public static long diffDays(Date startDate, Date endDate){
		
		long totalDate = 0;  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(startDate);  
        long timestart = calendar.getTimeInMillis();  
        calendar.setTime(endDate);  
        long timeend = calendar.getTimeInMillis();  
        totalDate = (timeend - timestart)/(1000*60*60*24);  
        return totalDate;
		
	}
}
