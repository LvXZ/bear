package com.group3.dto;

import com.group3.util.PropertiesUtil;

/**
 * @ClassName:
 * @Description:
 * @Author: lvxz
 * @Date: 2018-07-26  18:02
 */
public class MessageDTO {

    private int code;
    private String msg;

    //general exception
    public final static MessageDTO DATEBASE_ERROR = new MessageDTO(PropertiesUtil.getInt("database.error.code"),PropertiesUtil.getString("database.error.msg"));
    public final static MessageDTO SERVLET_ERROR = new MessageDTO(PropertiesUtil.getInt("servlet.error.code"),PropertiesUtil.getString("servlet.error.msg"));

    public final static MessageDTO SUCCESS = new MessageDTO(1,"成功");

    //user-login
    public final static MessageDTO LOGIN_SUCCESS = new MessageDTO(1,PropertiesUtil.getString("login.success"));
    public final static MessageDTO LOGIN_FAIL_1 = new MessageDTO(0,PropertiesUtil.getString("login.fail_1"));
    public final static MessageDTO LOGIN_FAIL_2 = new MessageDTO(0,PropertiesUtil.getString("login.fail_2"));
    public final static MessageDTO LOGIN_FAIL_3 = new MessageDTO(0,PropertiesUtil.getString("login.fail_3"));
    //user-register
    public final static MessageDTO REGISTER_SUCCESS = new MessageDTO(1,PropertiesUtil.getString("register.success"));
    public final static MessageDTO REGISTER_FAIL_1 = new MessageDTO(0,PropertiesUtil.getString("register.fail_1"));
    public final static MessageDTO REGISTER_FAIL_2 = new MessageDTO(0,PropertiesUtil.getString("register.fail_2"));
    public final static MessageDTO REGISTER_FAIL_3 = new MessageDTO(0,"注册失败，缺少图片");
    //user-update-password
    public final static MessageDTO UPDATE_PWD_SUCCESS = new MessageDTO(1,PropertiesUtil.getString("update.success"));
    public final static MessageDTO UPDATE_PWD_FAIL_1 = new MessageDTO(0,"更新密码失败，账户不存在");
    public final static MessageDTO UPDATE_PWD_FAIL_2 = new MessageDTO(0,"更新密码失败，原密码输入错误");
    public final static MessageDTO UPDATE_PWD_FAIL_3 = new MessageDTO(0,"更新密码失败，数据库拒绝更新密码");
    //user-get-info
    public final static MessageDTO QUERY_USER_SUCCESS = new MessageDTO(1,"获取账户信息成功");
    public final static MessageDTO QUERY_USER_FAIL = new MessageDTO(0,"获取账户信息失败，账户不存在");
    //user-update-info
    public final static MessageDTO UPDATE_USER_SUCCESS = new MessageDTO(1,"更新账户信息成功");
    public final static MessageDTO UPDATE_USER_FAIL = new MessageDTO(0,"更新账户信息失败，数据库拒绝更新密码");











    private MessageDTO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
