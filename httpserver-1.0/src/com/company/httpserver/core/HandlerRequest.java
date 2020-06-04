package com.company.httpserver.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import javax.servlet.Servlet;
import com.company.httpserver.util.Logger;

/**
 * 处理客户端请求
 * @author Mr.Liang
 * @version 1.0
 * @since   1.0
 *
 */

public class HandlerRequest implements Runnable {
	public Socket clientSocket;
	PrintWriter out = null;
	public HandlerRequest(Socket cilentSocket) {
		this.clientSocket = cilentSocket;
	}
	@Override
	public void run() {
		// 处理客户端请求
		BufferedReader br = null;
		// 将当前线程请求的名称打印出来
		Logger.log("httpserver thread : " + Thread.currentThread().getName());
		try {
			// 接收客户端信息
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// 获取响应流对象
			out = new PrintWriter(clientSocket.getOutputStream());
			/*String temp = null;
			while ((temp = br.readLine()) != null) {
				System.out.println( temp );
			}*/
			// 获取请求行
			String requestLine = br.readLine();
			// 获取URI  请求行(reequestLine) -> 请求方式(GET/POST) 请求版本协议号  -> 三者之间通过空格连接
			
			String requestURI = requestLine.split(" ")[1];
			// 判断用户请求是否为一个静态页面，以.html或者.htm结尾的页面
			if (requestURI.endsWith(".html") || requestURI.endsWith(".htm")) {
				// 处理静态页面的方法
				requestStaticPage(requestURI,out);
			}else {
				// 动态资源：Java程序，业务处理类
				// 带参数: requestURI: /oa/login?username=admin&password=123123
				// 无参数: requestURI: /oa/login
				String servletPath = requestURI;
				// 判断servletPath是否包含参数
				if (servletPath.contains("?")) {
					servletPath = servletPath.split("\\?")[0];
				}
				// 根据servletPath找到业务处理逻辑类
				/*if (servletPath.equals("/oa/login")) {
					LoginServlet loginServlet = new LoginServlet();
					loginServlet.service();
				}*/
				
				// 获取应用名称 ： oa在uri中
				String webAppName = servletPath.split("[/]")[1];

				// 获取servletMaps集合中的value值： servletMap key：urlPattern value：servletClassName
				Map<String, String> servletMap = WebParser.servletMaps.get(webAppName);
				
//				System.out.println("servletMap.toString()"+servletMap.toString());
				
				// 获取servletMap集合中的key值
				String urlPattern = servletPath.substring(1 + webAppName.length());
				
				// 获取servletClassName
				String servletClassName = servletMap.get(urlPattern);
							
				// 判断业务处理的servlet类是否存在
				if (servletClassName != null) {
					// 获取封装响应参数对象
					ResponseObject responseObject = new ResponseObject();
					responseObject.setWrite(out);
					
					System.out.println("URI  " + requestURI);
					// 获取封装请求参数对象
					RequestObject requestObject = new RequestObject(requestURI);
					
					out.print("HTTP/1.1 200 OK\n");
					out.print("Content-Type:text/html;charset=utf-8\n\n");
					
					// 创建Servlet对象之前，先从缓存池中查找，查看是否存在
					// 1、如果不存在，则创建，并放入缓存池之中
					// 2、如果存在，则直接拿出来使用
					Servlet servlet = ServletCache.get(urlPattern);
					
					if (servlet == null) {
						
						// 通过反射机制创建该业务处理类
						Class c = Class.forName(servletClassName);
						Object obj = c.newInstance();
						// 通过接口来实现
						servlet = (Servlet)obj;
						// 将创建好的Servlet对象放入缓存池中
						ServletCache.put(urlPattern, servlet);
					}
					System.out.println("Servlet INFO " + servlet);
					servlet.service(requestObject,responseObject);
					
				}else {
					// 如果业务处理的servlet类不存在，则返回404页面
					StringBuilder html = new StringBuilder();
					html.append("HTTP/1.1 404 NotFound\n");
					html.append("Content-Type:text/html;charset=utf-8\n\n");
					html.append("<html>");
					html.append("<head>");
					html.append("<title>404-错误</title>");
					html.append("<meta content='text/html;charset=utf-8'/>");
					html.append("</head>");
					html.append("<body>");
					html.append("<center><font size='35px' color='red'>404-Not Found<资源暂时不存在></font></center>");
					html.append("</body>");
					html.append("</html>");
					out.print(html);
				}
			}
			// 强制刷新
			out.flush();
			
			} catch (Exception e) {
//			e.printStackTrace();
		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (clientSocket != null) {
				try {
					clientSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 处理静态页面
	 * @param requestURI 请求URI
	 * @param out     响应流对象
	 */
	public void requestStaticPage(String requestURI, PrintWriter out) {
		// 静态页面的路径
		String htmlPath = requestURI.substring(1);
		BufferedReader br = null;
		try {
			// 读取页面
			br = new BufferedReader(new FileReader(htmlPath));
			StringBuilder html = new StringBuilder();
			// 拼接响应信息
			html.append("HTTP/1.1 200 OK\n");
			html.append("Content-Type:text/html;charset=utf-8\n\n");
			String temp = null;
			while ((temp  = br.readLine()) != null) {
				html.append(temp);
			}
			// 通过输出流输出
			out.print(html);
		} catch (FileNotFoundException e) {
			StringBuilder html = new StringBuilder();
			html.append("HTTP/1.1 404 NotFound\n");
			html.append("Content-Type:text/html;charset=utf-8\n\n");
			html.append("<html>");
			html.append("<head>");
			html.append("<title>404-错误</title>");
			html.append("<meta content='text/html;charset=utf-8'/>");
			html.append("</head>");
			html.append("<body>");
			html.append("<center><font size='35px' color='red'>404-Not Found</font></center>");
			html.append("</body>");
			html.append("</html>");
			out.print(html);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

}
