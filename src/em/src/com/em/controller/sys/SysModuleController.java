package com.em.controller.sys;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysModule;
import com.em.model.SysModuleFunction;
import com.em.model.SysModuleFunctionOperation;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/module", viewPath="/pages/sys/module")
public class SysModuleController extends BaseController {

    @Override
    @RequiresPermissions("sys.module.query")
    public void index() {
        // TODO Auto-generated method stub
        SysModule module     = getModel(SysModule.class, "module");
        List<SysModule> list = SysModule.dao.list(module);
        for (SysModule m : list) {
            m.put("functionList", SysModuleFunction.dao.list(m.getStr("id")));
        }
        setAttr("module", module);
        setAttr("list", list);
        setAttr("moduleid", getPara("moduleid"));
        render("list.jsp");
    }
    
    @RequiresPermissions(value={"sys.module.add", "sys.module.edit"}, logical=Logical.OR)
    public void edit() {
        String id = getPara();
        SysModule module = new SysModule();
        if (id != null) {
            module = SysModule.dao.findById(id);
        } else {
            module.set("order", 99);
        }
        setAttr("module", module);
        render("edit.jsp");
    }
    
    @RequiresPermissions(value={"sys.module.add", "sys.module.edit"}, logical=Logical.OR)
    @Before(Tx.class)
    public void save() {
        SysModule module = getModel(SysModule.class, "module");
        SysModule old    = SysModule.dao.getByColumn("name", module.getStr("name"), "id");
        if (old != null && !StringUtils.equals(module.getStr("id"), old.getStr("id"))) {
            render(BjuiRender.error("保存失败，已存在名称为[ "+ module.getStr("name") +" ]的模块！"));
            return;
        }
        if (Util.isEmptyString(module.get("id"))) {
            module
                .set("id", Util.getUUID())
                .setCreateInfo()
                .save();
        } else {
            module
                .setUpdateInfo()
                .update();
        }
        
        render(BjuiRender.successAndCloseCurrent());
    }
    
    //del
    @RequiresPermissions("sys.module.del")
    @Before(Tx.class)
    public void del() {
        String id        = getPara();
        SysModule module = SysModule.dao.findById(id);
        if (module == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (!SysModule.dao.isDel(id)) {
            render(BjuiRender.error("禁止删除，该模块功能已有角色在使用！"));
            return;
        }
        module.update4DelById(id);
        /* del child module */
        SysModuleFunction.dao.delByModuleid(id);
        /* del role operation */
        SysModuleFunctionOperation.dao.delByModuleid(id);
        
        render(BjuiRender.success());
    }
    
    // save order
    @RequiresPermissions(value={"sys.module.add", "sys.module.edit"}, logical=Logical.OR)
    @Before(Tx.class)
    public void saveOrder() {
        int count = getParaToInt("count", 0);
        for (int i = 0; i < count; i++) {
            SysModule module = getModel(SysModule.class, "moduleList["+ i +"]");
            if (module != null && !Util.isEmptyString(module.get("id"))) {
                module.update();
                
                String function_count = module.getStr("function_count");
                if (function_count != null) {
                    int funcCount = Integer.valueOf(function_count);
                    for (int j = 0; j < funcCount; j++) {
                        SysModuleFunction func = getModel(SysModuleFunction.class, "functionList["+ i +"]["+ j +"]");
                        if (func != null && !Util.isEmptyString(func.get("id"))) {
                            func.update();
                        }
                    }
                }
            }
        }
        render(BjuiRender.success());
    }
    
    /********************************************/
    
    @RequiresPermissions(value={"sys.module.add", "sys.module.edit"}, logical=Logical.OR)
    public void editFunction() {
        String moduleid   = getPara(0);
        String functionid = getPara(1);
        SysModuleFunction function = new SysModuleFunction();
        if (functionid != null) {
            function = SysModuleFunction.dao.findById(functionid);
        } else {
            function.set("order", 99);
        }
        if (moduleid != null) {
            SysModule module = SysModule.dao.findById(moduleid);
            if (module != null) {
                function.put("modulename", module.get("shuoming"));
                function.set("moduleid", module.get("id"));
            }
        }
        setAttr("function", function);
        render("module-function-edit.jsp");
    }
    
    @RequiresPermissions(value={"sys.module.add", "sys.module.edit"}, logical=Logical.OR)
    public void saveFunction() {
        SysModuleFunction function = getModel(SysModuleFunction.class, "function");
        SysModuleFunction old      = SysModuleFunction.dao.getByColumn("name", function.getStr("name"), "id");
        if (old != null && !StringUtils.equals(function.getStr("id"), old.getStr("id"))) {
            render(BjuiRender.error("保存失败，已存在名称为[ "+ function.getStr("name") +" ]的模块功能！"));
            return;
        }
        if (Util.isEmptyString(function.get("id"))) {
            function
                .set("id", Util.getUUID())
                .setCreateInfo()
                .save();
        } else {
            function
                .setUpdateInfo()
                .update();
        }
        render(BjuiRender.successAndCloseCurrent());
    }
    
    @RequiresPermissions("sys.module.del")
    public void delFunction() {
        String id                  = getPara();
        SysModuleFunction function = null;
        if (id != null) function = SysModuleFunction.dao.findById(id);
        if (function == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (!SysModuleFunction.dao.isDel(id)) {
            render(BjuiRender.error("禁止删除，该模块功能已有角色在使用！"));
            return;
        }
        function.update4DelById(id);
        /* delete role operation */
        SysModuleFunctionOperation.dao.delByModulefunctionid(id);
        
        render(BjuiRender.success());
    }
    
}
