package org.company.oa.servlet;

import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletResponse;

import com.company.httpserver.core.RequestObject;
import com.company.httpserver.core.ResponseObject;

/**
 * 处理登录业务的Java程序，该Java程序有webApp开发人员开发，有web服务器调用
 * @author Mr.Liang  webApp
 * @version 1.0
 * @since 1.0
 *
 */

public class LoginServlet implements Servlet{

	// 处理业务的类
	public void service(RequestObject requestObject,ServletResponse response) {
		System.out.println("UserInfo正在进行处理........");
		PrintWriter out = response.getWrite();
		out.print("<html>");
		out.print("<head>");
		out.print("<title></title>");
		out.print("<meta content='text/html;charset=utf-8 />'");
		out.print("</head>");
		out.print("<body><center><font size='35px' color='blue'>正在验证，稍等............</font></center></body>");
		out.print("</html>");
	}
}
