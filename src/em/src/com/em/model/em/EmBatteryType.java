package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_battery_type")
public class EmBatteryType extends BaseModel<EmBatteryType> {
    
    public static final EmBatteryType dao = new EmBatteryType();
    
    public List<EmBatteryType> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_battery_type WHERE deletetime IS NULL";
        List<EmBatteryType> list = find(sql);
        return list;
    }
    
}
