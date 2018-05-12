package com.em.controller.em;

import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysUsers;
import com.em.model.em.EmBatteryState;
import com.em.model.em.EmComm;
import com.em.model.em.EmLight;
import com.em.model.em.EmLightSource;
import com.em.model.em.EmOnlineState;
import com.em.model.em.EmPosition;
import com.em.model.em.EmRecordHistory;
import com.em.model.em.EmRecordLbsDao;
import com.em.model.em.EmType;
import com.em.model.em.EmWorkState;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;

@ControllerBind(controllerKey="/sys/em/history", viewPath="/pages/em/history")
public class EmRecordHistoryController extends BaseController {
    
    // 定位信息
    @Override
    public void index() {
        // TODO Auto-generated method stub
        setAttr("typeList", EmType.dao.list());
        setAttr("positionList", EmPosition.dao.list());
        render("history_position_list.jsp");
    }
    
    // 工作状态
    public void listWorkstate() {
        setAttr("typeList", EmType.dao.list());
        setAttr("workStateList", EmWorkState.dao.list());
        setAttr("onlineStateList", EmOnlineState.dao.list());
        setAttr("commList", EmComm.dao.list());
        setAttr("lightList", EmLight.dao.list());
        setAttr("lightSourceList", EmLightSource.dao.list());
        render("history_workstate_list.jsp");
    }
    // LBS信息
    public void recordlbs() {
    	String id = getPara();
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        setAttr("historyLbsRecordsList", EmRecordLbsDao.dao.list(id));
    	render("history_lbs_record_list.jsp");
    }
    
    // 电池信息
    public void listBattery() {
        setAttr("typeList", EmType.dao.list());
        setAttr("batteryStateList", EmBatteryState.dao.list());
        render("history_battery_list.jsp");
    }
    
    public void list() {
        String orderField          = this.getOrderBy();
        Integer pageNumber         = getParaToInt("pageCurrent", 1);
        Integer pageSize           = getParaToInt("pageSize", this.pageSize);
        EmRecordHistory history    = getModel(EmRecordHistory.class, "history");
        
        SysUsers user      = getUser();
        if (!user.getStr("id").equals("super") && !Util.isEmptyString(user.get("areaids"))) {
            history.put("areaids", user.get("areaids"));
        }
        
        Page<EmRecordHistory> page = EmRecordHistory.dao.paging(history, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    // 位置记录
    public void position() {
        String id = getPara();
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        setAttr("eminfoid", id);
        setAttr("positionList", EmPosition.dao.list());
        render("list_position.jsp");
    }
    
    // 工作状态
    public void workstate() {
        String id = getPara();
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        setAttr("eminfoid", id);
        setAttr("workStateList", EmWorkState.dao.list());
        setAttr("onlineStateList", EmOnlineState.dao.list());
        setAttr("commList", EmComm.dao.list());
        setAttr("lightList", EmLight.dao.list());
        setAttr("lightSourceList", EmLightSource.dao.list());
        
        render("list_workstate.jsp");
    }
    
}
