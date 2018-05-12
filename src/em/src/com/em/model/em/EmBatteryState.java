package com.em.model.em;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.em.model.BaseModel;
import com.em.model.SysArea;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_battery_state")
public class EmBatteryState extends BaseModel<EmBatteryState> {
    
    public static final EmBatteryState dao = new EmBatteryState();
    
    public List<EmBatteryState> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_battery_state WHERE deletetime IS NULL";
        List<EmBatteryState> list = find(sql);
        return list;
    }
    
    public Map<String, String> listMap() {
        List<EmBatteryState> list = this.list();
        Map<String, String> map = new HashMap<String, String>();
        for (EmBatteryState a : list) {
            map.put(String.valueOf(a.get("id")), a.getStr("name"));
        }
        return map;
    }
    
}
