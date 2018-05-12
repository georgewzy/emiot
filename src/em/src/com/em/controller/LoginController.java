package com.em.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.em.constant.Constant;
import com.em.model.SysUsers;
import com.em.model.SysUsersLoginlog;
import com.em.model.SystemOperationLog;
import com.em.tools.AESTools;
import com.em.tools.KeyGenerator;
import com.em.tools.Util;
import com.em.tools.captcha.CaptchaServiceSingleton;
import com.em.tools.render.BjuiRender;
import com.em.tools.render.JCaptchaRender;
import com.jfinal.core.ActionKey;
import com.jfinal.ext.route.ControllerBind;

@ControllerBind(controllerKey="/login", viewPath="/pages")
public class LoginController extends BaseController {
    
    @ActionKey("/")
    public void toSys() {
        index();
        //render("/toSys.html");
    }
    
	@Override
	public void index() {
	    if (getUser() != null) {
	        redirect("/sys/");
	        return;
	    }
	    
	    if (Util.isAjax(getRequest())) {
	        render(BjuiRender.timeout());
	        return;
	    }
	    
	    KeyGenerator keygen = new KeyGenerator();
        String randomKey = this.getSessionAttr(Constant.SYS_SESSION_SHA256_KEY);
        if (randomKey == null) {
            try {
                SecretKeySpec key = keygen.getKey();
                randomKey = KeyGenerator.getKeyStr(key);
                this.setSessionAttr(Constant.SYS_SESSION_SHA256_KEY, randomKey);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        
        setAttr("randomKey", randomKey);
		render("login.jsp");
	}
	
	public void getCaptcha() {
	    String captchaId = getSession().getId();
	    this.setSessionAttr(Constant.SYS_SESSION_ID, captchaId);
	    render(new JCaptchaRender(captchaId));
	}
	
	public void err401() {
	    String message = "对不起，您没有权限操作！";
	    if (Util.isAjax(getRequest())) {
	        render(BjuiRender.error(message));
	    } else {
	        renderText(message);
	    }
	}
	
	/**
     * 检查验证码
     * @param captcha
     * @return
     */
    private boolean checkcaptcha(String captcha) {
        // TODO Auto-generated method stub
        String captchaId = getSessionAttr(Constant.SYS_SESSION_ID);
        boolean b = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, captcha);
        if (b == false)
            log.error("验证码不正确！");
        return b;
    }
    
    /**
     * 验证登陆信息
     * @param username
     * @param password
     * @param captcha
     * @return
     */
    private int checkinguser(String username, String password, String captcha) {
        // TODO Auto-generated method stub
        int a = 0;
        /*检查验证码*/
        boolean isCaptcha = this.checkcaptcha(captcha);
        if (!isCaptcha) {
            a = 100;
        } else {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.getSession().setAttribute(Constant.SYS_SESSION_SHA256_KEY, getSessionAttr(Constant.SYS_SESSION_SHA256_KEY));
            /*验证登陆*/
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                token.setRememberMe(true);
                currentUser.login(token);
                a = 999;
            } catch (Exception e) {
                a = 200;
                currentUser.getSession().removeAttribute(Constant.SYS_SESSION_ADMIN);
                //e.printStackTrace();
                log.error("用户名或密码错误！");
            }
        }
        return a;
    }
    
