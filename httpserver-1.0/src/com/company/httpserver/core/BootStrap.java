package com.company.httpserver.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.dom4j.DocumentException;

import com.company.httpserver.util.Logger;

/**
 * httpserver 程序主入口
 * 
 * @author Mr.Liang
 * @version 1.0
 * @since 1.0
 *
 */

public class BootStrap {

	/**
	 * 主程序
	 * 
	 * @param args
	 * @throws DocumentException 
	 */
	public static void main(String[] args) throws DocumentException {
		// 程序入口
		start();
	}

	/**
	 * 主程序入口
	 * @throws DocumentException 
	 */
	public static void start() throws DocumentException {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		BufferedReader br = null;
		try {  
			Logger.log("httpserver start");
			// 解析服务器中包含的web.xml配置文件
			// 此处使用一个获取所用应用名称的方法获取应用名称
			String [] webAppName = {"oa"};
			WebParser.parser(webAppName);
			// 获取当前时间
			long start = System.currentTimeMillis();
			int port = ServerParser.getPort();
			Logger.log("httpserver port = " + port);
			// 创建服务器套接字
			serverSocket = new ServerSocket(port);
			// 获取结束时间
			long end = System.currentTimeMillis();
			Logger.log("httpserver started = " + (end - start) + "ms");
			while (true) {
				// 开始监听网络 ，此时程序处于等待状态。等待就收客户端的信息
				clientSocket = serverSocket.accept();
				// 接受客户端信息
				/*br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				// 定义一个变量存储接受的信息
				String temp = null;
				while ((temp = br.readLine()) != null) {
					System.out.println(temp);
				}*/
				new Thread(new HandlerRequest(clientSocket)).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			/*if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (clientSocket != null) {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
