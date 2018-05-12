package com.em.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysEmployee;
import com.em.model.SysUsers;
import com.em.model.em.EmArea;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey = "/sys/employee", viewPath = "/pages/sys/employee")
public class SysEmployeeController extends BaseController {

    @Override
    @RequiresPermissions("sys.employee.query")
    public void index() {
        // TODO Auto-generated method stub
        setAttr("emAreaList", EmArea.dao.list4Select());
        render("list.jsp");
    }
    
    @RequiresPermissions("sys.employee.query")
    public void list() {
        String orderField      = this.getOrderBy();
        Integer pageNumber     = getParaToInt("pageCurrent", 1);
        Integer pageSize       = getParaToInt("pageSize", this.pageSize);
        SysEmployee employee   = getModel(SysEmployee.class, "employee");
        SysUsers user = getUser();
        
        if (!user.getStr("id").equals("super") && !Util.isEmptyString(user.get("areaids"))) {
            employee.put("areaids", user.get("areaids"));
        }
        
        Page<SysEmployee> page = SysEmployee.dao.paging(employee, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    @RequiresPermissions(value={"sys.employee.add", "sys.employee.edit"}, logical=Logical.OR)
    public void edit() {
        String id = getPara();
        SysEmployee employee  = SysEmployee.dao.findById(id);
        List<EmArea> areaList = EmArea.dao.list4Select();
        if (employee == null) {
            if (noPermissions("sys.employee.add")) return;
        } else {
            if (noPermissions("sys.employee.edit")) return;
        }
        setAttr("employee", employee);
        setAttr("areaList", areaList);
        render("edit.jsp");
    }
    
    @RequiresPermissions(value={"sys.employee.add", "sys.employee.edit"}, logical=Logical.OR)
    @Before(Tx.class)
    public void save() {
        SysEmployee employee = getModel(SysEmployee.class, "employee");
        /* save */
        if (Util.isEmptyString(employee.get("id"))) {
            employee
                .set("id", Util.getUUID())
                .setCreateInfo()
                .save();
        } else {
            employee
                .setUpdateInfo()
                .update();
        }
        render(BjuiRender.successAndCloseCurrent());
    }
    
    @RequiresPermissions("sys.employee.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            SysEmployee.dao.update4DelByIds(id);
            // 移除登陆用户
            for (String s : id.split(",")) {
                SysUsers.dao.delByEmployeeid(s);
            }
        } else {
            new SysEmployee().update4DelById(id);
            // 移除登陆用户
            SysUsers.dao.delByEmployeeid(id);
        }
        render(BjuiRender.success());
    }
    
}
