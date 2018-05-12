package com.em.controller.em;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SystemOperationLog;
import com.em.model.em.EmArea;
import com.em.model.em.EmInfo;
import com.em.model.em.EmRecordService;
import com.em.model.em.EmServicetype;
import com.em.model.em.EmType;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

@ControllerBind(controllerKey="/sys/em/service", viewPath="/pages/em/service")
public class EmRecordServiceController extends BaseController {

    @Override
    public void index() {
        // TODO Auto-generated method stub
        String id = getPara();
        EmInfo em = EmInfo.dao.getBy(id);
        setAttr("typeList", EmType.dao.list());
        setAttr("servicetypeList", EmServicetype.dao.list());
        setAttr("emAreaList", EmArea.dao.list4Select());
        setAttr("em", em);
        render("list.jsp");
    }
    
    public void list() {
        String orderField          = this.getOrderBy();
        Integer pageNumber         = getParaToInt("pageCurrent", 1);
        Integer pageSize           = getParaToInt("pageSize", this.pageSize);
        EmRecordService rs         = getModel(EmRecordService.class, "service");
        Page<EmRecordService> page = EmRecordService.dao.paging(rs, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    public void edit() {
        String id       = getPara();
        String eminfoid = getPara("eminfoid");
        EmInfo em       = EmInfo.dao.getBy(eminfoid);
        if (em == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        EmRecordService service = EmRecordService.dao.findById(id);
        if (service == null) {
            if (noPermissions("em.emservice.add")) return;
            service = new EmRecordService().put("eminfoid", eminfoid).put("emtypeid", em.get("typeid"));
        } else {
            if (noPermissions("em.emservice.edit")) return;
        }
        setAttr("em", em);
        setAttr("service", service);
        setAttr("servicetypeList", EmServicetype.dao.list());
        render("edit.jsp");
    }
    
    public void save() {
        EmRecordService service = getModel(EmRecordService.class, "service");
        if (Util.isEmptyString(service.get("id"))) {
            if (noPermissions("em.emservice.add")) return;
            service.set("id", Util.getUUID()).setCreateInfo().save();
            SystemOperationLog.dao.createLog(Constant.OPERATIONTYPE.ADD, "添加设备维护记录", "新增了设备维护记录", service.get("eminfoid"));
        } else {
            if (noPermissions("em.emservice.edit")) return;
            service.setUpdateInfo().update();
            SystemOperationLog.dao.createLog(Constant.OPERATIONTYPE.EDIT, "修改设备维护记录", "修改了设备维护记录", service.get("eminfoid"));
        }
        
        render(BjuiRender.successAndCloseCurrent());
    }
    
    @RequiresPermissions("em.emservice.del")
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            EmRecordService.dao.update4DelByIds(id);
            for (String s : id.split(",")) {
                EmRecordService service = EmRecordService.dao.findById(s);
                if (service != null) {
                    EmInfo em = EmInfo.dao.findById(service.get("eminfoid"));
                    SystemOperationLog.dao.createLog(Constant.OPERATIONTYPE.DELETE, "删除设备维护记录", "删除了设备维护记录["+ em.get("name") +"("+ em.getStr("code") +")]", s);
                }
            }
        } else {
            EmInfo.dao.update4DelById(id);
            EmRecordService service = EmRecordService.dao.findById(id);
            if (service != null) {
                EmInfo em = EmInfo.dao.findById(service.get("eminfoid"));
                SystemOperationLog.dao.createLog(Constant.OPERATIONTYPE.DELETE, "删除设备维护记录", "删除了设备维护记录["+ em.get("name") +"("+ em.getStr("code") +")]", id);
            }
        }
        
        render(BjuiRender.success());
    }
    
}
