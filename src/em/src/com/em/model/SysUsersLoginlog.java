package com.em.model;

import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="sys_users_loginlog")
public class SysUsersLoginlog extends BaseModel<SysUsersLoginlog> {

    public final static SysUsers dao = new SysUsers();
    
    
}
