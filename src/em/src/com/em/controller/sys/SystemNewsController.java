package com.em.controller.sys;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysUsers;
import com.em.model.SystemNews;
import com.em.model.SystemNewsCate;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.plugin.shiro.ShiroMethod;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey = "/sys/systemnews", viewPath = "/pages/sys/news")
public class SystemNewsController extends BaseController {

    @Override
    @RequiresPermissions("news.newslist.query")
    public void index() {
        // TODO Auto-generated method stub
        setAttr("cateList", SystemNewsCate.dao.list());
        render("list.jsp");
    }
    
    @RequiresPermissions("news.newslist.query")
    public void list() {
        String orderField      = this.getOrderBy();
        Integer pageNumber     = getParaToInt("pageCurrent", 1);
        Integer pageSize       = getParaToInt("pageSize", this.pageSize);
        SystemNews systemnews   = getModel(SystemNews.class, "systemnews");
        
        SysUsers user  = getUser();
        List<Object> cateids = new ArrayList<Object>();
        if (!user.get("id").equals("super")) {
            List<SystemNewsCate> cateList = SystemNewsCate.dao.list();
            for (SystemNewsCate c : cateList) {
                if (ShiroMethod.hasPermission("news."+ c.getStr("modulename") +".query")) {
                    cateids.add(c.get("id"));
                }
            }
        }
        if (!cateids.isEmpty()) systemnews.put("cateids", cateids);
        
        Page<SystemNews> page = SystemNews.dao.paging(systemnews, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    @RequiresPermissions(value={"news.newslist.add", "news.newslist.edit"}, logical=Logical.OR)
    public void edit() {
        String id = getPara();
        SystemNews systemnews = SystemNews.dao.findById(id);
        setAttr("systemnews", systemnews);
        setAttr("cateList", SystemNewsCate.dao.list());
        render("edit.jsp");
    }
    
    public void view() {
        String id = getPara();
        SystemNews systemnews = SystemNews.dao.findById(id);
        setAttr("systemnews", systemnews);
        render("view.jsp");
    }
    
    @RequiresPermissions(value={"news.newslist.add", "news.newslist.edit"}, logical=Logical.OR)
    @Before(Tx.class)
    public void save() {
        SystemNews systemnews = getModel(SystemNews.class, "systemnews");
        /* save */
        if (Util.isEmptyString(systemnews.get("id"))) {
            systemnews
                .set("id", Util.getUUID())
                .setCreateInfo()
                .save();
        } else {
            systemnews
                .setUpdateInfo()
                .update();
        }
        render(BjuiRender.successAndCloseCurrentAndRefreshTab("sys-systemnews-list"));
    }
    
    @RequiresPermissions("news.newslist.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            SystemNews.dao.update4DelByIds(id);
        } else {
            new SystemNews().update4DelById(id);
        }
        render(BjuiRender.success());
    }
    
}
