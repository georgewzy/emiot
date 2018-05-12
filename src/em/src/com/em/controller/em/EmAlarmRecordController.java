package com.em.controller.em;

import java.util.List;

import com.em.controller.BaseController;
import com.em.model.SysUsers;
import com.em.model.em.EmAlarmRecord;
import com.em.model.em.EmAlarmrecordtype;
import com.em.model.em.EmAlarmtype;
import com.em.model.em.EmType;
import com.em.tools.Util;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

@ControllerBind(controllerKey="/sys/em/alarmrecord", viewPath="/pages/em/alarmrecord")
public class EmAlarmRecordController extends BaseController {

    @Override
    public void index() {
        // TODO Auto-generated method stub
        String eminfoid = getPara();
        List<EmAlarmRecord> list = EmAlarmRecord.dao.listBy(eminfoid);
        setAttr("list", JsonKit.toJson(list));
        setAttr("alarmtypeList", EmAlarmtype.dao.list());
        setAttr("alarmrecordtypeList", EmAlarmrecordtype.dao.list());
        render("list.jsp");
    }
    
    public void listAlarmRecord() {
        setAttr("typeList", EmType.dao.list());
        setAttr("alarmtypeList", EmAlarmtype.dao.list());
        setAttr("alarmrecordtypeList", EmAlarmrecordtype.dao.list());
        render("history_list.jsp");
    }
    
    public void list() {
        String orderField        = this.getOrderBy();
        Integer pageNumber       = getParaToInt("pageCurrent", 1);
        Integer pageSize         = getParaToInt("pageSize", this.pageSize);
        EmAlarmRecord alarm      = getModel(EmAlarmRecord.class, "ar");
        
        SysUsers user      = getUser();
        if (!user.getStr("id").equals("super") && !Util.isEmptyString(user.get("areaids"))) {
            alarm.put("areaids", user.get("areaids"));
        }
        
        Page<EmAlarmRecord> page = EmAlarmRecord.dao.paging(alarm, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
}
