package com.em.controller.em;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysUsers;
import com.em.model.SystemOperationLog;
import com.em.model.em.EmArea;
import com.em.model.em.EmBattery;
import com.em.model.em.EmBatteryType;
import com.em.model.em.EmBrightlevel;
import com.em.model.em.EmComm;
import com.em.model.em.EmCommP;
import com.em.model.em.EmExcursus;
import com.em.model.em.EmInfo;
import com.em.model.em.EmInfoConfig;
import com.em.model.em.EmInfoSetting;
import com.em.model.em.EmLight;
import com.em.model.em.EmLightSource;
import com.em.model.em.EmPower;
import com.em.model.em.EmType;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/em", viewPath="/pages/em/info")
public class EmInfoController extends BaseController {

    @Override
    @RequiresPermissions("em.em.query")
    public void index() {
        // TODO Auto-generated method stub
        setAttr("emAreaList", EmArea.dao.list4Select());
        setAttr("typeList", EmType.dao.list());
        setAttr("powerList", EmPower.dao.list());
        setAttr("batteryList", EmBattery.dao.list());
        setAttr("batteryTypeList", EmBatteryType.dao.list());
        setAttr("lightList", EmLight.dao.list());
        setAttr("lightSourceList", EmLightSource.dao.list());
        setAttr("brightlevelList", EmBrightlevel.dao.list());
        setAttr("commList", EmComm.dao.list());
        setAttr("commPList", EmCommP.dao.list());
        setAttr("excursusList", EmExcursus.dao.list());
        render("em_info_list.jsp");
    }
    
    @RequiresPermissions("em.em.query")
    public void list() {
        String orderField  = this.getOrderBy();
        Integer pageNumber = getParaToInt("pageCurrent", 1);
        Integer pageSize   = getParaToInt("pageSize", this.pageSize);
        EmInfo em          = getModel(EmInfo.class, "em");
        SysUsers user      = getUser();
        
        if (!user.getStr("id").equals("super") && !Util.isEmptyString(user.get("areaids"))) {
            em.put("areaids", user.get("areaids"));
        }
        
        Page<EmInfo> page  = EmInfo.dao.paging(em, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    public void edit() {
        String id = getPara();
        if (Util.isEmptyString(id)) {
            if (noPermissions("em.em.add")) return;
        } else {
            if (noPermissions("em.em.edit")) return;
        }
        EmInfo em               = EmInfo.dao.getBy(id);
        EmInfoSetting emsetting = null;
        EmInfoConfig emconfig   = null;
        if (em == null) {
            em = new EmInfo()
                .set("typeid", 0)
                .set("powerid", 0)
                .set("excursusid", 0)
                .set("batteryid", 4)
                .set("batterytypeid", 5)
                .set("commid", 3)
                .set("ah", 10)
                .set("brightlevelid", 0)
                .set("cpid", 0);
        } else {
            emsetting = EmInfoSetting.dao.getBy(id);
            emconfig  = EmInfoConfig.dao.getBy(id);
        }
        if (emsetting == null) {
            emsetting = new EmInfoSetting()
                .set("ip", "www.sinoais.com")
                .set("port", "8888")
                .set("apn", "CMNET")
                .set("lightid", 2)
                .set("lightsourceid", 0)
                .set("charginglimit", 4200)
                .set("dischargelimit", 3700)
                .set("forcedwork", false)
                .set("syncwork", false);
        }
        if (emconfig == null) {
            emconfig = new EmInfoConfig().set("events", "FFFF").set("scope", 50).set("voltageupper", 16).set("voltagelimit", 9).set("currentupper", 1).set("currentlimit", 0.1);
        }
        
        setAttr("em", em);
        setAttr("emsetting", emsetting);
        setAttr("emconfig", emconfig);
        setAttr("areaList", EmArea.dao.list4Select());
        //setAttr("areaMap", SysArea.dao.listMap());
        //setAttr("areaList", SysArea.dao.list());
        setAttr("typeList", EmType.dao.list());
        setAttr("powerList", EmPower.dao.list());
        setAttr("batteryList", EmBattery.dao.list());
        setAttr("batteryTypeList", EmBatteryType.dao.list());
        setAttr("lightList", EmLight.dao.list());
        setAttr("lightSourceList", EmLightSource.dao.list());
        setAttr("brightlevelList", EmBrightlevel.dao.list());
        setAttr("commList", EmComm.dao.list());
        setAttr("commPList", EmCommP.dao.list());
        setAttr("excursusList", EmExcursus.dao.list());
        render("em_info_edit.jsp");
    }
    
    @Before(Tx.class)
    public void save() {
        EmInfo em             = getModel(EmInfo.class, "em");
        EmInfoSetting setting = getModel(EmInfoSetting.class, "emsetting");
        EmInfoConfig config   = getModel(EmInfoConfig.class, "emconfig");
        if (Util.isEmptyString(em.get("id"))) {
            if (noPermissions("em.em.add")) return;
        } else {
            if (noPermissions("em.em.edit")) return;
        }
        if (Util.isEmptyString(em.get("id"))) {
            em.set("id", Util.getUUID()).setCreateInfo().save();
            SystemOperationLog.dao.createLog(Constant.OPERATIONTYPE.ADD, "添加设备", "新增了设备["+ em.get("name") +"]", em.get("id"));
        } else {
            em.setUpdateInfo().update();
            SystemOperationLog.dao.createLog(Constant.OPERATIONTYPE.EDIT, "修改设备", "修改了设备["+ em.get("name") +"]", em.get("id"));
        }
        
        if (Util.isEmptyString(setting.get("id"))) {
            setting.set("id", Util.getUUID()).set("eminfoid", em.get("id")).save();
        } else {
            setting.update();
        }
        if (Util.isEmptyString(config.get("id"))) {
            config.set("id", Util.getUUID()).set("eminfoid", em.get("id")).save();
        } else {
            config.update();
        }
        
        render(BjuiRender.successAndCloseCurrentAndRefreshTab("em-list"));
    }
    
    @RequiresPermissions("em.em.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            EmInfo.dao.update4DelByIds(id);
            for (String s : id.split(",")) {
                EmInfo em = EmInfo.dao.findById(s);
                if (em != null) {
                    SystemOperationLog.dao.createLog(Constant.OPERATIONTYPE.DELETE, "删除设备", "删除了设备["+ em.get("name") +"]", s);
                }
            }
        } else {
            EmInfo.dao.update4DelById(id);
            EmInfo em = EmInfo.dao.findById(id);
            if (em != null) {
                SystemOperationLog.dao.createLog(Constant.OPERATIONTYPE.DELETE, "删除设备", "删除了设备["+ em.get("name") +"]", id);
            }
        }
        
        render(BjuiRender.success());
    }
    
}