	public void clklogin() {
	    Session session = SecurityUtils.getSubject().getSession();
	    
        /*登陆信息*/
        String username = getPara("username");
        String password = getPara("passwordhash");
        String captcha  = getPara("captcha");
        String key      = (String) session.getAttribute(Constant.SYS_SESSION_SHA256_KEY);
        
        SecretKeySpec kkk = null;
        try {
            kkk = AESTools.getSecretKey(key);
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int a = this.checkinguser(username, AESTools.decrypt(password, kkk), captcha);
        SysUsersLoginlog log = new SysUsersLoginlog();
        log
            .set("id", Util.getUUID())
            .set("username", username)
            .set("loginip", Util.getIpAddr(getRequest()))
            .set("logintime", Util.getSqlTime(new Date()));
        switch (a) {
        case 100:
            setAttr("message", "验证码错误！");
            index();
            break;
        case 200:
            setAttr("message", "用户名或密码错误！");
            // save log
            log.set("beizhu", "fail").save();
            index();
            break;
        case 999:
            if (session.getAttribute(Constant.SYS_SESSION_ADMIN) != null) {
                /*将用户名写入Cookie*/
                setCookie(Constant.COOKIE_USERNAME, username, 86400);
                // save log
                log.set("beizhu", "success").save();
                // save system operation log
                SysUsers user = (SysUsers) session.getAttribute(Constant.SYS_SESSION_ADMIN);
                new SystemOperationLog()
                    .set("id", Util.getUUID())
                    .set("type", Constant.OPERATIONTYPE.LOGIN)
                    .set("name", "登录系统")
                    .set("beizhu", "登陆IP:"+ log.getStr("loginip"))
                    .set("username", username)
                    .set("employeename", user.get("name"))
                    .set("createtime", Util.getSqlTime(new Date()))
                    .save();
                
                redirect("/sys/");
            } else {
                setAttr("message", "用户名或密码错误！");
                index();
            }
            break;
        default:
            break;
        }
	}
	
	public void ajaxlogin() {
	    Session session = SecurityUtils.getSubject().getSession();
	    String username = getPara("username");
        String password = getPara("password");
        String captcha  = getPara("captcha");
        String key      = (String) session.getAttribute(Constant.SYS_SESSION_SHA256_KEY);
        
        SecretKeySpec kkk = null;
        try {
            kkk = AESTools.getSecretKey(key);
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int a = this.checkinguser(username, AESTools.decrypt(password, kkk), captcha);
        
        switch (a) {
        case 100:
            render(BjuiRender.error("验证码不正确！"));
            break;
        case 200:
            render(BjuiRender.error("登陆失败！用户名或密码错误！"));
            break;
        case 999:
            this.removeSessionAttr(Constant.SYS_SESSION_SHA256_KEY);
            /*将用户名写入Cookie*/
            setCookie(Constant.COOKIE_USERNAME, username, 86400);
            render(BjuiRender.successAndCloseCurrent("登陆成功！"));
            break;
        default:
            break;
        }
	}
	
	/**
     * 登陆超时
     */
    public void logintimeout() {
        // TODO Auto-generated method stub
        KeyGenerator keygen = new KeyGenerator();
        String randomKey    = this.getSessionAttr(Constant.SYS_SESSION_SHA256_KEY);
        if (randomKey == null) {
            try {
                SecretKeySpec key = keygen.getKey();
                randomKey = KeyGenerator.getKeyStr(key);
                this.setSessionAttr(Constant.SYS_SESSION_SHA256_KEY, randomKey);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        String username = getPara("username");
        if (Util.isEmptyString(username)) {
            username = getCookie(Constant.COOKIE_USERNAME);
        }
        setAttr("username", username);
        setAttr("randomKey", randomKey);
        render("login_timeout.jsp");
    }
    
    /**
     * 退出登陆
     */
    @ActionKey(value="/logout")
    public void logout() {
        // TODO Auto-generated method stub
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.getSession() != null) {
            // save to system operation log
            SysUsers user = getUser();
            new SystemOperationLog()
                .set("id", Util.getUUID())
                .set("type", Constant.OPERATIONTYPE.LOGOUT)
                .set("name", "退出系统")
                .set("beizhu", "退出系统！")
                .set("username", user.get("username"))
                .set("employeename", user.get("name"))
                .set("createtime", Util.getSqlTime(new Date()))
                .save();
            
            currentUser.logout();
        }
        redirect("/login");
    }
    
}
