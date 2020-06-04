package com.company.httpserver.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

/**
 * 	Servlet缓存池对象
 * @author Administrator
 * @version 1.0
 * @since   1.0
 */
public class ServletCache {

	private static Map<String, Servlet> servletMap = new HashMap<String, Servlet>();
	
	public static void put(String urlPattern,Servlet servlet) {
		servletMap.put(urlPattern, servlet);
	}
	
	public static Servlet get(String urlPattern) {
		return servletMap.get(urlPattern);
	}
}
