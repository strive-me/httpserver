package javax.servlet;

import java.io.PrintWriter;

import com.company.httpserver.core.RequestObject;
import com.company.httpserver.core.ResponseObject;

/**
 * 由SUN公司指定的接口规范，该接口有服务器开发人员调用，有web开发人员实现
 * 
 * @author Administrator
 * @version 1.0
 * @since 1.0
 *
 */
public interface Servlet {

	/**
	 * 处理业务的核心方法
	 * @param requestObject 
	 * 
	 * @param responseObject
	 */
	void service(RequestObject request, ServletResponse response);
}
