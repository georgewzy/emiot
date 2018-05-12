package com.em.model.em;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_info_quality")
public class EmInfoQuality extends BaseModel<EmInfoQuality> {
    
    public static final EmInfoQuality dao = new EmInfoQuality();
    
    public EmInfoQuality getBy(Object eminfoid) {
        String sql = "SELECT * FROM em_info_quality WHERE eminfoid = ?";
        return findFirst(sql, eminfoid);
    }
    
}
