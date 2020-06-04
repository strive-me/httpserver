package com.company.httpserver.core;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析server.xml配置文件
 * @author Administrator
 * @version 1.0
 * @since 1.0
 *
 */
public class ServerParser {

	public static int getPort() {
		int port = 9999;
		try {
			//创建解析器
			SAXReader saxReader = new SAXReader();
			// 通过解析器的read方法将配置文件读取到内存中，生成一个Document[org.dom4]对象树
			Document document = saxReader.read("conf/server.xml");
			// 获取connector节点的XPath路径： /server/servicer/connector
			// 获取connector节点的XPath路径： /server//connector
			// 获取connector节点的XPath路径： //connector
			Element connectorElt = (Element) document.selectSingleNode("//connector");
			// 获取Port属性的值
			port = Integer.parseInt(connectorElt.attributeValue("port"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return port;
	}
}
