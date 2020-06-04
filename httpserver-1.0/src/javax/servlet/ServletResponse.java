package javax.servlet;

import java.io.PrintWriter;

/**
 * 处理业务逻辑参数规范
 * @author Administrator
 * @version 1.0
 * @since 1.0
 */
public interface ServletResponse {

	void setWrite(PrintWriter out);
	
	PrintWriter getWrite();
}
