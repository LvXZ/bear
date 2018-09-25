package com.group3.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.dto.MessageDTO;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.Shop;
import com.group3.entity.UserInfo;
import com.group3.service.AccountManageService;

import com.group3.util.CookieUtil;
import com.group3.util.ParamsJSONUtil;
import com.group3.util.RequestJSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**   
 * @ClassName: UserController  
 * @description: user用户URL后端响应模块，非后端人员禁止修改
 * @author: lvxz 
 * @Date: Aug 23, 2018	 3:30:15 PM 
 */


@RestController
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	private AccountManageService accountManageService;
	
	/*************用户模块：用户登录、注册、更改密码、获取个人信息、更改个人信息***************/
	/**
	 * 用户登录
	 * telephone、password
	 * telephone、name、image
	 */
	@PostMapping(value="/login")
	public ResponseInfoDTO<UserInfo> loginPerson(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = JSON.parseObject(params, UserInfo.class);

		ResponseInfoDTO<UserInfo> rpt = accountManageService.accountLogin(userInfo);
		if(rpt.getCode() == 1){
			CookieUtil.deleteCookieUserInfoJson(request,response);
			response.setCharacterEncoding("UTF-8");
			response.addCookie(CookieUtil.setCookieUserInfoJson(rpt.getData()));
		}

		return rpt;
	}


	/**
	 * 用户注册，执行有图片
	 * all
	 * null
	 */
	@PostMapping(value="/register")
	public ResponseInfoDTO<UserInfo> registerPerson(HttpServletRequest request, HttpServletResponse response) {

		//获取参数
		String userStr = RequestJSONUtil.getString(request, "objJson");
        UserInfo userInfo = JSON.parseObject(userStr, UserInfo.class);

		//获取多个文件，此处默认接收一个文件
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("objImg");
		//获取图片
		MultipartFile userImg = null;
		if(files.size() > 0){
			userImg = files.get(0);
		}

		return accountManageService.accountRegister(userInfo,userImg);

	}

	/**
	 * 用户更改密码
	 * telephone、password、new_password
	 * null
	 */
	@PostMapping(value="/update_password")
	public ResponseInfoDTO<UserInfo> updatePassword(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

		UserInfo userinfo = JSON.parseObject(params, UserInfo.class);
		String new_password = ParamsJSONUtil.getString(params,"new_password");
		return accountManageService.modifyAccountPassword(userinfo,new_password);
	}
	
	/**
	 * 获取用户信息
	 * telephone
	 * all
	 */
	@PostMapping(value="/get_info")
	public ResponseInfoDTO<UserInfo> getUserInfo(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {
		UserInfo userinfo = JSON.parseObject(params, UserInfo.class);
		return accountManageService.getAccountInfo(userinfo.getTelephone());
	}
	
	/**
	 * 更新用户信息
	 * all
	 * null
	 */
	@PostMapping(value="/update_user_info")
	public ResponseInfoDTO<UserInfo> updateUserInfo(HttpServletRequest request, HttpServletResponse response) {

		String userStr = RequestJSONUtil.getString(request, "objJson");
		UserInfo userinfo = JSON.parseObject(userStr, UserInfo.class);

		return accountManageService.modifyAccountInfo(userinfo);
	}


	/**
	 * 更新用户图片
	 * objImg
	 * null
	 */
	@PostMapping(value="/update_user_image")
	public ResponseInfoDTO<UserInfo> updateUserImage(HttpServletRequest request, HttpServletResponse response) {


		String userStr = RequestJSONUtil.getString(request, "objJson");
		UserInfo userinfo = JSON.parseObject(userStr, UserInfo.class);
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("objImg");
		if(files.size() > 0){
			MultipartFile goodsImg = files.get(0);
			return accountManageService.modifyAccountImage(userinfo,goodsImg);
		}else{
			return ResponseInfoDTO.fail(MessageDTO.REGISTER_FAIL_3);
		}

	}



	/**
	 * 支付验证
	 * telephone、password
	 */
	@PostMapping(value="/purchase")
	public ResponseInfoDTO<UserInfo> passwordPerson(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = JSON.parseObject(params, UserInfo.class);

		ResponseInfoDTO<UserInfo> rpt = accountManageService.password(userInfo);

		return rpt;
	}

}
