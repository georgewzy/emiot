package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_alarmtype")
public class EmAlarmtype extends BaseModel<EmAlarmtype> {
    
    public static final EmAlarmtype dao = new EmAlarmtype();
    
    public List<EmAlarmtype> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_alarmtype WHERE deletetime IS NULL";
        List<EmAlarmtype> list = find(sql);
        return list;
    }
    
}
