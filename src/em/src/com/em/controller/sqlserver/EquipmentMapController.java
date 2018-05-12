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
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

//@ControllerBind(controllerKey="/sys/emmap", viewPath="/pages/emmap")
public class EquipmentMapController extends BaseController {

    @Override
    @RequiresPermissions("emmap.maplist.query")
    public void index() {
        // TODO Auto-generated method stub
        String id = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        
        List<EmInfo> list = EmInfo.dao.list(new EmInfo());
        
        setAttr("em", em);
        setAttr("list", JsonKit.toJson(list));
        render("equipment_map.jsp");
    }
    
    @RequiresPermissions("emmap.maplist.query")
    public void list() {
        String orderField    = this.getOrderBy();
        Integer pageNumber   = getParaToInt("pageCurrent", 1);
        Integer pageSize     = getParaToInt("pageSize", this.pageSize);
        EmInfo em         = getModel(EmInfo.class, "em");
        Page<EmInfo> page = EmInfo.dao.paging(em, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    public void getEm() {
        String id    = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        renderJson(em);
    }
    
    public void view() {
        String id       = getPara("id");
        EmInfo em = EmInfo.dao.getBy(id);
        setAttr("em", em);
        setAttr("areaMap", SysArea.dao.listMap());
        setAttr("areaList", SysArea.dao.list());
        setAttr("typeList", EmType.dao.list());
        render("equipment_list_currentinfo.jsp");
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
