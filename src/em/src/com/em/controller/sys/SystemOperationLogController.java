package com.em.controller.sys;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SystemOperationLog;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

@ControllerBind(controllerKey = "/sys/operationlog", viewPath = "/pages/sys/operationlog")
public class SystemOperationLogController extends BaseController {

    @Override
    @RequiresPermissions("sys.operationlog.query")
    public void index() {
        // TODO Auto-generated method stub
        setAttr("typeMap", Constant.OPERATION_TYPE);
        render("list.jsp");
    }
    
    @RequiresPermissions("sys.operationlog.query")
    public void list() {
        String orderField             = this.getOrderBy();
        Integer pageNumber            = getParaToInt("pageCurrent", 1);
        Integer pageSize              = getParaToInt("pageSize", this.pageSize);
        SystemOperationLog log        = getModel(SystemOperationLog.class, "log");
        Page<SystemOperationLog> page = SystemOperationLog.dao.paging(log, pageSize, pageNumber, orderField);
        renderJson(page);
    }
}
