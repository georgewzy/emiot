package com.em.controller.em;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysArea;
import com.em.model.SystemOperationLog;
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
import com.em.model.em.EmRecordCurrent;
import com.em.model.em.EmRecordHistory;
import com.em.model.em.EmType;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/emlist", viewPath="/pages/em/list")
public class EmInfoListController extends BaseController {

    @Override
    @RequiresPermissions("em.em.query")
    public void index() {
        // TODO Auto-generated method stub
        String id = getPara();
        EmInfo em = EmInfo.dao.getBy(id);
        
        setAttr("em", em);
        setAttr("areaList", SysArea.dao.list());
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
        render("em_list.jsp");
    }
    
    @RequiresPermissions("em.em.query")
    public void list() {
        String orderField  = this.getOrderBy();
        Integer pageNumber = getParaToInt("pageCurrent", 1);
        Integer pageSize   = getParaToInt("pageSize", this.pageSize);
        EmInfo em          = getModel(EmInfo.class, "em");
        Page<EmInfo> page  = EmInfo.dao.paging(em, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    public void view() {
        String id = getPara();
        EmInfo em = EmInfo.dao.getBy(id);
        if (em == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        EmInfoSetting emsetting = EmInfoSetting.dao.getBy(id);
        EmInfoConfig emconfig   = EmInfoConfig.dao.getBy(id);
        EmRecordCurrent current = EmRecordCurrent.dao.getBy(id);
        current.set("workcommtime",Util.getFormatDate(current.getDate("workcommtime"),"yyyy-MM-dd HH:mm:ss"));
        setAttr("em", em);
        setAttr("emsetting", emsetting);
        setAttr("emconfig", emconfig);
        setAttr("current", current);
        render("em_list_currentinfo.jsp");
    }
    
    //***********************遥控遥测*****************************//
    // 设备参数设置
    public void editSetting() {
        String eminfoid = getPara();
        EmInfoSetting setting = EmInfoSetting.dao.getBy(eminfoid);
        EmInfoConfig config   = EmInfoConfig.dao.getBy(eminfoid);
        if (setting == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        setAttr("emsetting", setting);
        setAttr("emconfig", config);
        setAttr("lightList", EmLight.dao.list());
        setAttr("lightSourceList", EmLightSource.dao.list());
        render("em_info_setting_edit.jsp");
    }
    
    @Before(Tx.class)
    public void saveSetting() {
        EmInfoSetting setting = getModel(EmInfoSetting.class, "emsetting");
        EmInfoConfig config   = getModel(EmInfoConfig.class, "emconfig");
        EmInfo em = EmInfo.dao.findById(setting.get("eminfoid"));
        
        if (Util.isEmptyString(setting.get("id")) || em == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        setting.update();
        config.update();
        //日志
        SystemOperationLog.dao.createLog(Constant.OPERATIONTYPE.EDIT, "修改设备参数设置", "修改了设备["+ em.get("name") +"]的参数设置。", em.get("id"));
        
        render(BjuiRender.success());
    }
    
    public void remoteControl() {
        render("em_remote.jsp");
    }
    
    //***********************图表（轨迹、变化曲线、电池）*****************************//
    // 轨迹回放
    public void position() {
        String id = getPara();
        EmInfo em = EmInfo.dao.getBy(id);
        EmRecordHistory his = getModel(EmRecordHistory.class, "his");
        if (em == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        
        if (Util.isEmptyString(his.get("starttime")) && Util.isEmptyString(his.get("endtime"))) {
            Date today   = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.MONTH, -1);
            his.put("starttime", Util.getFormatDate(cal.getTime(), "yyyy-MM-dd"));
            his.put("endtime", Util.getFormatDate("yyyy-MM-dd"));
        }
        List<EmRecordHistory> positionList = EmRecordHistory.dao.listPositionBy(his, id);
        setAttr("list", JsonKit.toJson(positionList));
        setAttr("em", em);
        setAttr("his", his);
        render("em_list_position.jsp");
    }
    
    // 变化曲线
    public void voltagecurrent() {
        String id = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        if (em == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        setAttr("em", em);
        render("em_list_voltagecurrent.jsp");
    }
    
    // 电池状态
    public void battery() {
        String id = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        if (em == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        setAttr("em", em);
        render("em_list_battery.jsp");
    }
    //开关时间
    public void onofftime(){
    	String id = getPara();
        EmInfo em = EmInfo.dao.findById(id);
        if (em == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        setAttr("em", em);
        render("em_list_onofftime_history.jsp");
    }
    //充放电量
    public void recordhistory(){
    	String id = getPara();
    	EmInfo em = EmInfo.dao.findById(id);
    	if (em == null) {
    		render(BjuiRender.error(Constant.ERR_URL));
    		return;
    	}
    	setAttr("em", em);
    	render("em_list_record_history.jsp");
    }
    
    //***********************操作日志*****************************//
    // 设备操作日志
    public void operationLog() {
        setAttr("eminfoid", getPara());
        setAttr("typeMap", Constant.OPERATION_TYPE);
        render("em_info_log.jsp");
    }
    
    public void listOperationLog() {
        String orderField             = this.getOrderBy();
        Integer pageNumber            = getParaToInt("pageCurrent", 1);
        Integer pageSize              = getParaToInt("pageSize", this.pageSize);
        SystemOperationLog log        = getModel(SystemOperationLog.class, "log");
        Page<SystemOperationLog> page = SystemOperationLog.dao.paging(log, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    
    
}
