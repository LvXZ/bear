package com.group3.controller;

import com.alibaba.fastjson.JSON;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.UserInfo;
import com.group3.service.AccountManageService;
import com.group3.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**   
 * @ClassName: HttpController  
 * @description:  网页返回控制层，没有团队同意，禁止个人修改行为
 * @author: lvxz 
 * @Date: Aug 24, 2018	 10:02:55 AM 
 */

@Controller
public class HttpController {

    @Autowired
    private AccountManageService accountManageService;
	
	@RequestMapping("/")
    public String login1Page() {
        return "index";
    }

    @RequestMapping("/index")
    public String indexPage() {
        return "index";
    }

    @RequestMapping("/addshop")
    public String addshopPage() {
        return "addshop";
    }

    @RequestMapping("/addgoods")
    public String addgoodsPage() {
        return "addgoods";
    }

    @RequestMapping("/modifygoods")
    public String modifygoodsPage() {
        return "modifygoods";
    }

    @RequestMapping("/userinfo")
    public String userInfoPage(HttpServletRequest request) {

        String userCookiesValue = CookieUtil.getCookieUserInfoJson(request);

        if(userCookiesValue != null){
            System.out.println(userCookiesValue);
            UserInfo userinfo = JSON.parseObject(userCookiesValue, UserInfo.class);
            ResponseInfoDTO<UserInfo> responseInfoDTO = accountManageService.accountCookie(userinfo);
            if(responseInfoDTO.getCode() == 1){
                return "userinformation";
            }else{
                return "index";
            }

        }else {
            return "index";
        }

    }

    @RequestMapping("/modifyshop")
    public String modifyShopPage() {
        return "modifyshop";
    }

    @RequestMapping("/shoper")
    public String shoperPage() {
        return "shoper";
    }
    
}
