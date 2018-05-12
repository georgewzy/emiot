package com.em.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.em.constant.Constant;
import com.em.model.SysUsers;
import com.em.model.SysUsersArea;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.core.Controller;
import com.jfinal.ext.plugin.shiro.ShiroMethod;
import com.jfinal.log.Logger;

public abstract class BaseController extends Controller {

    protected int pageNumber = 1;
    protected int pageSize   = 30;
    protected int batchSize  = 100;
    public static Logger log = Logger.getLogger(BaseController.class);
    
    public abstract void index();
    
    @Override
    public void render(String view) {
        super.render(view);
    }
    
    public SysUsers getUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.getSession(false) == null || !currentUser.isAuthenticated()) return null;
        SysUsers users = (SysUsers) currentUser.getSession(false).getAttribute(Constant.SYS_SESSION_ADMIN);
        
        // 管辖区域 areaids
        String areaids = SysUsersArea.dao.getAreaIds(users.getStr("id"));
        users.put("areaids", areaids);
        
        return users;
    }
    
    /**
     * 检测 权限
     */
    protected boolean noPermissions(String permissions) {
        if (ShiroMethod.lacksPermission(permissions)) {
            render(BjuiRender.error(Constant.ERR_PERMISSION));
            return true;
        }
        return false;
    }
    
    /**
     * 判断浏览器类型是否是IE,是则返回true,不是返回false
     * @return boolean
     */
    public boolean isNotIE(){
        String useragent = (getRequest().getHeader("USER-AGENT")).toLowerCase();
        if (useragent.indexOf("chrome") > 0 || useragent.indexOf("safari") > 0 || useragent.indexOf("firefox") > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 组装order by From B-JUI
     * @return
     */
    public String getOrderBy() {
        String orderBy = null;
        String orderField         = getPara("orderField");
        String orderDirection     = getPara("orderDirection");
        if (!Util.isEmptyString(orderField)) {
            orderBy = orderField +" "+ orderDirection;
            setAttr("orderField", orderField);
            setAttr("orderDirection", orderDirection);
        }
        return orderBy;
    }
    
    /**
     * 转到URL错误页
     */
    public void toAjaxUrlError() {
        //render(JyJsonRender.error(JyConstant.ERR_URL));
    }
    public void toUrlError() {
        setAttr("errMsg", Constant.ERR_URL);
        renderError(404);
    }
    
    /**
     * 转到SYS错误页
     */
    public void toAjaxError() {
        //render(MyDwzRender.error(CmsConstant.ERR_SYS));
    }
    public void toError() {
        //setAttr("errMsg", CmsConstant.ERR_SYS);
        renderError(404);
    }
    
}
