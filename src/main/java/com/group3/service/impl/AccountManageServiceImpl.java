package com.group3.service.impl;

import com.group3.dao.UserInfoDAO;
import com.group3.dto.MessageDTO;
import com.group3.dto.ResponseInfoDTO;
import com.group3.entity.UserInfo;
import com.group3.redis.RedisService;
import com.group3.redis.key.UserInfoKey;
import com.group3.service.AccountManageService;
import com.group3.util.FileUtil;
import com.group3.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**   
 * @ClassName: AccountManageServiceImpl  
 * @description:   
 * @author: lvxz 
 * @Date: Aug 23, 2018	 3:21:49 PM 
 */

@Service
public class AccountManageServiceImpl implements AccountManageService {
	

	@Autowired
	private UserInfoDAO userInfoDAO;
	@Autowired
	private RedisService redisService;


	@Override
	public ResponseInfoDTO<UserInfo> accountLogin(UserInfo userInfo) {

		UserInfo newPersonInfo;

		UserInfoKey userInfoKey = UserInfoKey.getByPhone;
		String key = userInfo.getTelephone();
		/*userInfoKey.setFlag(redisService.stringExists(userInfoKey, key));

		if(userInfoKey.isFlag()){
			System.out.println("get redis");
			newPersonInfo = redisService.stringGet(userInfoKey, key, UserInfo.class);
		}else {//redis不存在该用户缓存，调用数据库
			System.out.println("set redis");
			newPersonInfo = userInfoDAO.selectUserPasswordByPhone(userInfo.getTelephone());
		}

		//UserInfo newPersonInfo = userInfoDAO.selectUserPasswordByPhone(userInfo.getTelephone());
		if(newPersonInfo != null) {
			if(newPersonInfo.getPassword().equals(userInfo.getPassword())) {

				if(!userInfoKey.isFlag()){//存放用户信息于redis
					redisService.stringSet(userInfoKey,key, newPersonInfo);
				}

				return ResponseInfoDTO.success(MessageDTO.LOGIN_SUCCESS, newPersonInfo);
			}else {
				return ResponseInfoDTO.fail(MessageDTO.LOGIN_FAIL_1);
			}
		}else {
			return ResponseInfoDTO.fail(MessageDTO.LOGIN_FAIL_3);
		}*/

		userInfoKey.setFlag(redisService.hashExists(userInfoKey, key));

		if(userInfoKey.isFlag()){
			System.out.println("get redis");
			newPersonInfo = redisService.hashGet(userInfoKey, key, UserInfo.class);
		}else {//redis不存在该用户缓存，调用数据库
			System.out.println("set redis");
			newPersonInfo = userInfoDAO.selectUserPasswordByPhone(userInfo.getTelephone());
		}

		//UserInfo newPersonInfo = userInfoDAO.selectUserPasswordByPhone(userInfo.getTelephone());
		if(newPersonInfo != null) {
			if(newPersonInfo.getPassword().equals(userInfo.getPassword())) {

				if(!userInfoKey.isFlag()){//存放用户信息于redis
					redisService.hashSet(userInfoKey,key, newPersonInfo);
				}
				newPersonInfo.setPassword("");
				return ResponseInfoDTO.success(MessageDTO.LOGIN_SUCCESS, newPersonInfo);
			}else {
				return ResponseInfoDTO.fail(MessageDTO.LOGIN_FAIL_1);
			}
		}else {
			return ResponseInfoDTO.fail(MessageDTO.LOGIN_FAIL_3);
		}

	}

    @Override
    public ResponseInfoDTO<UserInfo> accountCookie(UserInfo userInfo) {

        UserInfo getUserInfo = userInfoDAO.selectUserExistByPhone(userInfo.getTelephone());
        if(getUserInfo==null){
            return ResponseInfoDTO.fail("cookies不存在，您未登录");
        }else{
            if(getUserInfo.getName().equals(userInfo.getName())){
                return ResponseInfoDTO.success(userInfo);
            }else{
                return ResponseInfoDTO.fail("cookies存在错误，请您重新登录");
            }
        }


    }

