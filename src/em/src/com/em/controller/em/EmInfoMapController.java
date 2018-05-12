package com.em.controller.em;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.em.controller.BaseController;
import com.em.model.em.EmArea;
import com.em.model.em.EmInfo;
import com.em.tools.Util;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

@ControllerBind(controllerKey="/sys/emmap", viewPath="/pages/em/list")
public class EmInfoMapController extends BaseController {

    @Override
    @RequiresPermissions("eminfo.map.query")
    public void index() {
        // TODO Auto-generated method stub
        String id     = getPara();
        String areaid = getPara("areaid");
        String place  = getPara("place");
        EmInfo emInfo = new EmInfo();
        if (!Util.isEmptyString(areaid)) {
            EmArea area = EmArea.dao.getFather(areaid);
            if (area != null) {
                place = area.getStr("province");
            }
            String ids = EmArea.dao.getChildrenids(place);
            setAttr("ids", area.get("id") +","+ ids);
        }
        
        List<EmInfo> list = EmInfo.dao.list(emInfo);
        EmInfo em = EmInfo.dao.findById(id);
        
        setAttr("em", em);
        setAttr("list", JsonKit.toJson(list));
        setAttr("place", place);
        render("em_map.jsp");
    }
    
    @RequiresPermissions("eminfo.map.query")
    public void list() {
        String orderField  = this.getOrderBy();
        Integer pageNumber = getParaToInt("pageCurrent", 1);
        Integer pageSize   = getParaToInt("pageSize", this.pageSize);
        EmInfo em          = getModel(EmInfo.class, "em");
        Page<EmInfo> page  = EmInfo.dao.paging(em, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    public void getEm() {
        String id = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        renderJson(em);
    }
    
}
