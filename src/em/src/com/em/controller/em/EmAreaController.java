package com.em.controller.em;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.CodeProvince;
import com.em.model.em.EmArea;
import com.em.model.json.TreeMenu;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/em/area", viewPath="/pages/em/area")
public class EmAreaController extends BaseController {

    @Override
    @RequiresPermissions("em.emarea.query")
    public void index() {
        // TODO Auto-generated method stub
        List<EmArea> list = EmArea.dao.list();
        setAttr("list", list);
        setAttr("provinceList", CodeProvince.dao.list());
        render("list.jsp");
    }
    
    @RequiresPermissions(value={"em.emarea.add", "em.emarea.edit"}, logical=Logical.OR)
    @Before(Tx.class)
    public void save() {
        String json = getPara("emareas");
        
        List<TreeMenu> list = JSONArray.parseArray(json, TreeMenu.class);
        EmArea emarea = null;
        for (TreeMenu t : list) {
            emarea = new EmArea();
            emarea
                .set("parentid", null)
                .set("name", t.getName())
                .set("province", Util.isEmptyString(t.getProvince()) ? null : t.getProvince())
                .set("seq", t.getOrder())
                .set("level", t.getLevel());
            if (Util.isEmptyString(t.getId())) {
                if (noPermissions("em.emarea.add")) {
                    throw new RuntimeException("对不起，你没有添加单位的权限！");
                }
                emarea.set("id", Util.getUUID()).setCreateInfo().save();
            } else {
                if (noPermissions("em.emarea.edit")) {
                    throw new RuntimeException("对不起，你没有修改单位的权限！");
                }
                emarea.set("id", t.getId()).setUpdateInfo().update();
            }
            if (t.getChildren() != null) {
                this.saveChildren(emarea.getStr("id"), t.getChildren());
            }
        }
        
        render(BjuiRender.success());
    }
    
    private void saveChildren(String parentid, List<TreeMenu> chidren) {
        EmArea emarea = null;
        for (TreeMenu t : chidren) {
            emarea = new EmArea();
            emarea
                .set("parentid", parentid)
                .set("name", t.getName())
                .set("seq", t.getOrder())
                .set("level", t.getLevel());
            if (Util.isEmptyString(t.getId())) {
                if (noPermissions("em.emarea.add")) {
                    throw new RuntimeException("对不起，你没有添加单位的权限！");
                }
                emarea.set("id", Util.getUUID()).setCreateInfo().save();
            } else {
                if (noPermissions("em.emarea.edit")) {
                    throw new RuntimeException("对不起，你没有修改单位的权限！");
                }
                emarea.set("id", t.getId()).setUpdateInfo().update();
            }
            if (t.getChildren() != null) this.saveChildren(emarea.getStr("id"), t.getChildren());
        }
    }
    
    //del
    @RequiresPermissions("em.emarea.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara();
        EmArea m = EmArea.dao.findById(id);
        if (m == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        m.update4DelById(id);
        //del children
        EmArea.dao.delChildren(id);
        
        render(BjuiRender.success());
    }
    
}
