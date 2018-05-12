package com.em.controller.sqlserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.em.EmType;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.tx.Tx;

//@ControllerBind(controllerKey="/sys/emtype", viewPath="/pages/em")
public class EquipmentTypeController extends BaseController {

    @Override
    @RequiresPermissions("em.emtype.query")
    public void index() {
        // TODO Auto-generated method stub
        List<EmType> list = EmType.dao.list();
        
        setAttr("list", JsonKit.toJson(list));
        render("equipment_type_list.jsp");
    }
    
    @RequiresPermissions(value = {"em.emtype.add", "em.emtype.edit"}, logical = Logical.OR)
    @Before(Tx.class)
    public void save() {
        JSONArray jsonArray = JSONArray.parseArray(getPara("json"));
        if (jsonArray == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        List<EmType> list = new ArrayList<EmType>();
        for (Object o : jsonArray) {
            Map<String, Object> map = (Map<String, Object>) o;
            EmType type = new EmType();
            type.setAttrs(map);
            type.set("equipmenttypeorder", Integer.valueOf(String.valueOf(type.get("equipmenttypeorder"))));
            if (Util.isEmptyString(map.get("equipmenttypeid"))) {
                type
                    .set("equipmenttypeid", Util.getUUID())
                    .save();
            } else {
                type.update();
            }
            list.add(type);
        }
        
        renderJson(list);
    }
    
    //del
    @RequiresPermissions("em.emtype.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("equipmenttypeid");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            EmType.dao.deleteById(id.split(","));
        } else {
            EmType.dao.deleteById(id);
        }
    }
}