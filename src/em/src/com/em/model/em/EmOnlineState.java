package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_online_state")
public class EmOnlineState extends BaseModel<EmOnlineState> {
    
    public static final EmOnlineState dao = new EmOnlineState();
    
    public List<EmOnlineState> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_online_state WHERE deletetime IS NULL";
        List<EmOnlineState> list = find(sql);
        return list;
    }
    
}
