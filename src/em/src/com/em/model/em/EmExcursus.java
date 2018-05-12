package com.em.model.em;

import java.util.List;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_excursus")
public class EmExcursus extends BaseModel<EmExcursus> {
    
    public static final EmExcursus dao = new EmExcursus();
    
    public List<EmExcursus> list() {
        String sql = "SELECT id, name, id AS oldid FROM em_excursus WHERE deletetime IS NULL";
        List<EmExcursus> list = find(sql);
        return list;
    }
    
}
