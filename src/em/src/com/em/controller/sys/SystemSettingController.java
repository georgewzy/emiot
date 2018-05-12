package com.em.controller.sys;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.em.controller.BaseController;
import com.em.model.SystemSetting;
import com.em.tools.render.BjuiRender;
import com.jfinal.ext.route.ControllerBind;

@ControllerBind(controllerKey = "/sys/setting", viewPath = "/pages/sys/setting")
public class SystemSettingController extends BaseController {

    @Override
    @RequiresPermissions("sys.setting.query")
    public void index() {
        // TODO Auto-generated method stub
        SystemSetting setting = SystemSetting.dao.findById(1);
        setAttr("setting", setting);
        render("list.jsp");
    }
    
    @RequiresPermissions("sys.setting.edit")
    public void save() {
        SystemSetting setting = getModel(SystemSetting.class, "setting");
        setting.update();
        render(BjuiRender.success());
    }
}
