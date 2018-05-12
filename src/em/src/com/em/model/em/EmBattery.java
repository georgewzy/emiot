package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_battery")
public class EmBattery extends BaseModel<EmBattery> {
    
    public static final EmBattery dao = new EmBattery();
    
    public List<EmBattery> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_battery WHERE deletetime IS NULL";
        List<EmBattery> list = find(sql);
        return list;
    }
    
}
