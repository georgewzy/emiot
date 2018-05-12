package com.em.model.em;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_info_config")
public class EmInfoConfig extends BaseModel<EmInfoConfig> {
    
    public static final EmInfoConfig dao = new EmInfoConfig();
    
    public EmInfoConfig getBy(Object eminfoid) {
        String sql = "SELECT * FROM em_info_config WHERE eminfoid = ?";
        return findFirst(sql, eminfoid);
    }
    
    
}
