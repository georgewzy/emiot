package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_type")
public class EmType extends BaseModel<EmType> {
    
    public static final EmType dao = new EmType();
    
    public List<EmType> list() {
        String sql = "SELECT id, name, pic, icon, id AS oldid FROM em_type WHERE deletetime IS NULL";
        List<EmType> list = find(sql);
        return list;
    }
    
}
