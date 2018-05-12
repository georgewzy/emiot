package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_alarmrecordtype")
public class EmAlarmrecordtype extends BaseModel<EmAlarmrecordtype> {
    
    public static final EmAlarmrecordtype dao = new EmAlarmrecordtype();
    
    public List<EmAlarmrecordtype> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_alarmrecordtype WHERE deletetime IS NULL";
        List<EmAlarmrecordtype> list = find(sql);
        return list;
    }
    
}
