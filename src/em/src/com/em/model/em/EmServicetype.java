package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_servicetype")
public class EmServicetype extends BaseModel<EmServicetype> {
    
    public static final EmServicetype dao = new EmServicetype();
    
    public List<EmServicetype> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_servicetype WHERE deletetime IS NULL";
        List<EmServicetype> list = find(sql);
        return list;
    }
    
}
