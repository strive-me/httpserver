package com.company.httpserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author Mr.Liang
 * @version 1.0
 * @since 1.0
 *
 */
public class DateUtil {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss SSSS");
	
	public DateUtil() {
	}
	
	/**
	 * 获取系统当前时间
	 * @return String [YYYY-MM-dd MM:mm:ss SSS]
	 */
	public static String getCurrentTime() {
		return dateFormat.format(new Date());
	}
	
}
