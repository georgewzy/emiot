package com.em.tools.tag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.em.tools.Util;

/**
 * Tag 类
 * 
 * @author xiaoxiao
 *
 */
public class MyFunctionTag {
    
    /**
     * 将日期转成时间戳
     * @param d
     * @return
     */
    public static Long toTimeStamp(Date d) {
        return d.getTime();
    }
    
    public static String toEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }
    
    //判断多个逗号分隔的String是否包含object
    public static boolean isContainObject(String str, Object o) {
        boolean flag = false;
        for (String s : str.split(",")) {
            if (StringUtils.equals(s, String.valueOf(o))) {
                flag = true;
                break;
            }
        }
        
        return flag;
    }
    
    //判断List列表是否包含子元素
    public static boolean isContain(List<Object> list, Object o) {
        return list.contains(o);
    }
    
    //返回日期间隔天数
    public static int diffDay(Date start, Date end) {
        return Util.getDiffDays(start, end);
    }
    
    //返回日期间隔年数 - 计算年龄
    public static int diffYear(Date start, Date end) {
        return Util.getDiffYears(start, end);
    }
    
    //replace
    public static String replace(String str, String replace, String replacement) {
        return str.replaceAll(replace, replacement);
    }
    
    //removeHtml
    public static String removeHtml(String str) {
        return str.replaceAll("<.*?>", "").replaceAll("\\s", "").replaceAll("\n", "&nbsp;").replaceAll("\'", "&acute;");
    }
    
    //Shiro: has Permission ?
    public static boolean hasPermission(String permission) {
        if (Util.isEmptyString(permission))
            return false;
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isPermitted(permission)) {
            return true;
        }
        return false;
    }
}
