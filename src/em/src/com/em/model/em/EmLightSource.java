package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_light_source")
public class EmLightSource extends BaseModel<EmLightSource> {
    
    public static final EmLightSource dao = new EmLightSource();
    
    public List<EmLightSource> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_light_source WHERE deletetime IS NULL";
        List<EmLightSource> list = find(sql);
        return list;
    }
    
}
