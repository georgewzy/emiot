package com.em.controller.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysOperation;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/operation", viewPath="/pages/sys/operation")
public class SysOperationController extends BaseController {

    @Override
    @RequiresPermissions("sys.operation.query")
    public void index() {
        // TODO Auto-generated method stub
        List<SysOperation> list = SysOperation.dao.list();
        setAttr("list", JsonKit.toJson(list));
        render("list.jsp");
    }
    
    @RequiresPermissions(value = {"sys.operation.add", "sys.operation.edit"}, logical = Logical.OR)
    @Before(Tx.class)
    public void save() {
        JSONArray jsonArray = JSONArray.parseArray(getPara("json"));
        if (jsonArray == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        List<SysOperation> list = new ArrayList<SysOperation>();
        for (Object o : jsonArray) {
            Map<String, Object> map = (Map<String, Object>) o;
            SysOperation operation = new SysOperation();
            map.remove("createtime");
            map.remove("deletetime");
            map.remove("updatetime");
            operation.setAttrs(map);
            operation.set("order", Integer.valueOf(String.valueOf(operation.get("order"))));
            if (Util.isEmptyString(map.get("id"))) {
                operation
                    .set("id", Util.getUUID())
                    .setCreateInfo()
                    .save();
            } else {
                operation.setUpdateInfo().update();
            }
            list.add(operation);
        }
        
        renderJson(list);
    }
    
    //del
    @RequiresPermissions("sys.operation.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            SysOperation.dao.update4DelByIds(id);
        } else {
            new SysOperation().update4DelById(id);
        }
    }
    
}
