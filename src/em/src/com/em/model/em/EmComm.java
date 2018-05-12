package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_communication")
public class EmComm extends BaseModel<EmComm> {
    
    public static final EmComm dao = new EmComm();
    
    public List<EmComm> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_communication WHERE deletetime IS NULL";
        List<EmComm> list = find(sql);
        return list;
    }
    
}
