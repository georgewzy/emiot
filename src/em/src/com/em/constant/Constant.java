package com.em.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public class Constant {
    
    /*Session*/
    public static final String SYS_SESSION_ID           = "sys_session_id";             // session id
    public static final String SYS_SESSION_SHA256_KEY   = "sys_session_sha1_key";       // 随机的盐
    public static final String SYS_SESSION_ADMIN        = "sys_session_admin";          // 管理员信息
    public static final String SYS_SESSION_ADMIN_POWERS = "sys_session_admin_powers";   // 管理组权限信息
    public static final String COOKIE_USERNAME          = "cookie_username";            // username for ajaxlogin
    
    public static final String SYS_MAIL_SMTP            = "smtp.qq.com";                // 系统邮件smtp
    public static final String SYS_MAIL_USER            = "管理系统";                    // 系统邮件发信人
    public static final String SYS_MAIL_ADD             = "503867007@qq.com";           // 系统邮件地址
    public static final String SYS_MAIL_PASS            = "xiao6666";                   // 系统邮件密码
    public static final int PASSWORDLENGTH              = 6;                            // 密码长度
    
    /* sql Server 数据源名称 */
    public static final String SQLSERVER = "sqlserver";
    
    /*错误提示*/
    public static final String ERR_URL        = "URL错误！";
    public static final String ERR_SYS        = "系统错误！";
    public static final String ERR_PERMISSION = "对不起，您没有权限操作！";
    
    /*上传目录*/
    public static final String UP_ROOT              = "upload/";
    public static final String UP_ROOT_IMAGE        = "upload/images/";         //图片
    public static final String UP_ROOT_IMAGE_THUMB  = "upload/images/thumb/";   //缩略图
    public static final String UP_ROOT_FLASH        = "upload/flash/";          //flash
    public static final String UP_ROOT_FILES        = "upload/files/";          //files
    public static final String UP_ROOT_VIDEO        = "upload/video/";          //video
    
    /*缩略图尺寸*/
    public static int THUMB_TITLEPIC_W = 150; //标题图片 
    public static int THUMB_TITLEPIC_H = 100;
    
    /*系统操作日志记录类型*/
    public interface OPERATIONTYPE {
        String ADD     = "add";
        String EDIT    = "edit";
        String DELETE  = "delete";
        String SETTING = "setting";
        String LOGIN   = "login";
        String LOGOUT  = "logout";
    }
    public static final Map<String, String> OPERATION_TYPE = new LinkedHashMap<String, String>();
    static {
        OPERATION_TYPE.put(OPERATIONTYPE.ADD, "添加");
        OPERATION_TYPE.put(OPERATIONTYPE.EDIT, "修改");
        OPERATION_TYPE.put(OPERATIONTYPE.DELETE, "删除");
        OPERATION_TYPE.put(OPERATIONTYPE.SETTING, "设置");
        OPERATION_TYPE.put(OPERATIONTYPE.LOGIN, "登录");
        OPERATION_TYPE.put(OPERATIONTYPE.LOGOUT, "退出");
    }
    
}
