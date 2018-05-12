package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_light")
public class EmLight extends BaseModel<EmLight> {
    
    public static final EmLight dao = new EmLight();
    
    public List<EmLight> list() {
        String sql = "SELECT *, id AS oldid FROM em_light WHERE deletetime IS NULL";
        List<EmLight> list = find(sql);
        return list;
    }
    
}
