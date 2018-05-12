package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_power")
public class EmPower extends BaseModel<EmPower> {
    
    public static final EmPower dao = new EmPower();
    
    public List<EmPower> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_power WHERE deletetime IS NULL";
        List<EmPower> list = find(sql);
        return list;
    }
    
}
