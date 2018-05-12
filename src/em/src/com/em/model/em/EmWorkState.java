package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_work_state")
public class EmWorkState extends BaseModel<EmWorkState> {
    
    public static final EmWorkState dao = new EmWorkState();
    
    public List<EmWorkState> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_work_state WHERE deletetime IS NULL";
        List<EmWorkState> list = find(sql);
        return list;
    }
    
}
