package com.em.model.em;

import com.em.model.BaseModel;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="em_info_setting")
public class EmInfoSetting extends BaseModel<EmInfoSetting> {
    
    public static final EmInfoSetting dao = new EmInfoSetting();
    
    public EmInfoSetting getBy(Object eminfoid) {
        String sql = "SELECT * FROM em_info_setting WHERE eminfoid = ?";
        return findFirst(sql, eminfoid);
    }
    
    
}