	@Override
	public ResponseInfoDTO<UserInfo> accountRegister(UserInfo userInfo, MultipartFile userImg) {

		if(userInfoDAO.selectUserExistByPhone(userInfo.getTelephone())!=null){
			return ResponseInfoDTO.fail(MessageDTO.REGISTER_FAIL_1);
		}

		if(userImg != null){
			try{
				addUserImg(userInfo,userImg);
			}catch (Exception e) {
				throw new RuntimeException("--------------addUser创建图片地址失败"+e.getMessage());
			}
		}

		int flag = userInfoDAO.insertUserInfo(userInfo);
		if(flag == 1){
			return ResponseInfoDTO.success(MessageDTO.REGISTER_SUCCESS);
		}
		return ResponseInfoDTO.fail(MessageDTO.REGISTER_FAIL_2);

	}

	/**头像处理**/
    private void addUserImg(UserInfo userInfo, MultipartFile userImg) {
        String dest = FileUtil.getUserInfoImagePath(userInfo.getTelephone());
        String userImgAddr = ImageUtil.generateNormalImg(userImg, dest);
        userInfo.setImage(userImgAddr);
    }


	@Override
	public ResponseInfoDTO<UserInfo> modifyAccountPassword(UserInfo userInfo, String new_password) {

		UserInfo getUserInfo = userInfoDAO.selectUserInfoByPhone(userInfo.getTelephone());
		if(getUserInfo == null) {
			return ResponseInfoDTO.fail(MessageDTO.UPDATE_PWD_FAIL_1);
		}else {

			if(getUserInfo.getPassword().equals(userInfo.getPassword())) {

				userInfo.setPassword(new_password);
				int flag = userInfoDAO.updatePasswordByPhone(userInfo);
				if(flag == 1) {
					return ResponseInfoDTO.success(MessageDTO.UPDATE_PWD_SUCCESS);
				}else {
					return ResponseInfoDTO.fail(MessageDTO.UPDATE_PWD_FAIL_3);
				}
			}
			return ResponseInfoDTO.fail(MessageDTO.UPDATE_PWD_FAIL_2);
		}
	}



	@Override
	public ResponseInfoDTO<UserInfo> getAccountInfo(String phone) {
		
		UserInfo getUserInfo = userInfoDAO.selectUserInfoByPhone(phone);
		if(getUserInfo == null) {
			return ResponseInfoDTO.fail(MessageDTO.QUERY_USER_FAIL);
		}else {
			return ResponseInfoDTO.success(MessageDTO.QUERY_USER_SUCCESS, getUserInfo);
		}
	}

	@Override
	public ResponseInfoDTO<UserInfo> modifyAccountInfo(UserInfo userInfo) {

		int flag = userInfoDAO.updateUserInfoByPhone(userInfo);
		if(flag == 1) {
			return ResponseInfoDTO.success(MessageDTO.UPDATE_USER_SUCCESS);
		}else {
			return ResponseInfoDTO.fail(MessageDTO.UPDATE_USER_FAIL);
		}
	}

	@Override
	public ResponseInfoDTO<UserInfo> modifyAccountImage(UserInfo userInfo, MultipartFile userImg) {


    	UserInfo getUserInfo = userInfoDAO.selectUserPasswordByPhone(userInfo.getTelephone());
		File del_file = new File("D:/Download/o2o/images"+getUserInfo.getImage());
		System.out.println(del_file);
		if(del_file.isFile()){
			del_file.delete();
		}

		if(userImg != null){
			try{
				addUserImg(userInfo,userImg);
			}catch (Exception e) {
				throw new RuntimeException("--------------addUser创建图片地址失败"+e.getMessage());
			}
		}



		int flag = userInfoDAO.updateUserInfoByPhone(userInfo);
		if(flag == 1) {
			return ResponseInfoDTO.success(MessageDTO.UPDATE_USER_SUCCESS);
		}else {
			return ResponseInfoDTO.fail(MessageDTO.UPDATE_USER_FAIL);
		}
	}


}
