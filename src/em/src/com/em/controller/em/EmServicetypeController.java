package com.em.controller.em;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.em.EmServicetype;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/em/servicetype", viewPath="/pages/em/property")
public class EmServicetypeController extends BaseController {

    @Override
    @RequiresPermissions("em.servicetype.query")
    public void index() {
        // TODO Auto-generated method stub
        List<EmServicetype> list = EmServicetype.dao.list();
        
        setAttr("list", JsonKit.toJson(list));
        render("em_servicetype_list.jsp");
    }
    
    @RequiresPermissions(value = {"em.servicetype.add", "em.servicetype.edit"}, logical = Logical.OR)
    @Before(Tx.class)
    public void save() {
        JSONArray jsonArray = JSONArray.parseArray(getPara("json"));
        if (jsonArray == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        List<EmServicetype> list = new ArrayList<EmServicetype>();
        for (Object o : jsonArray) {
            Map<String, Object> map = (Map<String, Object>) o;
            EmServicetype old = EmServicetype.dao.findById(map.get("oldid"));
            EmServicetype obj = new EmServicetype().setAttrs(map).put("oldid", map.get("oldid"));
            if (old == null) {
                old = EmServicetype.dao.findById(map.get("id"));
                if (old != null) {
                    if (old.get("deletetime") == null)
                        throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的设备维护类型已存在数据库中！");
                    else
                        old.delete();
                }
                obj.setCreateInfo().save();
            } else {
                if (!obj.get("id").equals(obj.get("oldid"))) {
                    EmServicetype replaceM = EmServicetype.dao.findById(obj.get("id"));
                    if (replaceM != null) {
                        if (replaceM.get("deletetime") == null)
                            throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的设备维护类型已存在数据库中！");
                        else
                            replaceM.delete();
                    }
                    EmServicetype.dao.updatePK(obj.get("oldid"), obj.get("id"));
                    obj.setUpdateInfo().update();
                } else {
                    obj.setUpdateInfo().update();
                }
            }
            list.add(obj);
        }
        
        renderJson(list);
    }
    
    //del
    @RequiresPermissions("em.servicetype.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            EmServicetype.dao.update4DelByIds(id);
        } else {
            EmServicetype.dao.update4DelById(id);
        }
        render(BjuiRender.success());
    }
}