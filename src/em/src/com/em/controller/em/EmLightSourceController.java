package com.em.controller.em;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.em.EmLightSource;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/em/lightsource", viewPath="/pages/em/property")
public class EmLightSourceController extends BaseController {

    @Override
    @RequiresPermissions("em.lightsource.query")
    public void index() {
        // TODO Auto-generated method stub
        List<EmLightSource> list = EmLightSource.dao.list();
        
        setAttr("list", JsonKit.toJson(list));
        render("em_light_source_list.jsp");
    }
    
    @RequiresPermissions(value = {"em.lightsource.add", "em.lightsource.edit"}, logical = Logical.OR)
    @Before(Tx.class)
    public void save() {
        JSONArray jsonArray = JSONArray.parseArray(getPara("json"));
        if (jsonArray == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        List<EmLightSource> list = new ArrayList<EmLightSource>();
        for (Object o : jsonArray) {
            Map<String, Object> map = (Map<String, Object>) o;
            EmLightSource old = EmLightSource.dao.findById(map.get("oldid"));
            EmLightSource obj = new EmLightSource().setAttrs(map).put("oldid", map.get("oldid"));
            if (old == null) {
                old = EmLightSource.dao.findById(map.get("id"));
                if (old != null) {
                    if (old.get("deletetime") == null)
                        throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的灯质源已存在数据库中！");
                    else
                        old.delete();
                }
                obj.setCreateInfo().save();
            } else {
                if (!obj.get("id").equals(obj.get("oldid"))) {
                    EmLightSource replaceM = EmLightSource.dao.findById(obj.get("id"));
                    if (replaceM != null) {
                        if (replaceM.get("deletetime") == null)
                            throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的灯质源已存在数据库中！");
                        else
                            replaceM.delete();
                    }
                    EmLightSource.dao.updatePK(obj.get("oldid"), obj.get("id"));
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
    @RequiresPermissions("em.lightsource.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            EmLightSource.dao.update4DelByIds(id);
        } else {
            EmLightSource.dao.update4DelById(id);
        }
        render(BjuiRender.success());
    }
}