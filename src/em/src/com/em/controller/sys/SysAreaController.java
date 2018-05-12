package com.em.controller.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysArea;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/area", viewPath="/pages/sys/area")
public class SysAreaController extends BaseController {

    @Override
    @RequiresPermissions("sys.area.query")
    public void index() {
        // TODO Auto-generated method stub
        List<SysArea> list = SysArea.dao.list();
        setAttr("list", JsonKit.toJson(list));
        render("list.jsp");
    }
    
    @RequiresPermissions(value = {"sys.area.add", "sys.area.edit"}, logical = Logical.OR)
    @Before(Tx.class)
    public void save() {
        JSONArray jsonArray = JSONArray.parseArray(getPara("json"));
        if (jsonArray == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        List<SysArea> list = new ArrayList<SysArea>();
        for (Object o : jsonArray) {
            Map<String, Object> map = (Map<String, Object>) o;
            SysArea area = new SysArea();
            area.setAttrs(map);
            SysArea old = SysArea.dao.getBy(area.get("seq"));
            if (old != null && !StringUtils.equals(area.getStr("id"), old.getStr("id"))) {
                if (old.get("deletetime") == null)
                    throw new RuntimeException("保存失败！数据库中存在序号为 ["+ old.get("seq") +"] 的区域！");
                else
                    old.delete();
            }
            if (Util.isEmptyString(map.get("id"))) {
                area
                    .set("id", Util.getUUID())
                    .setCreateInfo()
                    .save();
            } else {
                area.setUpdateInfo().update();
            }
            list.add(area);
        }
        renderJson(list);
    }
    
    //del
    @RequiresPermissions("sys.area.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara();
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            SysArea.dao.update4DelByIds(id);
        } else {
            new SysArea().update4DelById(id);
        }
        
        render(BjuiRender.success());
    }
    
}
