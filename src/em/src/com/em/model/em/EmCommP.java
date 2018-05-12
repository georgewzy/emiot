package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_communication_protocol")
public class EmCommP extends BaseModel<EmCommP> {
    
    public static final EmCommP dao = new EmCommP();
    
    public List<EmCommP> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_communication_protocol WHERE deletetime IS NULL";
        List<EmCommP> list = find(sql);
        return list;
    }
    
}
