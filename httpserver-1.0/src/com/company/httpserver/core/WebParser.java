package com.company.httpserver.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析服务器中的web.xml配置文件
 * 
 * @author Mr.Liang
 * @version 1.0
 * @since 1.0
 *
 */

public class WebParser {
	
	public static Map<String, Map<String, String>> servletMaps = new HashMap<String,Map<String, String>>();

	/**
	 * 解析服务器所有应用的web.xml
	 * @param webAppNames  服务器中所有应用的名称
	 * @throws DocumentException 
	 */
	public static void parser(String [] webAppNames) throws DocumentException {
		for (String webAppName : webAppNames) {
			Map<String, String> servletMap = parser(webAppName);
			servletMaps.put(webAppName, servletMap);
		}
	}
	/**
	 * 解析单个web.xml配置文件
	 * 
	 * @param webAppName 应用的名称
	 * @return servletMap<String,String>
	 * @throws DocumentException
	 * 
	 */
	private static Map<String, String> parser(String webAppName) throws DocumentException {
		// 获取web.xml配置文件的路径
		String webPath = webAppName + "/WEB-INF/web.xml";
		// 创建解析器
		SAXReader saxReader = new SAXReader();
		// 通过解析器的read方法将配置文件读取到内存中，生成一个Document[org.dom4j]对象树
		Document document = saxReader.read(new File(webPath));

		// 获取servlet结点元素： web-app --> servlet
		List<Element> servletNodes = document.selectNodes("/web-app/servlet");
		// 创建一个servletInfoMap集合，将servlet-name和servlet-class的值分别作为key和value存放到该集合中
		Map<String, String> servletInfoMap = new HashMap<String, String>();
		// 开始遍历servletNodes
		for (Element servletNode : servletNodes) {
			// 获取servlet-name节点元素对象
			Element servletNameElement = (Element) servletNode.selectSingleNode("servlet-name");
			// 获取servlet-name节点元素的值
			String servletName = servletNameElement.getStringValue();

			// 获取servlet-class节点元素对象
			Element servletClassElement = (Element) servletNode.selectSingleNode("servlet-class");
			// 获取servlet-class的节点元素值
			String servletClassName = servletClassElement.getStringValue();

			// 将servletName和servletClassName分别作为key和value存入集合servletInfoMap中
			// key = loginServlet
			// value = org.company.page.servlet.LoginServlet
			servletInfoMap.put(servletName, servletClassName);
		}

		// 获取servlet-mapping节点元素对象 web-app ----> serverlet-mapping
		List<Element> servletMapNodes = document.selectNodes("/web-app/servlet-mapping");
		// 创建一个servletMapInfoMap集合，将servlet-name和servlet-class的值分别作为key和value存放到该集合中
		Map<String, String> servletMappingInfoMap = new HashMap<String, String>();
		// 开始遍历servletMapNodes
		for (Element servletMapNode : servletMapNodes) {
			// 获取servlet-name节点元素对象
			Element servletNameElt = (Element) servletMapNode.selectSingleNode("servlet-name");
			// 获取servlet-name节点的值
			String servletName = servletNameElt.getStringValue();
			// 获取url-pattern节点元素对象
			Element urlPatternElt = (Element) servletMapNode.selectSingleNode("url-pattern");
			// 获取url-pattern节点的值
			String urlPatternName = urlPatternElt.getStringValue();

			// 将servletMapName和servletUrlName的值分别作为键值存入servletMappingInfoMap集合中
			// key = loginServlet
			// value = /login
			servletMappingInfoMap.put(servletName, urlPatternName);
		}

		// 获取servletInfoMap或者servletMappingInfoMap集合中的任何一个key的值的集合
		Set<String> servletNames = servletInfoMap.keySet();
		// 创建一个servletMap集合，将servletMappingInfoMap的value值和servletInfoMap的value分别作为key和value存储到该集合中
		Map<String, String> servetMap = new HashMap<String, String>();
		// 开始遍历servletNmaes
		for (String servletName : servletNames) {
			// 获取servletMappingInfoMap集合中的value值：urlPatternName
			// urlPattern = /login
			String urlPattern = servletMappingInfoMap.get(servletName);
			// 获取servletInfoMap集合中的value值servletClass
			// servler = org.company.page.servlet.LoginServlet
			String servletClassName = servletInfoMap.get(servletName);
			
			// 将urlPattern和servletClassName的值分别作为key和value的值存入servletMap集合中
			servetMap.put(urlPattern, servletClassName);

		}
		// key = /login
		// value = org.company.page.servlet.LoginServlet
		return servetMap;
	}
}
