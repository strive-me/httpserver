package com.company.httpserver.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

/**
 * 封装请求参数
 * 
 * @author Administrator
 * @version 1.0
 * @since 1.0
 */
public class RequestObject {

	Map<String, String[]> parameterMap = new HashMap<String, String[]>();

	public RequestObject(String requestURI) {
		

		// 1、没有参数
		// 2、有一个参数
		// 3、有参数，但是参数的值为空
		// 4、有多个参数

		// 判断是否有参数
		if (requestURI.contains("?")) {
			// 将requestURI通过？进行分割
			String[] uriAndData = requestURI.split("[?]");
			// 再次判断requestURI是否有参数
			// 排除：oa/user?情况
			if (uriAndData.length > 1) {
				// 获取参数部分
				String data = uriAndData[1];
				// 判断requestURI中是否包含多个参数,是否包含&符号
				if (data.contains("&")) {
					// 多个参数，通过&符号进行分割，拿到参数数组
					String[] nameAndValueAttrs = data.split("[&]");
					// 使用循环获取每一个单独的参数
					for (String nameAndValue : nameAndValueAttrs) {
						// 拿到每一个参数的 key=value
						String[] nameAndValueAttr = nameAndValue.split("[=]");
						// 判断parameterMap结合中是否存在即将存入的参数，如果存在，即为多值参数
						if (parameterMap.containsKey(nameAndValueAttr[0])) { // 存在即为多值参数
							// 将之前存储进去的值取出来
							String [] values = parameterMap.get(nameAndValueAttr[0]);
							// 定义一个新数组，长度永远比原数组长度大1
							String [] newValues = new String[values.length + 1];
							// 将原来的参数值复制到新的数组中
							System.arraycopy(values, 0, newValues, 0, values.length);
							// 判断参数是否有值
							if (nameAndValueAttr.length > 1) {
								// 如果第二个存入的参数没有值
								newValues[newValues.length-1] = nameAndValueAttr[1];
							}else {
								newValues[newValues.length-1] = "";
							}
							// 将新数组的值存入Map集合中
							parameterMap.put(nameAndValueAttr[0], newValues);
						} else {
							// 判断参数是否有值
							if (nameAndValueAttr.length > 1) {
								// 将参数存入parameterMap集合中
								parameterMap.put(nameAndValueAttr[0], new String[] { nameAndValueAttr[1] });
							}else {
								parameterMap.put(nameAndValueAttr[0], new String[] {""});
								
							}
						}
					}

				} else { // 单个参数
					// 判断参数是否存在值 ,通过分割“=”来进行判断
					String[] nameAndValueAttr = data.split("[=]");
					// 判断参数是否为空
					if (nameAndValueAttr.length > 1) {
						// 有值则将其存放到字符串数组中
						parameterMap.put(nameAndValueAttr[0], new String[] { nameAndValueAttr[1] });
					} else {
						parameterMap.put(nameAndValueAttr[0], new String[] { "" });

					}
				}
			}
		}
	}
	
	/**
	 * 获取普通标签参数的值
	 * @param key  标签name属性的值
	 * @return String 标签value的值
	 */
	public String getParameterValue(String key) {
		String [] value = parameterMap.get(key);
		return (value != null && value.length != 0 ? value[0]:null);
	}
	
	/**
	 * 获取多选框的值
	 * @param key  标签name属性的值
	 * @return String[] 标签value的值
	 */
	public String[] getParameterValues(String key) {
		
		return parameterMap.get(key);
	}
}
