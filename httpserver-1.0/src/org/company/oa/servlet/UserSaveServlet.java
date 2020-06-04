package org.company.oa.servlet;

import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletResponse;
import javax.swing.text.html.HTML;

import com.company.httpserver.core.RequestObject;

public class UserSaveServlet implements Servlet {

	@Override
	public void service(RequestObject requestObject, ServletResponse response) {
		System.out.println("UserInfo正在保存");
		// 获取页面请求参数
		String username = requestObject.getParameterValue("username");
		String gender = requestObject.getParameterValue("gender");
		String [] interests = requestObject.getParameterValues("interest");
		StringBuilder interest = new StringBuilder();
		for (String interestValue : interests) {
			interest.append(interestValue).append("; ");
		}
		
//		System.out.println(interests[1]);
		// 将获取到的请求参数值在响应给浏览器
		PrintWriter out = response.getWrite();
		
		out.append("<html>");
		out.append("<head>");
		out.append("<title>User-Info</title>");
		out.append("<meta content='text/html;charset=utf-8'/>");
		out.append("</head>");
		out.print("<body>");
		out.print("username:" + username + "<br>");
		out.print("gender:" + (gender.equals("1") ? "男" : "女") + "<br>");
		out.print("interest:" + interest + "<br>");
		out.print("</body>");
		out.append("</html>");
		
	}

}
