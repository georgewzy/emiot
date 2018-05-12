package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_position")
public class EmPosition extends BaseModel<EmPosition> {
    
    public static final EmPosition dao = new EmPosition();
    
    public List<EmPosition> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_position WHERE deletetime IS NULL";
        List<EmPosition> list = find(sql);
        return list;
    }
    
}
