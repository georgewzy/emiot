package com.em.controller.sqlserver;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysArea;
import com.em.model.em.EmInfo;
import com.em.model.em.EmType;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

//@ControllerBind(controllerKey="/sys/em", viewPath="/pages/em")
public class EquipmentController extends BaseController {

    @Override
    @RequiresPermissions("em.em.query")
    public void index() {
        // TODO Auto-generated method stub
        List<SysArea> areaList         = SysArea.dao.list();
        List<EmType> typeList   = EmType.dao.list();
        
        setAttr("areaList", areaList);
        setAttr("typeList", typeList);
        render("equipment_list.jsp");
    }
    
    @RequiresPermissions("em.em.query")
    public void list() {
        String orderField    = this.getOrderBy();
        Integer pageNumber   = getParaToInt("pageCurrent", 1);
        Integer pageSize     = getParaToInt("pageSize", this.pageSize);
        EmInfo em         = getModel(EmInfo.class, "em");
        Page<EmInfo> page = EmInfo.dao.paging(em, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    public void edit() {
        String id = getPara();
        if (Util.isEmptyString(id)) {
            if (noPermissions("em.em.add")) return;
        } else {
            if (noPermissions("em.em.edit")) return;
        }
        EmInfo em = EmInfo.dao.findById(id);
        
        setAttr("em", em);
        setAttr("areaList", SysArea.dao.list());
        setAttr("typeList", EmType.dao.list());
        render("equipment_edit.jsp");
    }
    
    @RequiresPermissions("em.em.add")
    public void save() {
        EmInfo em = getModel(EmInfo.class, "em");
        if (Util.isEmptyString(em.get("equipmentid"))) {
            em.set("equipmentid", Util.getUUID()).save();
        } else {
            em.update();
        }
        render(BjuiRender.successAndCloseCurrent());
    }
    
    @RequiresPermissions("em.em.del")
    public void del() {
        String id    = getPara("equipmentid");
        EmInfo em = EmInfo.dao.findById(id);
        if (em == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        em.delete();
        render(BjuiRender.success());
    }
    
}
