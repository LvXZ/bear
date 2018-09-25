package com.group3.service;

import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**   
 * @ClassName: AccountManageService  
 * @description:   
 * @author: lvxz 
 * @Date: Aug 23, 2018	 3:21:39 PM 
 */
public interface AccountManageService {

	/**
	 * 登陆
	 * @return
	 * @throws
	 */
	ResponseInfoDTO<UserInfo> accountLogin(UserInfo personInfo);

	/**
	 * 验证支付
	 * @return
	 * @throws
	 */
	ResponseInfoDTO<UserInfo> password(UserInfo personInfo);

	/**
	 * 查验用户cookies
	 * @return
	 * @throws
	 */
	ResponseInfoDTO<UserInfo> accountCookie(UserInfo userInfo);

	/**
	 * 注册
	 * @return
	 * @throws
	 */
	ResponseInfoDTO<UserInfo> accountRegister(UserInfo userInfo, MultipartFile userImg);
	
	/**
	 * 修改密码
	 * @return
	 * @throws 
	 */
	ResponseInfoDTO<UserInfo> modifyAccountPassword(UserInfo personInfo, String new_password);

	/**
	 * 获取账户信息（为修改账户铺垫）
	 * @return
	 * @throws
	 */
	ResponseInfoDTO<UserInfo> getAccountInfo(String phone);

	/**
	 * 修改账户信息
	 * @return
	 * @throws 
	 */
	ResponseInfoDTO<UserInfo> modifyAccountInfo(UserInfo personInfo);

	/**
	 * 修改账户图片
	 * @return
	 * @throws
	 */
	ResponseInfoDTO<UserInfo> modifyAccountImage(UserInfo personInfo, MultipartFile userImg);



}
