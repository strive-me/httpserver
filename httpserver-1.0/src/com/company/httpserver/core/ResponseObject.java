package com.company.httpserver.core;

import java.io.PrintWriter;

import javax.servlet.ServletResponse;

/**
 * 处理响应流信息
 * @author Administrator
 * @version 1.0
 * @since 1.0
 */

public class ResponseObject implements ServletResponse {

	private PrintWriter out;
	
	public void setWrite(PrintWriter out) {
		this.out = out;
	}
	
	public PrintWriter getWrite() {
		return out;
	}
}
