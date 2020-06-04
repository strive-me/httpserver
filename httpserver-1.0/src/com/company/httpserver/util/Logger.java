package com.company.httpserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 日志记录器
 * @author Mr.Liang
 * @version 1.0
 * @since 1.0
 *
 */

public class Logger {
	
	// 工具类的方法往往是静态的直接通过类名调用，不需要去创建对象
	// 工具类的构造方法往往也是私有的，但不是必须的
	public Logger() {
	}
	
	/**
	 * 普通日志记录器
	 * @param msg
	 */
	public static void log(String msg) {
//		SimpleDateFormat dataFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss SSSS");
//		Date nowTime = new Date();
//		String nowTimeStr = dataFormat.format(nowTime);
		
		System.out.println("[INFO] " + DateUtil.getCurrentTime() + " " + msg);
	}

}
