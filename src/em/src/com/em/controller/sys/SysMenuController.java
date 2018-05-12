package com.em.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysMenu;
import com.em.model.json.TreeMenu;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/menu", viewPath="/pages/sys/menu")
public class SysMenuController extends BaseController {

    @Override
    @RequiresPermissions("sys.menu.query")
    public void index() {
        // TODO Auto-generated method stub
        List<SysMenu> list = SysMenu.dao.list();
        setAttr("list", list);
        render("list.jsp");
    }
    
    @RequiresPermissions(value={"sys.menu.add", "sys.menu.edit"}, logical=Logical.OR)
    @Before(Tx.class)
    public void save() {
        String json = getPara("menus");
        
        List<TreeMenu> list = JSONArray.parseArray(json, TreeMenu.class);
        SysMenu menu = null;
        for (TreeMenu t : list) {
            menu = new SysMenu();
            menu
                .set("parentid", null)
                .set("name", t.getName())
                .set("url", t.getUrl())
                .set("target", t.getTarget())
                .set("icon", t.getIcon())
                .set("seq", t.getOrder())
                .set("targetid", t.getTargetid())
                .set("level", t.getLevel());
            if (Util.isEmptyString(t.getId())) {
                if (noPermissions("sys.menu.add")) {
                    throw new RuntimeException("对不起，你没有添加菜单的权限！");
                }
                menu.set("id", Util.getUUID()).setCreateInfo().save();
            } else {
                if (noPermissions("sys.menu.edit")) {
                    throw new RuntimeException("对不起，你没有修改菜单的权限！");
                }
                menu.set("id", t.getId()).setUpdateInfo().update();
            }
            if (t.getChildren() != null) {
                this.saveChildren(menu.getStr("id"), t.getChildren());
            }
        }
        
        render(BjuiRender.success());
    }
    
    private void saveChildren(String parentid, List<TreeMenu> chidren) {
        SysMenu menu = null;
        for (TreeMenu t : chidren) {
            menu = new SysMenu();
            menu
                .set("parentid", parentid)
                .set("name", t.getName())
                .set("url", t.getUrl())
                .set("target", t.getTarget())
                .set("icon", t.getIcon())
                .set("seq", t.getOrder())
                .set("targetid", t.getTargetid())
                .set("level", t.getLevel());
            if (Util.isEmptyString(t.getId())) {
                if (noPermissions("sys.menu.add")) {
                    throw new RuntimeException("对不起，你没有添加菜单的权限！");
                }
                menu.set("id", Util.getUUID()).setCreateInfo().save();
            } else {
                if (noPermissions("sys.menu.edit")) {
                    throw new RuntimeException("对不起，你没有添加菜单的权限！");
                }
                menu.set("id", t.getId()).setUpdateInfo().update();
            }
            if (t.getChildren() != null) this.saveChildren(menu.getStr("id"), t.getChildren());
        }
    }
    
    //del
    @RequiresPermissions("sys.menu.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara();
        SysMenu m = SysMenu.dao.findById(id);
        if (m == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        m.update4DelById(id);
        //del children
        SysMenu.dao.delChildren(id);
        
        render(BjuiRender.success());
    }
    
}
