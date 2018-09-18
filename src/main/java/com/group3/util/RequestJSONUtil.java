package com.group3.util;

import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName: RequestJSONUtil
 * @description: HttpServletRequest获取参数工具类
 * @author: lvxz
 * @Date: Aug 23, 2018	 3:30:15 PM
 */
public class RequestJSONUtil {

	public static Integer getInteger(HttpServletRequest request, String name) {

		try {
			return Integer.decode(request.getParameter(name));
		} catch (Exception e) {
			return -1;
		}
	}

	public static long getLong(HttpServletRequest request, String name) {

		try {
			return Long.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return -1;
		}
	}

	public static Double getDouble(HttpServletRequest request, String name) {

		try {
			return Double.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return -1d;
		}
	}

	public static Boolean getBoolean(HttpServletRequest request, String name) {

		try {
			return Boolean.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return false;
		}
	}

	public static String getString(HttpServletRequest request, String name) {
		try {
			String result = request.getParameter(name);
			if (result != null) {
				result = result.trim();
			}
			if ("".equals(result))
				result = null;
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}