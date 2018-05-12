package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_brightlevel")
public class EmBrightlevel extends BaseModel<EmBrightlevel> {
    
    public static final EmBrightlevel dao = new EmBrightlevel();
    
    public List<EmBrightlevel> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_brightlevel WHERE deletetime IS NULL";
        List<EmBrightlevel> list = find(sql);
        return list;
    }
    
}
