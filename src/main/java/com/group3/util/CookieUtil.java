package com.group3.util;

import com.alibaba.fastjson.JSON;
import com.group3.entity.UserInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @ClassName: CookieUtil
 * @Description:
 * @author: lvxz
 * @create: 2018-08-31 16:45
 */


public class CookieUtil {

    private static String cookieName = "UserObjJson";

    /**
     * 获取Cookies
     * @param request
     * @return
     */
    public static String getCookieUserInfoJson(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        if(cookies != null && cookies.length > 0){
            for(Cookie cookie:cookies){

                if(cookie.getName().equals(cookieName)){
                    try {
                        return URLDecoder.decode(cookie.getValue(),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return null;
    }


    /**
     * 添加Cookies
     * @param userInfo
     * @return
     */
    public static Cookie setCookieUserInfoJson(UserInfo userInfo){

        String cookieValue = null;
        try {
            cookieValue = URLEncoder.encode(JSON.toJSONString(userInfo),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(60*15);

        return cookie;
    }


    /**
     * 删除Cookies
     * @param request
     */
    public static void deleteCookieUserInfoJson(HttpServletRequest request, HttpServletResponse response){


        Cookie[] cookies = request.getCookies();

        if(cookies != null && cookies.length > 0){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(cookieName)){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }


    // TODO 重置Cookies存活时间


}
