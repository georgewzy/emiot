package com.em.model;

import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="system_setting")
public class SystemSetting extends BaseModel<SystemSetting> {

    public static final SystemSetting dao = new SystemSetting();
    
}
